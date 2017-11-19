package service;
import java.util.ArrayList;
import java.util.List;

import model.*;

/**
 * @author zhoulei
 *
 */
public interface AdminService {
	
	public boolean UserAllinfo(String user,String pwd);
	
	
	public void deleteAdminUserByID(List<Integer> list);
	
	
	public ArrayList FunctionInfo(String emai,String pwd);
	
	
	public ArrayList DetailFunctionInfo(String emai, String pwd);
	
	
	public String groupNamefromEmail(String emai, String pwd);
	
	
	public ArrayList groupinfolist();
	
	
	public ArrayList BigFunction(final int admintype);
	
	
	public ArrayList LittleFunctionInfo(final int admintype);

	
	public void deletefromid(final int id,final int groupid);
	
}
