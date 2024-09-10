package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.UserModel;

public interface IUserDao {
	List<UserModel> findAll();
	
	List<UserModel> findById(int id);
	
	void insert(UserModel user);

	List<UserModel> findByUserName(String name);
	
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	UserModel findByEmail(String email);
}
