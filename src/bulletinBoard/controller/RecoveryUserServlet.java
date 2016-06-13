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

import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/recoveryUser" })
public class RecoveryUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		new UserService().recovery(Integer.parseInt(request.getParameter("userId")));

		HttpSession session = request.getSession();
		List<String> compMessage = new ArrayList<String>();
		compMessage.add(request.getParameter("name")+"のユーザー機能が復活しました。");
		session.setAttribute("compMessages", compMessage);

		response.sendRedirect("managementUsers");

	}
}
