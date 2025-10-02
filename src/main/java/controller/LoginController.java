package controller;

import model.User;
import service.UserService;
import service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/login.jsp").forward(req, resp);
    }

    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String username = req.getParameter("username");
	    String password = req.getParameter("password");
	    boolean remember = "1".equals(req.getParameter("remember"));
	
	    // TODO: lấy user từ DB (UserService). Mật khẩu lưu dạng hash BCrypt
	    model.User user = userService.findByUsername(username);
	    if (user == null || !org.mindrot.jbcrypt.BCrypt.checkpw(password, user.getPasswordHash())) {
	        req.setAttribute("error", "Sai thông tin đăng nhập");
	        req.getRequestDispatcher("/login.jsp").forward(req, resp);
	        return;
	    }
	
	    // 1) Session
	    HttpSession session = req.getSession(true);
	    session.setAttribute("user", user);
	    session.setMaxInactiveInterval(30 * 60);
	
	    // 2) Cookie remember-me (nếu tick)
	    if (remember && rememberMeService != null) {
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
