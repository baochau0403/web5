package serviceimpl;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.models.UserModel;
import vn.iotstar.services.UserService;

public class userServiceImpl implements UserService {

	IUserDao userDao = new UserDaoImpl();

	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.get(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public boolean register(String username, String email, String password, String fullname, String image) {

//		long millis = System.currentTimeMillis();
//		Date date = new Date(millis);
		userDao.insert(new UserModel(1, username, email, password, fullname,image));
		return true;
	}

	@Override
	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
	}

	@Override
	public UserModel get(String username) {
		return userDao.findByUserName(username).get(0);
	}

	

	@Override
	public boolean register(String email, String password, String username, String fullname) {
		return false;
	}

	@Override
	public boolean checkExistPhone(String phone) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insert(UserModel user) {
		// TODO Auto-generated method stub
		
	}

}
