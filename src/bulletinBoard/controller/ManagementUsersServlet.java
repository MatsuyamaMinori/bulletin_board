package bulletinBoard.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.User;
import bulletinBoard.comparator.ManagementUsersCom;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = {"/managementUsers"})
public class ManagementUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("loginUser");
		request.setAttribute("loginUser", user);

		List<User> users = new UserService().getUserAll();
		Collections.sort(users, new ManagementUsersCom());
		request.setAttribute("user", users);

		request.getRequestDispatcher("/managementUsers.jsp").forward(request, response);
	}

}
