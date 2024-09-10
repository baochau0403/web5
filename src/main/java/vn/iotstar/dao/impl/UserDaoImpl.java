package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.configs.DBConnectMySQL;
import vn.iotstar.dao.IUserDao;
import vn.iotstar.models.UserModel;

public class UserDaoImpl extends DBConnectMySQL implements IUserDao {
	

	public Connection conn=null;
	public PreparedStatement ps =null;
	public ResultSet rs=null;
	

	@Override
	public List<UserModel> findAll() {
		String sql ="select * from baochau";
		
		List<UserModel> list = new ArrayList<>();
		
		try {
			Connection conn = super.getDatabaseConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				list.add(new UserModel(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("fullname"), rs.getString("images")));
				
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<UserModel> findById (int id) 
	{
		String sql="SELECT* FROM baochau WHERE id=?";
		List<UserModel> list = new ArrayList<UserModel>();
		try {
			Connection conn = super.getDatabaseConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				UserModel model1=new UserModel();
				list.add(new UserModel(model1.getId(), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("fullname"), rs.getString("images")));
			}
			return list;

		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void insert(UserModel baochau) {
		
		String sql = "INSERT INTO baochau(id, username, email, password, fullname, images) VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			Connection conn = super.getDatabaseConnection();
			
			Object ps = conn.prepareStatement(sql);
			
			((PreparedStatement) ps).setInt(1, baochau.getId());
			((PreparedStatement) ps).setString(2, baochau.getUsername());
			((PreparedStatement) ps).setString(3, baochau.getEmail());
			((PreparedStatement) ps).setString(4, baochau.getPassword());
			((PreparedStatement) ps).setString(5, baochau.getFullname());
			((PreparedStatement) ps).setString(6, baochau.getImages());
			
			((PreparedStatement) ps).executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UserDaoImpl userDao = new UserDaoImpl();
		
		//userDao.insert(new UserModel(4, "bcd", "bcd@gmail.com", "567", "bcdefg",""));
		
		List<UserModel> list1=userDao.findById(3);
		
		//List<UserModel> list = userDao.findAll();
		
		for (UserModel user : list1) {
			System.out.println(user);
		}
		}
		
		


	@Override
	public List<UserModel> findByUserName(String name) {
		String sql="SELECT* FROM user WHERE username=?";
		List<UserModel> list =new ArrayList<UserModel>();
		try {
			conn= super.getDatabaseConnection();
			ps= conn.prepareStatement(sql);
			ps.setString(1, name);
			rs=ps.executeQuery();
			
			while(rs.next())
			{
				UserModel model1=new UserModel();
				list.add(new UserModel(model1.getId(), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("fullname"), rs.getString("image")));
			}
			return list;

		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	

	@Override
	public boolean checkExistEmail(String email) {
		UserModel user = null;
		user = this.findByEmail(email);
		if (user == null)
			return false;
		return true;

	}

	@Override
	public boolean checkExistUsername(String username) {
		UserModel user = null;
		if (this.findByUserName(username).size()==0)
			return false;
		user = this.findByUserName(username).get(0);
		if (user == null)
			return false;
		return true;
	}

	@Override
	public boolean checkExistPhone(String phone) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserModel findByEmail(String email) {
		String sql = "SELECT * tableUser WHERE email = ?";
		UserModel oneUser = new UserModel();

		try {
			conn = super.getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			while (rs.next()) {
				oneUser.setId(rs.getInt("id"));
				oneUser.setUsername(rs.getString("username"));
				oneUser.setFullname(rs.getString("fullname"));
				oneUser.setEmail(rs.getString("email"));
				oneUser.setPassword(rs.getString("password"));
				oneUser.setImages(rs.getString("image"));
			}
			return oneUser;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}


}
