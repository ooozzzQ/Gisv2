package dao;

import java.util.List;

public interface UserDao {
	public boolean getLastInfo( final String user,final String pwd);
	
	public void deleteAdminUserByID(List<Integer> list);
}
