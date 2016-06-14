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

import org.apache.commons.lang.StringUtils;

import bulletinBoard.beans.Articles;
import bulletinBoard.beans.User;
import bulletinBoard.service.ArticlesService;

@WebServlet(urlPatterns = { "/newArticles" })
public class NewArticlesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/newArticles.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();
		List<String> compMessage = new ArrayList<String>();

			User user = (User) session.getAttribute("loginUser");

			Articles article = new Articles();
			article.setTitel(request.getParameter("titel"));
			article.setText(request.getParameter("text"));
			article.setCategory(request.getParameter("category"));
			article.setUserId(user.getId());

			if (isValid(request, messages) == true) {

			new ArticlesService().register(article);
			compMessage.add("〈件名〉　"+request.getParameter("titel")+"　の投稿が完了しました。");
			session.setAttribute("compMessages", compMessage);
			response.sendRedirect("top");

		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("error",article );

			request.getRequestDispatcher("/newArticles.jsp").forward(request, response);
//			response.sendRedirect("newArticles");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String titel = request.getParameter("titel");
		String text = request.getParameter("text");
		String category = request.getParameter("category");

		if (StringUtils.isBlank(titel) == true) {
			messages.add("件名を入力してください。");
		}
		if (50 < titel.length()) {
			messages.add("件名を50文字以下で入力してください。");
		}
		if (StringUtils.isBlank(text) == true) {
			messages.add("本文を入力してください。");
		}
		if (1000 < text.length()) {
			messages.add("本文を1000文字以下で入力してください。");
		}
		if (StringUtils.isBlank(category) == true) {
			messages.add("投稿の種類を入力してください。");
		}
		if (10 < category.length()) {
			messages.add("投稿の種類を10文字以下で入力してください。");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
