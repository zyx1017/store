package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.UserServiceImp;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
	
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	
	public String firstUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/index.jsp";
	}
	
	//userRegist
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		接收表单参数
		Map<String, String[]> map = request.getParameterMap();
		User user  = new User();
		MyBeanUtils.populate(user, map);
		//为用户其它属性赋值
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		
		System.out.println(user);
		
//		调用业务层注册功能
		UserService UserService = new UserServiceImp();
		try {
			UserService.userRegist(user);
//			注册成功,向用户邮箱发送信息,跳转到提示页面
			//发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活");
			} catch (Exception e) {
//				注册失败,跳转到提示页面
				request.setAttribute("msg", "用户注册失败，请重新注册");
			}		
		return "/jsp/info.jsp";
	}
	
	//激活用户
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取激活码
		String code = request.getParameter("code");
		//调用业务层的激活功能
		UserService UserService = new UserServiceImp();
		
		try {
			//激活的信息提示
			boolean flag = UserService.userActive(code);
			if(flag==true){
				//激活成功，向request放入提示信息，转发到登陆页面
				request.setAttribute("msg", "用户激活成功，请登录！");
				
				return "/jsp/login.jsp";
			}else{
				//激活失败，向request放入提示信息
				request.setAttribute("msg", "用户激活失败，请重新激活！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/jsp/info.jsp";
	}
	
	
	//userLogin
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取业务数据
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		//调用业务层登录功能
		UserService UserService = new UserServiceImp();
		User user02 = null;
		try{
			user02 = UserService.userLogin(user);
			//用户登陆成功，将用户信息放入session
			request.getSession().setAttribute("loginUser", user02);
			
			//自动登陆
			String autoLogin = request.getParameter("autoLogin");
			if("1".equals(autoLogin)){
				Cookie autoLoginCookie = new Cookie("autoLoginCookie", user.getUsername()+"@"+user.getPassword());
				autoLoginCookie.setPath("/");
				autoLoginCookie.setMaxAge(60*60*24*7);
				response.addCookie(autoLoginCookie);
			}else{
				Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
				autoLoginCookie.setPath("/");
				autoLoginCookie.setMaxAge(0);
				response.addCookie(autoLoginCookie);
			}
			
			//记住用户名
			String rememberme = request.getParameter("rememberme");
			if("1".equals(rememberme)){
				Cookie remembermeCookie = new Cookie("remembermeCookie", user.getUsername());
				remembermeCookie.setPath("/");
				remembermeCookie.setMaxAge(60*60*24*7);
				response.addCookie(remembermeCookie);
			}else{
				//清除cookie
				Cookie newCookie = new Cookie("remembermeCookie", "");
				newCookie.setMaxAge(0);
				newCookie.setPath("/");
				response.addCookie(newCookie);
			}
			
			response.sendRedirect("/store/index.jsp");
			return null;
		}catch (Exception e) {
			//用户登陆失败
			String msg = e.getMessage();
			System.out.println(msg);
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
	}
	
	//logOut
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//清除session
		request.getSession().invalidate();
		//清除cookie
		Cookie newCookie = new Cookie("autoLoginCookie", "");
		newCookie.setMaxAge(0);
		newCookie.setPath("/");
		response.addCookie(newCookie);
		//重新定向到首页
		response.sendRedirect("/store/index.jsp");
		return null;
	}
	
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		UserService UserService = new UserServiceImp();
		
		try {
			User existUser = UserService.findByUsername(username);
			if(existUser ==null){
				response.getWriter().println(1);
			}else{
				response.getWriter().println(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
