package com.temples.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
	@Setter
	@Getter
	private String userName;
	private String password;
	
	public UserDto(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public UserDto() {
		super();
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
	
	

}
