package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取redis中的全部分类 若redis中没有则取数据库中
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if(null == jsonStr || "".equals(jsonStr)){
			//调用业务层获取全部分类
			CategoryService CategoryService = new CategoryServiceImp();
			List<Category> list = CategoryService.getAllCats();
			//将全部分类转换为JSON格式的数据
			jsonStr = JSONArray.fromObject(list).toString();
			jedis.set("allCats", jsonStr);
			System.out.println("redis中无数据");
			//将全部分类信息响应到客户端
			//告诉浏览器本次响应的是json格式的字符串
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().println(jsonStr);
		}else{
			
			//将全部分类信息响应到客户端
			//告诉浏览器本次响应的是json格式的字符串
			System.out.println("redis中有数据");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().println(jsonStr);
		}
		JedisUtils.closeJedis(jedis);
		
		return null;
	}
}
