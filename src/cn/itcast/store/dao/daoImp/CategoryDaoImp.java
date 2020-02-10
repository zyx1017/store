package cn.itcast.store.dao.daoImp;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.domain.Category;
import cn.itcast.store.utils.JDBCUtils;

public class CategoryDaoImp implements CategoryDao {

	@Override
	public List<Category> getAllCats() throws Exception {
		String sql = "SELECT * FROM category";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql,new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void addCategory(Category c) throws Exception {
		String sql = "INSERT INTO category VALUES(?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,c.getCid(),c.getCname());
	}

	@Override
	public Category findById(String cid) throws Exception {
		String sql = "SELECT * FROM category WHERE cid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql,new BeanHandler<Category>(Category.class),cid);
	}

	@Override
	public void updateCategory(Category category) throws Exception {
		String sql = "UPDATE category SET cname =? WHERE cid =?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,category.getCname(),category.getCid());
	}
	
	

}
