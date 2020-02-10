package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminCategoryServlet extends BaseServlet {
	//findAllCats
		public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			//获取全部分类信息
			CategoryService CategoryService=new CategoryServiceImp();
			List<Category> list=CategoryService.getAllCats();
			//全部分类信息放入request
			req.setAttribute("allCats", list);
			//转发到/admin/category/list.jsp
			return "/admin/category/list.jsp";
		}

		//addCategoryUI
		public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			return "/admin/category/add.jsp";
		}
		
		//editUI
		public String editUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			String cid = req.getParameter("cid");
			CategoryService CategoryService = new CategoryServiceImp();
			Category c = CategoryService.findById(cid);
			req.setAttribute("category", c);
			
			return "/admin/category/edit.jsp";
		}
		
		//addCategory
		public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			//获取分类名称
			String cname=req.getParameter("cname");
			//创建分类ID
			String id=UUIDUtils.getId();
			Category c=new Category();
			c.setCid(id);
			c.setCname(cname);
			//调用业务层添加分类功能
			CategoryService CategoryService=new CategoryServiceImp();
			CategoryService.addCategory(c);
			//重定向到查询全部分类信息
			resp.sendRedirect("/store/AdminCategoryServlet?method=findAllCats");
			return null;
		}
		
		//editCategory
		public String editCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			//获取分类参数
			Category category = MyBeanUtils.populate(Category.class, req.getParameterMap());
			
			//调用业务层添加分类功能
			CategoryService CategoryService=new CategoryServiceImp();
			CategoryService.editCategory(category);
			//重定向到查询全部分类信息
			resp.sendRedirect("/store_v5/AdminCategoryServlet?method=findAllCats");
			return null;
		}
		
}
