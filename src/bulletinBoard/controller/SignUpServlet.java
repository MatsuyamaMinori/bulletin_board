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

import bulletinBoard.beans.Branches;
import bulletinBoard.beans.JobTitles;
import bulletinBoard.beans.User;
import bulletinBoard.service.BranchesService;
import bulletinBoard.service.JobTitlesService;
import bulletinBoard.service.UserCheckService;
import bulletinBoard.service.UserService;


@WebServlet(urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

	List<Branches> branches = new BranchesService().getBranches();
	request.setAttribute("branches", branches);

	List<JobTitles> JobTitles = new JobTitlesService().getJobTitles();
	request.setAttribute("JobTitles", JobTitles);


		request.getRequestDispatcher("/signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		List<String> compMessage = new ArrayList<String>();

		HttpSession session = request.getSession();

			User user = new User();
			user.setName(request.getParameter("name"));
			user.setLoginId(request.getParameter("loginId"));
			user.setPassword(request.getParameter("password"));
			user.setBranchId(Integer.parseInt(request.getParameter("branchId")));
			user.setJobTitleId(Integer.parseInt(request.getParameter("jobTitleId")));

		if (isValid(request, messages) == true) {

			new UserService().register(user);
			compMessage.add(request.getParameter("name")+"のユーザー情報の登録が完了しました。");
			session.setAttribute("compMessages", compMessage);
			response.sendRedirect("managementUsers");

		} else {
			request.setAttribute("error",user );

			session.setAttribute("errorMessages", messages);

			List<Branches> branches = new BranchesService().getBranches();
			request.setAttribute("branches", branches);
			List<JobTitles> JobTitles = new JobTitlesService().getJobTitles();
			request.setAttribute("JobTitles", JobTitles);

			request.getRequestDispatcher("/signup.jsp").forward(request, response);
		}
	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String name = request.getParameter("name");
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int jobTitleId = Integer.parseInt(request.getParameter("jobTitleId"));


		if (StringUtils.isBlank(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}
		if (!loginId.matches("^\\w{6,20}$")) {
			messages.add("ログインIDは半角英数字6文字以上20文字以内で入力してください");
		}
		UserCheckService userCheckService = new UserCheckService();
		User existLoginId = userCheckService.userCheckId(loginId);
		if(existLoginId != null){
			messages.add("このログインIDは既に使われています");
		}

		if (StringUtils.isBlank(name) == true) {
			messages.add("アカウント名を入力してください");
		}
		if (10 < name.length()) {
			messages.add("アカウント名を10文字以下で入力してください。");
		}

		User existPassword = userCheckService.userCheckPassword(password);
		if(existPassword != null){
			messages.add("このパスワードは既に使われています");
		}
		if (StringUtils.isBlank(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if (!password.matches("^\\w{6,255}$")) {
			messages.add("パスワードは半角英数字6文字以上255文字以内で入力してください");
		}
		if (!password.equals(checkPassword) || StringUtils.isBlank(checkPassword)) {
			messages.add("パスワードと確認用パスワードが一致しません");
		}

		if(branchId == 0){
			messages.add("所属の項目を選択してください");
		}
		if(jobTitleId == 0){
			messages.add("役職の項目を選択してください");
		}
		if(branchId == 1 && jobTitleId >= 3){
			messages.add("本社勤務者は役職に総務人事担当、情報管理担当以外は選べません。");
		}
		if(branchId >=2 && jobTitleId <= 2){
			messages.add("支店勤務者は役職に支店長、支店社員以外は選べません。");
		}


		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
