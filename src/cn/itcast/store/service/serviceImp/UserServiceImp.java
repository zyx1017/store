package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.utils.BeanFactory;

public class UserServiceImp implements UserService {
	UserDao UserDao = (UserDao) BeanFactory.createObject("UserDao");
	
	//实现注册功能
	@Override
	public void userRegist(User user) throws SQLException {
		UserDao.userRegist(user) ;
	}
	
	//用户激活
	@Override
	public boolean userActive(String code) throws SQLException {
		User user = UserDao.userActive(code);
		if(null!=user){
			//修改用户状态，清楚激活码
			user.setState(1);
			user.setCode(null);
			//对数据库执行更新
			UserDao.updateUser(user);
			return true;
		}else{
			return false;
		}
	}

	//用户登录
	@Override
	public User userLogin(User user) throws SQLException {
		User uu = UserDao.userLogin(user);
		//自定义异常
		if(null==uu){
			throw new RuntimeException("密码有误!");
		}else if(uu.getState() ==0){
			throw new RuntimeException("用户未激活!");
		}else{
			return uu;
		}
	}

	@Override
	public User findByUsername(String username) throws SQLException {
		return UserDao.findByUsername(username);
	}

}
