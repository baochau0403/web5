package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import serviceimpl.userServiceImpl;
import vn.iotstar.models.UserModel;
import vn.iotstar.services.UserService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
	public static final String SESSION_USERNAME = "username";
	public static final String COOKIE_REMEMBER = "username";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean isRememberMe = false;
		String remember = req.getParameter("remember");

		if ("on".equals(remember)) {
			isRememberMe = true;
		}
		String errorMsg = "";
		if (username.isEmpty() || password.isEmpty()) {
			errorMsg = "Tài khoản hoặc mật khẩu không được trống";
			req.setAttribute("error", errorMsg);
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}

		UserService service = new userServiceImpl();

		UserModel user = service.login(username, password);
		if (user != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute("account", user);
			System.out.println(user.getFullname());
			if (isRememberMe) {
				saveRemeber(resp, username, user.getFullname());
			}
			System.out.println("User "+username+" login to web");
			req.setAttribute("name", user.getFullname());
			req.getRequestDispatcher("main.jsp").forward(req, resp);
		} else {
			System.out.println("login fail");
			errorMsg = "Tài khoản hoặc mật khẩu không đúng";
			req.setAttribute("error", errorMsg); 
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("account") != null) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}
		// Check cookie
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					session = req.getSession(true);
					resp.sendRedirect(req.getContextPath() + "/waiting");
//					req.getRequestDispatcher("main.jsp").forward(req, resp);
					return;
				}
			}
		}
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	private void saveRemeber(HttpServletResponse response, String username, String fullname) {
		Cookie cookie = new Cookie(COOKIE_REMEMBER, username);
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
	}
}