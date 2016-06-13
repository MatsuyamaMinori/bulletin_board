package bulletinBoard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulletinBoard.beans.User;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/stopedUser" })
public class StopedUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		new UserService().stoped(Integer.parseInt(request.getParameter("userId")));

		HttpSession session = request.getSession();
		List<String> compMessage = new ArrayList<String>();
		List<String> messages = new ArrayList<String>();
		User user = (User) request.getSession().getAttribute("loginUser");

		if(user.getId() != Integer.parseInt(request.getParameter("userId"))){
			compMessage.add(request.getParameter("name")+"のユーザー機能が停止しました。");
			session.setAttribute("compMessages", compMessage);

			response.sendRedirect("managementUsers");
		}else{
			messages.add("自身のユーザー機能を停止することはできません。");
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/managementUsers.jsp").forward(request, response);
		}

	}
}