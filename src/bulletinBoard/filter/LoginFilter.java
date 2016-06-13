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

@WebFilter("/*")
public class LoginFilter implements Filter{

	public LoginFilter() {
//		System.out.println("LoginFilter");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
//		System.out.println("init");
	}

	public void destroy() {
//		System.out.println("destroy");
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

//		System.out.println("アクセス：" + uri + ":" + method);

		List<String> messages = new ArrayList<String>();

		if (uri.equals(contextPath + "/login.jsp") || uri.equals(contextPath + "/login")
				|| uri.equals(contextPath + "/css/style.css")){

		} else if(session == null || session.getAttribute("loginUser") == null) {

//			session.setAttribute("target", uri);
			messages.add("セッションが切れました。ログインをお願いします。");
			session.setAttribute("errorMessages", messages);

			((HttpServletResponse)response).sendRedirect("login");
			return;
		}

		// サーブレットの実行
		chain.doFilter(request, response);
	}

}


