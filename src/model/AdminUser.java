package model;

import java.util.HashSet;
import java.util.Set;

/**
 * AdminUser entity. @author MyEclipse Persistence Tools
 */

public class AdminUser implements java.io.Serializable {

	// Fields

	private Integer adminUserId;
	private String username;
	private String password;
	
	// Constructors
	public AdminUser() {
		
	}

	public AdminUser(Integer adminUserId, String username, String password) {
		super();
		this.adminUserId = adminUserId;
		this.username = username;
		this.password = password;
	}

	public Integer getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AdminUser [adminUserId=" + adminUserId + ", username=" + username + ", password=" + password + "]";
	}


	
}