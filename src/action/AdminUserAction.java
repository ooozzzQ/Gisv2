package action;

import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import service.AdminService;

public class AdminUserAction extends ActionSupport {
	private HttpServletRequest request;
	private int admintype;
	private String LittleFunctionId;
	private int BigFunctionId;
	private String certinum;
	private AdminService adminService;
	private String username;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	private String adminUserIds; // ����Ա�û���id

	public String getLittleFunctionId() {
		return LittleFunctionId;
	}

	public void setLittleFunctionId(String littleFunctionId) {
		LittleFunctionId = littleFunctionId;
	}

	public int getBigFunctionId() {
		return BigFunctionId;
	}

	public void setBigFunctionId(int bigFunctionId) {
		BigFunctionId = bigFunctionId;
	}

	public int getAdmintype() {
		return admintype;
	}

	public void setAdmintype(int admintype) {
		this.admintype = admintype;
	}


	@Override
	public String execute() throws Exception {

		return "";
	}

	/*
	 * @Description:��¼��֤�����Լ���̨Ȩ��action
	 * 
	 * 
	 * @date March 21, 2013 11:02:13 AM
	 * 
	 * @version
	 */
	public String logincheck() {
		String msg;
		String str = (String) ActionContext.getContext().getSession().get("certCode");
		if (!certinum.equalsIgnoreCase(str)) {
			msg = "验证码错误！";
			ActionContext.getContext().put("msg", msg);
			return "loginerror";
		}
		this.request = ServletActionContext.getRequest();
		ActionContext context = ActionContext.getContext();
		Map params = context.getParameters();
		String username = ((String[]) params.get("username"))[0];
		String password = ((String[]) params.get("pwd"))[0];
		/*
		 * if((password.indexOf("or")!=-1)||(password.indexOf("'")!=-1)||(username
		 * .indexOf(" ")!=-1)){ msg="��֤�����";
		 * ActionContext.getContext().put("msg", msg); return "loginerror"; }
		 */

		if ((adminService.UserAllinfo(username, password) == true)) {
			this.request = ServletActionContext.getRequest();
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("password", password);
			return "loginsuccess";
		}
		msg = "用户名或密码错误！";
		ActionContext.getContext().put("msg", msg);
		return "loginerror";
	}

	public String getAdminUserIds() {
		return adminUserIds;
	}

	public void setAdminUserIds(String adminUserIds) {
		this.adminUserIds = adminUserIds;
	}

}