package cn.itcast.store.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.CookieSupport;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.CookUtils;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

    public LoginFilter() {}

	
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		//如果是登录页直接放行
		String servletPath = request.getServletPath();
		if(servletPath.startsWith("/UserServlet")){
			String method = request.getParameter("method");
			if("loginUI".equals(method)){
				chain.doFilter(request, response);
				return ;
			}
		}
		
		//用户登陆信息
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		//如果已经登陆则放行
		if(loginUser != null){
			chain.doFilter(request, response);
			return;
		}
		
		//获得自动登陆的Cookie
		Cookie userCookie = CookUtils.getCookieByName("autoLoginCookie", request.getCookies());
		
		//判断自动登陆cookie是否存在 若没有则无需自动登陆
		if(userCookie == null){
			chain.doFilter(request, response);
			return ;
		}
		
		//存在cookie
		String[] u = userCookie.getValue().split("@");
		String username = u[0];
		String password = u[1];
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		try {
			UserDao UserDao = new UserDaoImp();
			loginUser = UserDao.userLogin(user);
			if(loginUser == null){
				chain.doFilter(request, response);
				return;
			}
			
			request.getSession().setAttribute("loginUser", loginUser);
			chain.doFilter(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("自动登陆异常");
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
