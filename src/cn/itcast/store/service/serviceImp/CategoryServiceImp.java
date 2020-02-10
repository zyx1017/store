package cn.itcast.store.service.serviceImp;

import java.util.List;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.dao.daoImp.CategoryDaoImp;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.utils.BeanFactory;
import cn.itcast.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImp implements CategoryService {
	CategoryDao CategoryDao = (CategoryDao) BeanFactory.createObject("CategoryDao");
	@Override
	public List<Category> getAllCats() throws Exception {
		
		return CategoryDao.getAllCats();
	}

	@Override
	public void addCategory(Category c) throws Exception {
		
		CategoryDao.addCategory(c);
		//更新redis
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

	@Override
	public Category findById(String cid) throws Exception {
		return CategoryDao.findById(cid);
	}

	@Override
	public void editCategory(Category category) throws Exception {
		CategoryDao.updateCategory(category);
		//更新redis
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

}
