package cn.itcast.store.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
/**
 * 统一编码
 * @author Administrator
 *
 */
public class EncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		//1.强转
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) resp;
		//System.out.println("@@@@@@@@@@@@@@@@@@");		
		//2.放行
		chain.doFilter(new MyRequest(request), response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}

//之前的MyRequest增强了request.getParameter("name");方法
//增强了所有的获取参数的方法request.getParameterValues("name");
//增强了所有的获取参数的方法request.getParameterMap();
class MyRequest extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	private boolean flag=true;
	
	
	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request=request;
	}
	
	@Override
	public String getParameter(String name) {  
		if(name==null || name.trim().length()==0){
			return null;
		}
		String[] values = getParameterValues(name);
		if(values==null || values.length==0){
			return null;
		}
		
		return values[0];
	}
	
	@Override
	/**
	 * hobby=[eat,drink]
	 */
	public String[] getParameterValues(String name) {
		if(name==null || name.trim().length()==0){
			return null;
		}
		Map<String, String[]> map = getParameterMap();
		if(map==null || map.size()==0){
			return null;
		}
		
		return map.get(name);
	}
	
	@Override
	/**
	 * map{ username=[tom],password=[123],hobby=[eat,drink]}
	 */
	public Map<String,String[]> getParameterMap() {  
		
		/**
		 * 首先判断请求方式
		 * 若为post  request.setchar...(utf-8)
		 * 若为get 将map中的值遍历编码就可以�?
		 */
		String method = request.getMethod();
		if("post".equalsIgnoreCase(method)){
			try {
				request.setCharacterEncoding("utf-8");
				return request.getParameterMap();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("get".equalsIgnoreCase(method)){
			Map<String,String[]> map = request.getParameterMap();
			if(flag){
				for (String key:map.keySet()) {
					String[] arr = map.get(key);
					//继续遍历数组
					for(int i=0;i<arr.length;i++){
						//编码
						try {
							arr[i]=new String(arr[i].getBytes("iso-8859-1"),"utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
				flag=false;
			}
			//�?要遍历map 修改value的每�?个数据的编码
			
			return map;
		}
		
		return super.getParameterMap();
	}
	
}