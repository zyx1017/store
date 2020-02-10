package cn.itcast.store.service;

import java.util.List;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;

public interface ProductService {

	List<Product> findHots() throws Exception;

	List<Product> findNews() throws Exception;

	Product findProductByPid(String pid) throws Exception;

	PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception;

	PageModel findAllProductsWithPage(int curNum) throws Exception;

	void saveProduct(Product product) throws Exception;

}
