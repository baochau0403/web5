package vn.iotstar.services;

import vn.iotstar.models.UserModel;

public interface UserService {

	UserModel login(String username, String password);
	UserModel get(String username);
	
	boolean register(String email, String password, String username, String
	fullname);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	boolean register(String email, String password, String username, String fullname, String phone);
	void insert(UserModel user);
	
	
}
