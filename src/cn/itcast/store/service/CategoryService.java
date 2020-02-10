package cn.itcast.store.service;

import java.util.List;

import cn.itcast.store.domain.Category;

public interface CategoryService {

	List<Category> getAllCats() throws Exception;

	void addCategory(Category c) throws Exception;

	Category findById(String cid)throws Exception;

	void editCategory(Category category)throws Exception;

}
