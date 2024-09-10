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
import vn.iotstar.services.UserService;
import vn.iotstar.utils.constant;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/register")
public class RegisterController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("username") != null) {
			resp.sendRedirect(req.getContextPath() + "/admin");
			return;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
		for (Cookie cookie : cookies) {
		 if (cookie.getName().equals("username")) {
		session = req.getSession(true);
		session.setAttribute("username", cookie.getValue());
		resp.sendRedirect(req.getContextPath() + "/admin");
		return;
		 }
		}
		}
		req.getRequestDispatcher(constant.Path.REGISTER).forward(req, resp);
		}
	@SuppressWarnings("static-access")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");
		
		UserService service = new userServiceImpl();
		String alertMsg ="";
		
		if (service.checkExistEmail(email)) {
			alertMsg = "Email đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(constant.Path.REGISTER).forward(req, resp);
			return;
		}
		if (service.checkExistUsername(username)) {
			alertMsg =
					"Tài khoản đã tồn tại!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(constant.Path.REGISTER).forward(req, resp);
			return;
			}
		
		boolean isSuccess = service.register(username, password, email, fullname,
				phone);
		if (isSuccess) {
			//SendMail sm = new SendMail();
			//sm.sendMail(email, "Shopping.iotstar.vn", "Welcome to Shopping. Please Loginto use service. Thanks !");
			req.setAttribute("alert", alertMsg);
			resp.sendRedirect(req.getContextPath() + "/login");
		} else {
			alertMsg = "System error!";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher(constant.Path.REGISTER).forward(req, resp);
		}
	}

	
	
}
