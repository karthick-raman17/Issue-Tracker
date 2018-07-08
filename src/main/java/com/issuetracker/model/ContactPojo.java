package com.issuetracker.model;

public class ContactPojo {
	
	String UUID ="";

	String loginId = "";

	String firstName = "";

	String lastName = "";

	String picture = "";

	String loginType = "";
	
	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uuid) {
		this.UUID = uuid;
	}
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return "ContactPojo [uuid=" + UUID + ", loginId=" + loginId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", picture=" + picture + ", loginType=" + loginType + "]";
	}

	

	

}
