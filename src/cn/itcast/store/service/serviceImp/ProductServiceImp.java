package cn.itcast.store.service.serviceImp;

import java.util.List;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.daoImp.ProductDaoImp;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.utils.BeanFactory;

public class ProductServiceImp implements ProductService {
	ProductDao ProductDao = (ProductDao) BeanFactory.createObject("ProductDao"); 
	
	@Override
	public List<Product> findHots() throws Exception {

		return ProductDao.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {

		return ProductDao.findNews();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		return ProductDao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception {
//		创建PageModel对象 目的:计算分页参数
		//统计当前分类下商品的个数
		int totalRecords = ProductDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum, totalRecords, 12);
//		关联集合
		List<Product> list = ProductDao.findProductsByCidWithPage(cid,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
//		关联url
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
//		创建PageModel对象 目的:计算分页参数
		int totalRecords = ProductDao.findTotalRecords();
		PageModel pm = new PageModel(curNum, totalRecords, 5);
		
//		关联集合
		List<Product> list = ProductDao.findAllProductsWithPage(pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
//		关联url	
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void saveProduct(Product product) throws Exception {

		ProductDao.saveProduct(product);
	}

}
