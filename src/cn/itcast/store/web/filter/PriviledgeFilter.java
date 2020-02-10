package cn.itcast.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.User;


public class PriviledgeFilter implements Filter {

   
    public PriviledgeFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		//判断当前session中是否存在已经登陆的用户
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		//用户登陆信息
				User loginUser = (User)request.getSession().getAttribute("loginUser");
				//如果已经登陆则放行
				if(loginUser != null){
					chain.doFilter(request, response);
					return;
				}else{
					request.setAttribute("msg", "请先登陆");
					request.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
				}
		
		//chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
