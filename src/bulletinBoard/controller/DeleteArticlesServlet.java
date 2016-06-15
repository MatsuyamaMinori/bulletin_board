package bulletinBoard.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletinBoard.beans.User;
import bulletinBoard.service.ArticlesService;
import bulletinBoard.service.CommentService;

@WebServlet(urlPatterns = { "/deleteArticles" })
public class DeleteArticlesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String category = request.getParameter("category");
		String beforeYear = request.getParameter("beforeYear");
		String beforeManth = request.getParameter("beforeManth");
		String beforeDate = request.getParameter("beforeDate");
		String afterYear = request.getParameter("afterYear");
		String afterManth = request.getParameter("afterManth");
		String afterDate = request.getParameter("afterDate");

		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int userId = Integer.parseInt(request.getParameter("userId"));

		String encodeStr = null;

		HttpSession session = request.getSession();
		List<String> compMessage = new ArrayList<String>();
		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		if(user.getJobTitleId() == 2 ||(user.getId() == userId) ||
				(user.getJobTitleId() == 3 && branchId == user.getBranchId())){

			new ArticlesService().delete(Integer.parseInt(request.getParameter("articleId")));
			new CommentService().delete(Integer.parseInt(request.getParameter("articleId")));

			if(StringUtils.isBlank(category) && StringUtils.isBlank(beforeYear) && StringUtils.isBlank(afterYear)){
				compMessage.add("〈件名〉　"+request.getParameter("titel")+"　の投稿を削除しました。");
				session.setAttribute("compMessages", compMessage);
				response.sendRedirect("top");
			} else {
				if(!StringUtils.isBlank(category)){
					encodeStr = URLEncoder.encode(category,"utf-8");
				}
				compMessage.add("〈件名〉　"+request.getParameter("titel")+"　の投稿を削除しました。");
				session.setAttribute("compMessages", compMessage);
				response.sendRedirect("top?categorySearch=" + encodeStr
						+ "&beforeYear=" + beforeYear + "&beforeManth=" + beforeManth + "&beforeDate=" + beforeDate
						+ "&afterYear=" + afterYear + "&afterManth=" + afterManth + "&afterDate=" + afterDate);
			}
		}else{
			messages.add("権限がありません。");
			session.setAttribute("errorMessages", messages);

			if(StringUtils.isBlank(category) && StringUtils.isBlank(beforeYear) && StringUtils.isBlank(afterYear)){
				response.sendRedirect("top");
			} else {
				if(!StringUtils.isBlank(category)){
					encodeStr = URLEncoder.encode(category,"utf-8");
				}
				response.sendRedirect("top?categorySearch=" + encodeStr
						+ "&beforeYear=" + beforeYear + "&beforeManth=" + beforeManth + "&beforeDate=" + beforeDate
						+ "&afterYear=" + afterYear + "&afterManth=" + afterManth + "&afterDate=" + afterDate);
			}
		}
	}
}