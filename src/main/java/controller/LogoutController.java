package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.RememberMeService;
import model.User;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private RememberMeService rememberMeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s != null) {
            Object u = s.getAttribute("user");
            s.invalidate();
            if (u instanceof User && rememberMeService != null) {
                try { rememberMeService.clearAll(((User) u).getId()); } catch (Exception ignore) {}
            }
        }
        RememberMeService.deleteCookie(resp, req.getContextPath());
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
