package bulletinBoard.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletinBoard.beans.Search;
import bulletinBoard.beans.User;
import bulletinBoard.beans.UsersArticles;
import bulletinBoard.beans.UsersComment;
import bulletinBoard.comparator.ArticlesCommentCom;
import bulletinBoard.service.ArticlesService;
import bulletinBoard.service.CommentService;

@WebServlet(urlPatterns = {"/top"})
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("loginUser");
		if(user == null){
			System.out.println("userがnullです。");
		}

		boolean authorityTransition;
		if(user.getJobTitleId() == 1){
			authorityTransition = true;
		} else {
			authorityTransition = false;
		}

		boolean deleteAll;
		boolean deleteBranch;
		if(user.getJobTitleId() == 2) {
			deleteAll = true;
		} else {
			deleteAll = false;
		}
		if(user.getJobTitleId() == 3) {
			deleteBranch = true;
		} else {
			deleteBranch = false;
		}

		List<String> categoriesList = new ArticlesService().getCategory();
		Set<String> categoriesSet = new HashSet<String>();
		categoriesSet.addAll(categoriesList);
		request.setAttribute("categories", categoriesSet);

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		List<String> searchMessages = new ArrayList<String>();

		String category = request.getParameter("categorySearch");
		String beforeYear = request.getParameter("beforeYear");
		String beforeManth = request.getParameter("beforeManth");
		String beforeDate = request.getParameter("beforeDate");
		String afterYear = request.getParameter("afterYear");
		String afterManth = request.getParameter("afterManth");
		String afterDate = request.getParameter("afterDate");

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdfM = new SimpleDateFormat("MM");
		SimpleDateFormat sdfD = new SimpleDateFormat("dd");
		String strY = sdfY.format(cal.getTime());
		String strM = sdfM.format(cal.getTime());
		String strD = sdfD.format(cal.getTime());

		Search search = new Search();
		search.setCategory(request.getParameter("categorySearch"));
		search.setYear1(request.getParameter("beforeYear"));
		search.setMonth1(request.getParameter("beforeManth"));
		search.setDate1(request.getParameter("beforeDate"));
		search.setYear2(request.getParameter("afterYear"));
		search.setMonth2(request.getParameter("afterManth"));
		search.setDate2(request.getParameter("afterDate"));

		if((StringUtils.isEmpty(beforeYear) && StringUtils.isEmpty(beforeManth) && StringUtils.isEmpty(beforeDate)) &&
				(StringUtils.isEmpty(afterYear) && StringUtils.isEmpty(afterManth) && StringUtils.isEmpty(afterDate))){

			List<UsersArticles> categoryArticles = new ArticlesService().getCategoryArticles
					(category,"2016","0","0",strY,strM,strD);
			if(categoryArticles != null){
				Collections.sort(categoryArticles, new ArticlesCommentCom());
				request.setAttribute("article", categoryArticles);
				request.setAttribute("search",search );
			}else{
				messages.add("投稿がありません。");
				session.setAttribute("errorMessages", messages);
				request.setAttribute("search",search );
			}

		} else if((!StringUtils.isEmpty(beforeYear) && !StringUtils.isEmpty(beforeManth) && !StringUtils.isEmpty(beforeDate)) &&
				(!StringUtils.isEmpty(afterYear) && !StringUtils.isEmpty(afterManth) && !StringUtils.isEmpty(afterDate))){

			try{
				Date date1 = DateFormat.getDateInstance().parse(beforeYear + "/" + beforeManth + "/" + beforeDate);
				Date date2 = DateFormat.getDateInstance().parse(afterYear + "/" + afterManth + "/" + afterDate);
				int diff = date1.compareTo(date2);

				if (diff == 0 || diff < 0) {
					List<UsersArticles> categoryArticles = new ArticlesService().getCategoryArticles
						(category,beforeYear,beforeManth,beforeDate,afterYear,afterManth,afterDate);
					if(categoryArticles != null){
						Collections.sort(categoryArticles, new ArticlesCommentCom());
						session.removeAttribute("article");
						request.setAttribute("article", categoryArticles);
						request.setAttribute("search",search );
					} else {
						messages.add("該当する投稿がありません。");
						session.setAttribute("errorMessages", messages);
						request.setAttribute("search",search );
					}
				} else {
					searchMessages.add("日付の入力が前と後で逆になっています。");
					session.setAttribute("searchMessages", searchMessages);
					request.setAttribute("search",search );
				}
			}catch (ParseException e) {
				System.out.println("前"+e);
			}

		} else if((StringUtils.isEmpty(beforeYear) && StringUtils.isEmpty(beforeManth) && StringUtils.isEmpty(beforeDate)) &&
				(!StringUtils.isEmpty(afterYear) && !StringUtils.isEmpty(afterManth) && !StringUtils.isEmpty(afterDate))){

			List<UsersArticles> categoryArticles = new ArticlesService().getCategoryArticles
					(category,"2016","0","0",afterYear,afterManth,afterDate);
			if(categoryArticles != null){
				Collections.sort(categoryArticles, new ArticlesCommentCom());
				session.removeAttribute("article");
				request.setAttribute("article", categoryArticles);
				request.setAttribute("search",search );
			} else {
				messages.add("該当する投稿がありません。");
				session.setAttribute("errorMessages", messages);
				request.setAttribute("search",search );
			}

		} else if((!StringUtils.isEmpty(beforeYear) && !StringUtils.isEmpty(beforeManth) && !StringUtils.isEmpty(beforeDate)) &&
				(StringUtils.isEmpty(afterYear) && StringUtils.isEmpty(afterManth) && StringUtils.isEmpty(afterDate))){

			List<UsersArticles> categoryArticles = new ArticlesService().getCategoryArticles
					(category,beforeYear,beforeManth,beforeDate,strY,strM,strD);
			if(categoryArticles != null){
				Collections.sort(categoryArticles, new ArticlesCommentCom());
				session.removeAttribute("article");
				request.setAttribute("article", categoryArticles);
				request.setAttribute("search",search );
			} else {
				messages.add("該当する投稿がありません。");
				session.setAttribute("errorMessages", messages);
				request.setAttribute("search",search );
			}

		} else {
			searchMessages.add("日付検索の年月日は必須です。");
			session.setAttribute("searchMessages", searchMessages);
			List<UsersArticles> categoryArticles = new ArticlesService().getCategoryArticles
					(category,"2016","0","0",strY,strM,strD);
			request.setAttribute("article", categoryArticles);
			request.setAttribute("search",search );
		}

		List<UsersComment> comments = new CommentService().getComment();
		request.setAttribute("comment", comments);

		request.setAttribute("authorityTransition", authorityTransition);
		request.setAttribute("deleteAll", deleteAll);
		request.setAttribute("deleteBranch", deleteBranch);

		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
