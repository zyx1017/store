package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;


public class IndexServlet extends BaseServlet {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//调用业务层查询最新商品,查询最热商品,返回2个集合
		ProductService ProductService = new ProductServiceImp();
		List<Product> list01 = ProductService.findHots();
		List<Product> list02 = ProductService.findNews();
		
		//将2个集合放入到request
		request.setAttribute("hots", list01);		
		request.setAttribute("news", list02);
		//转发到真实的首页
		return "/jsp/index.jsp";
	}
	
	
}
