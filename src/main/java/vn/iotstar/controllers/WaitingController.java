package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/waiting")
public class WaitingController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		
		HttpSession session = req.getSession(false);
		//check if already cookie or not
		boolean ok = false;
		if (session != null && session.getAttribute("account") != null) {
			ok = true;
		}
		
		// Check cookie
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				session = req.getSession(true);
				session.setAttribute(cookie.getName(), cookie.getValue());
				ok = true;
			}
			
		}
		if (!ok) {
			// go back home page
			goHomePage(req, resp);
			return;
		}
		req.setAttribute("name", session.getAttribute("username"));
		req.getRequestDispatcher("main.jsp").forward(req, resp);
		
	}

	public void goHomePage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("home.jsp").forward(req, resp);

	}
}
