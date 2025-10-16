package service;

import dao.RememberMeDao;
import dao.UserDao;
import model.RememberMeToken;
import org.mindrot.jbcrypt.BCrypt;
import model.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

public class RememberMeService {
    private final RememberMeDao dao;
    private final UserDao userDao;

    public RememberMeService(RememberMeDao dao, UserDao userDao) {
        this.dao = dao;
        this.userDao = userDao;
    }

    public Cookie issueCookie(long userId, String contextPath) throws Exception {
        User user = userDao.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found for remember-me token");
        }
        String selector = UUID.randomUUID().toString().replace("-", "");
        byte[] raw = new byte[32];
        new SecureRandom().nextBytes(raw);
        String validator = Base64.getUrlEncoder().withoutPadding().encodeToString(raw);

        String validatorHash = BCrypt.hashpw(validator, BCrypt.gensalt(10));
        RememberMeToken t = new RememberMeToken();
        t.setUser(user);
        t.setSelector(selector);
        t.setValidatorHash(validatorHash);
        t.setExpiresAt(Instant.now().plus(Duration.ofDays(30)));
        dao.save(t);

        String value = selector + ":" + validator;
        Cookie c = new Cookie("remember_me", value);
        c.setHttpOnly(true);
        c.setSecure(false);             // đặt true khi triển khai HTTPS
        c.setPath(contextPath);
        c.setMaxAge(60 * 60 * 24 * 30);
        return c;
    }

    public Long consume(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws Exception {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (!"remember_me".equals(c.getName())) continue;
            String[] parts = c.getValue().split(":", 2);
            if (parts.length != 2) return null;
            String selector = parts[0], validator = parts[1];

            RememberMeToken t = dao.findBySelector(selector);
            if (t == null || Instant.now().isAfter(t.getExpiresAt())) return null;
            if (!BCrypt.checkpw(validator, t.getValidatorHash())) {
                dao.deleteBySelector(selector);
                return null;
            }
            // rotate
            dao.deleteBySelector(selector);
            Cookie rotated = issueCookie(t.getUser().getId(), contextPath);
            resp.addCookie(rotated);
            return t.getUser().getId();
        }
        return null;
    }

    public void clearAll(long userId) throws Exception { dao.deleteAllForUser(userId); }

    public static void deleteCookie(HttpServletResponse resp, String contextPath) {
        Cookie c = new Cookie("remember_me", "");
        c.setPath(contextPath);
        c.setMaxAge(0);
        resp.addCookie(c);
    }
}
