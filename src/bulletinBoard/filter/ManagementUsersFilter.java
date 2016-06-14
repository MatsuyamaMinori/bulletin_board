package bulletinBoard.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulletinBoard.beans.User;

@WebFilter(urlPatterns = {"/*"})
public class ManagementUsersFilter implements Filter{

	public ManagementUsersFilter() {
		System.out.println("ManagementUsersFilter");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("init");
	}

	public void destroy() {
		System.out.println("destroy");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		String contextPath = req.getContextPath();
		String uri = req.getRequestURI();
//		String method = req.getMethod();
//
//		System.out.println("アクセス：" + uri + ":" + method);

		List<String> authorityMessages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		if ((uri.equals(contextPath + "/managementUsers") || uri.equals(contextPath + "/signup") || uri.equals(contextPath + "/settings"))
				&& (user.getJobTitleId() != 1 || user == null)){
			authorityMessages.add("権限がありません。");
			session.setAttribute("authorityMessages", authorityMessages);

			((HttpServletResponse)response).sendRedirect("top");
			return;
		}

		chain.doFilter(request, response);
	}

}