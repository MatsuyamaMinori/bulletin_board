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

import bulletinBoard.beans.Comment;
import bulletinBoard.beans.User;
import bulletinBoard.service.CommentService;

@WebServlet(urlPatterns = {"/newComment"})
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();
		List<String> compMessage = new ArrayList<String>();

			User user = (User) session.getAttribute("loginUser");

			Comment comment = new Comment();
			comment.setText(request.getParameter("text"));
			comment.setUserId(user.getId());
			comment.setArticleId(Integer.parseInt(request.getParameter("articleId")));

			String category = request.getParameter("category");
			String beforeYear = request.getParameter("beforeYear");
			String beforeManth = request.getParameter("beforeManth");
			String beforeDate = request.getParameter("beforeDate");
			String afterYear = request.getParameter("afterYear");
			String afterManth = request.getParameter("afterManth");
			String afterDate = request.getParameter("afterDate");
			if(category == null){
				System.out.println("カテゴリーが"+category);
			}

			String encodeStr = null;

		if (isValid(request, messages) == true) {

			new CommentService().register(comment);
			if(StringUtils.isBlank(category) && StringUtils.isBlank(beforeYear) && StringUtils.isBlank(afterYear)){
				compMessage.add("【件名】　"+request.getParameter("titel")+"　の投稿にコメントしました。");
				session.setAttribute("compMessages", compMessage);
				response.sendRedirect("top");
			} else {
				if(!StringUtils.isBlank(category)){
					encodeStr = URLEncoder.encode(category,"utf-8");
				}
				compMessage.add("【件名】　"+request.getParameter("title")+"　の投稿にコメントが送りました。");
				session.setAttribute("compMessages", compMessage);
				response.sendRedirect("top?categorySearch=" + encodeStr
						+ "&beforeYear=" + beforeYear + "&beforeManth=" + beforeManth + "&beforeDate=" + beforeDate
						+ "&afterYear=" + afterYear + "&afterManth=" + afterManth + "&afterDate=" + afterDate);
			}

		} else {
			if(StringUtils.isBlank(category) && StringUtils.isBlank(beforeYear) && StringUtils.isBlank(afterYear)){
				request.setAttribute("error",comment );
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("top");
			} else {
				if(!StringUtils.isBlank(category)){
					encodeStr = URLEncoder.encode(category,"utf-8");
				}
				request.setAttribute("error",comment );
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("top?categorySearch=" + encodeStr
						+ "&beforeYear=" + beforeYear + "&beforeManth=" + beforeManth + "&beforeDate=" + beforeDate
						+ "&afterYear=" + afterYear + "&afterManth=" + afterManth + "&afterDate=" + afterDate);
			}
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String text = request.getParameter("text");

		if (StringUtils.isBlank(text) == true) {
			messages.add("コメントを入力してください");
		}
		if (500 < text.length()) {
			messages.add("500文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
