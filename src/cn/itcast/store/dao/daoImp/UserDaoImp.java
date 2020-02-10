package cn.itcast.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;

public class UserDaoImp implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
		String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelphone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		qr.update(sql,params);
	}

	@Override
	public User userActive(String code) throws SQLException {
		String sql = "SELECT * FROM USER WHERE code = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query(sql, new BeanHandler<User>(User.class),code);
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "UPDATE USER SET username=?, password=?, name=?, email=?, telephone=?, birthday=?, sex=?, state=?, code=? WHERE uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelphone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
		qr.update(sql,params);
	}

	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "SELECT * FROM USER WHERE username=? and password=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class) ,user.getUsername(),user.getPassword());
		
	}

	@Override
	public User findByUsername(String username) throws SQLException {
		String sql = "SELECT * FROM USER WHERE username=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User exitUser = qr.query(sql, new BeanHandler<User>(User.class),username);
		return exitUser;
	}

}
