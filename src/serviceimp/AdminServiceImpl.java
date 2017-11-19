package serviceimp;

import java.util.ArrayList;
import java.util.List;

import dao.UserDao;
import service.AdminService;

public class AdminServiceImpl implements AdminService {

	private UserDao UserDao;

	public UserDao getUserDao() {
		return UserDao;
	}

	public void setUserDao(UserDao userDao) {
		UserDao = userDao;
	}

	public AdminServiceImpl() {

	}

	public AdminServiceImpl(dao.UserDao userDao) {
		super();
		UserDao = userDao;
	}

	public boolean UserAllinfo(String user, String pwd) {
		// TODO Auto-generated method stub
		return UserDao.getLastInfo(user, pwd);
	}

	public void deleteAdminUserByID(List<Integer> list) {
		UserDao.deleteAdminUserByID(list);

	}

	public ArrayList FunctionInfo(String emai, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList DetailFunctionInfo(String emai, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	public String groupNamefromEmail(String emai, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList groupinfolist() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList BigFunction(int admintype) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList LittleFunctionInfo(int admintype) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deletefromid(int id, int groupid) {
		// TODO Auto-generated method stub

	}

}