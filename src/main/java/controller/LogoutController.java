package controller;

import dao.impl.RememberMeDaoImpl;
import dao.impl.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.RememberMeService;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private final RememberMeService rememberMeService =
            new RememberMeService(new RememberMeDaoImpl(), new UserDaoImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("account");
            session.invalidate();
            if (user instanceof User u) {
                try {
                    rememberMeService.clearAll(u.getId());
                } catch (Exception ignored) {
                }
            }
        }
        RememberMeService.deleteCookie(resp, req.getContextPath());
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
