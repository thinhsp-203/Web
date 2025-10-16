package controller;

import dao.impl.RememberMeDaoImpl;
import dao.impl.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.RememberMeService;
import service.UserService;
import service.UserServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService userService = new UserServiceImpl();
    private final RememberMeService rememberMeService =
            new RememberMeService(new RememberMeDaoImpl(), new UserDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean remember = "1".equals(req.getParameter("remember"));

        User user = userService.authenticate(username, password);
        if (user == null) {
            req.setAttribute("error", "Sai thông tin đăng nhập");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("account", user);
        session.setMaxInactiveInterval(30 * 60);

        if (remember) {
            try {
                Cookie c = rememberMeService.issueCookie(user.getId(), req.getContextPath());
                resp.addCookie(c);
            } catch (Exception e) {
                // TODO: log warning
            }
        }

        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
