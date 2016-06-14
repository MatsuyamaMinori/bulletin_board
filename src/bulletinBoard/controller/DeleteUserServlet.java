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

@WebServlet(urlPatterns = { "/deleteUser" })
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		List<String> compMessage = new ArrayList<String>();
		User user = (User) request.getSession().getAttribute("loginUser");
		String name = request.getParameter("name");
		System.out.println(name);

		if(user.getId() != Integer.parseInt(request.getParameter("userId"))){
			new UserService().delete(Integer.parseInt(request.getParameter("userId")));

			compMessage.add(name+"のユーザー情報を削除しました。");
			session.setAttribute("compMessages", compMessage);

			response.sendRedirect("managementUsers");
		}else{
			messages.add("自身のユーザー情報を削除することはできません。");
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/managementUsers.jsp").forward(request, response);
		}
	}
}