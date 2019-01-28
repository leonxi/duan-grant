package com.xiaoji.duan.abd.entity;

import java.io.Serializable;

public class ABD_GroupUser implements Serializable {

	private static final long serialVersionUID = 3293182279187852257L;

	private String groupName;
	private String groupFullname;
	private String groupSubdomain;
	private String userName;
	private String userId;
	private String userType;
	private String userVerify;
	private String createTime;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupFullname() {
		return groupFullname;
	}

	public void setGroupFullname(String groupFullname) {
		this.groupFullname = groupFullname;
	}

	public String getGroupSubdomain() {
		return groupSubdomain;
	}

	public void setGroupSubdomain(String groupSubdomain) {
		this.groupSubdomain = groupSubdomain;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserVerify() {
		return userVerify;
	}

	public void setUserVerify(String userVerify) {
		this.userVerify = userVerify;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ABD_GroupUser [groupName=" + groupName + ", groupFullname=" + groupFullname + ", groupSubdomain="
				+ groupSubdomain + ", userName=" + userName + ", userId=" + userId + ", userType=" + userType
				+ ", userVerify=" + userVerify + ", createTime=" + createTime + "]";
	}

}
