package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	//findProductByPid
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {

//		获取商品pid
		String pid = request.getParameter("pid");
//		根据商品pid查询商品信息
		ProductService ProductService = new ProductServiceImp();
		Product product = ProductService.findProductByPid(pid);
//		将获取到的商品放入request
		request.setAttribute("product", product);
		return "/jsp/product_info.jsp";
	
	}

	//findProductsByCidWithPage
	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//	    获取cid,num
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));//当前页码 一般第一次为1
//	    调用业务层功能:以分页形式查询当前类别下商品信息
//	    返回PageModel对象(1_当前页商品信息2_分页3_url)
		ProductService ProductService = new ProductServiceImp();
		PageModel pm = ProductService.findProductsByCidWithPage(cid,curNum);
		
//	    将PageModel对象放入request
//	     转发到/jsp/product_list.jsp
		request.setAttribute("page", pm);
		return "/jsp/product_list.jsp";
	
	}
	

	
}
