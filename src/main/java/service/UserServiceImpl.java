package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Instant;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        User user = userDao.findByUsername(username.trim());
        if (user == null) {
            return null;
        }
        return BCrypt.checkpw(password, user.getPasswordHash()) ? user : null;
    }

    @Override
    public boolean register(String username, String password, String email, String fullname, String phone) {
        if (userDao.existsByUsername(username)
                || userDao.existsByEmail(email)
                || (phone != null && !phone.isBlank() && userDao.existsByPhone(phone))) {
            return false;
        }
        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setFullName(fullname);
        user.setPhone(phone);
        user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt(10)));
        user.setCreatedAt(Instant.now());
        userDao.save(user);
        return true;
    }

    @Override
    public User findByUsername(String username) {
        return username == null ? null : userDao.findByUsername(username.trim());
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }
}
