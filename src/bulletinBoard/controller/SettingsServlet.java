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
import bulletinBoard.exception.NoRowsUpdatedRuntimeException;
import bulletinBoard.service.BranchesService;
import bulletinBoard.service.JobTitlesService;
import bulletinBoard.service.UserCheckService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/settings" })
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		List<Branches> branches = new BranchesService().getBranches();
		request.setAttribute("branches", branches);

		List<JobTitles> JobTitles = new JobTitlesService().getJobTitles();
		request.setAttribute("JobTitles", JobTitles);

		User user = (User) request.getSession().getAttribute("loginUser");
		String userId = request.getParameter("userId");

		if(!StringUtils.isBlank(userId)){
			User settingUser = new UserService().getUser(Integer.parseInt(userId));
			if(settingUser != null){
				session.setAttribute("settingUser", settingUser);
				request.setAttribute("loginUser", user);
				request.getRequestDispatcher("/settings.jsp").forward(request, response);
			}else{
				messages.add("このユーザーは登録されていません。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("managementUsers");
			}
		}else{
			messages.add("ユーザー情報の取得に失敗しました。");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("managementUsers");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();
		List<String> compMessage = new ArrayList<String>();

		HttpSession session = request.getSession();

		User settingUser = getSettingUser(request);
		request.setAttribute("settingUser", settingUser);

		if (isValid(request, messages) == true) {

			try {
				boolean passUpdateFlg = false;
				if(StringUtils.isBlank(request.getParameter("password"))){
					passUpdateFlg = false;
				} else {
					passUpdateFlg = true;
				}

				new UserService().update(settingUser, passUpdateFlg);

			} catch (NoRowsUpdatedRuntimeException e) {
				session.removeAttribute("settingUser");
				List<Branches> branches = new BranchesService().getBranches();
				request.setAttribute("branches", branches);

				List<JobTitles> JobTitles = new JobTitlesService().getJobTitles();
				request.setAttribute("JobTitles", JobTitles);

				String userId = request.getParameter("userId");
//				int userId = Integer.parseInt(request.getParameter("userId"));
				if(userId != null){
					User settingUserRe = new UserService().getUser(Integer.parseInt(userId));
					session.setAttribute("settingUser", settingUserRe);
				}
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				request.getRequestDispatcher("/settings").forward(request, response);
			}

				session.removeAttribute("settingUser");
				compMessage.add(request.getParameter("name")+"のユーザー情報の編集が完了しました。");
				session.setAttribute("compMessages", compMessage);
				response.sendRedirect("managementUsers");

		} else {
			List<Branches> branches = new BranchesService().getBranches();
			request.setAttribute("branches", branches);
			List<JobTitles> JobTitles = new JobTitlesService().getJobTitles();
			request.setAttribute("JobTitles", JobTitles);

			request.setAttribute("settingUser",settingUser );

			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/settings.jsp").forward(request, response);
		}
	}

	private User getSettingUser(HttpServletRequest request)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		User settingUser = (User) session.getAttribute("settingUser");

		settingUser.setName(request.getParameter("name"));
		settingUser.setLoginId(request.getParameter("loginId"));
		settingUser.setPassword(request.getParameter("password"));
		settingUser.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		settingUser.setJobTitleId(Integer.parseInt(request.getParameter("jobTitleId")));
		settingUser.setId(Integer.parseInt(request.getParameter("userId")));
		return settingUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String loginId = request.getParameter("loginId");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String checkpassword = request.getParameter("checkPassword");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int jobTitleId = Integer.parseInt(request.getParameter("jobTitleId"));
		int id = Integer.parseInt(request.getParameter("userId"));

		if (StringUtils.isBlank(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}
		if (!loginId.matches("^\\w{6,20}$")) {
			messages.add("ログインIDは半角英数字6文字以上20文字以内で入力してください");
		}
		UserCheckService userCheckService = new UserCheckService();
		User existLoginId = userCheckService.userCheckId(loginId);
		if(existLoginId == null){
		} else if(id != existLoginId.getId()) {
			messages.add("このログインIDは既に使われています");
		}

		if (StringUtils.isBlank(name) == true) {
			messages.add("アカウント名を入力してください");
		}
		if (10 < name.length()) {
			messages.add("アカウント名は10文字以下で入力してください。");
		}

		if(StringUtils.isBlank(password) && !StringUtils.isBlank(checkpassword)){
			messages.add("パスワードも入力してください");
		}
		if (!StringUtils.isBlank(password) && !password.matches("^\\w{6,255}$")) {
			messages.add("パスワードは半角英数字6文字以上255文字以内で入力してください");
		}
		User existPassword = userCheckService.userCheckPassword(password);
		if(existPassword == null){
		} else if(id != existPassword.getId()){
			messages.add("このパスワードは既に使われています");
		}
		if((!StringUtils.isBlank(password) && StringUtils.isBlank(checkpassword))||!password.equals(checkpassword)){
			messages.add("パスワードと確認用パスワードが一致しません");
		}

		if(branchId == 0){
			messages.add("所属の項目を選択してください");
		}
		if(jobTitleId == 0){
			messages.add("役職の項目を選択してください");
		}
		if(branchId == 1 && jobTitleId >= 3){
			messages.add("本社勤務者は役職に総務人事担当、情報管理担当以外はは選べません。");
		}
		if(branchId >= 2 && jobTitleId <= 2){
			messages.add("支店勤務者は役職に支店長、支店社員以外は選べません。");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
