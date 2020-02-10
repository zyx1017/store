package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Order;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.service.serviceImp.OrderServiceImp;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminOrderServlet extends BaseServlet {
	public String findOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		OrderService OrderService=new OrderServiceImp();
		
		String st=req.getParameter("state");
		List<Order> list=null;
		if(null==st||"".equals(st)){
			//获取到全部订单
			list=OrderService.findAllOrders();	
		}else{
			list=OrderService.findAllOrders(st);
		}
		//将全部订单放入request
		req.setAttribute("allOrders", list);
		//转发 /admin/order/list.jsp
		return "/admin/order/list.jsp";
	}
	
	//findOrderByOidWithAjax
		public String findOrderByOidWithAjax(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			//服务端获取到订单ID,
			String oid=req.getParameter("id");
			//查询这个订单下所有的订单项以及订单项对应的商品信息,返回集合
			OrderService OrderService=new OrderServiceImp();
			Order order=OrderService.findOrderByOid(oid);
			//将返回的集合转换为JSON格式字符串,响应到客户端
			String jsonStr=JSONArray.fromObject(order.getList()).toString();
			//响应到客户端
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().println(jsonStr);
			return null;
		}
		
		//updateOrderByOid
		public String updateOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			//获取订单ID
			String oid=req.getParameter("oid");
			//根据订单ID查询订单
			OrderService OrderService=new OrderServiceImp();
			Order order=OrderService.findOrderByOid(oid);
			//设置订单状态
			order.setState(3);
			//修改订单信息
			OrderService.updateOrder(order);
			//重新定向到查询已发货订单
			resp.sendRedirect("/store/AdminOrderServlet?method=findOrders&state=3");
			return null;
		}	

}
