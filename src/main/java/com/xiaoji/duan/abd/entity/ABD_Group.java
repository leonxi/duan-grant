package com.xiaoji.duan.abd.entity;

import java.io.Serializable;

public class ABD_Group implements Serializable {

	private static final long serialVersionUID = 3293182279187852257L;

	private String name;
	private String fullName;
	private String subDomain;
	private String logo;
	private String register;
	private String verify;
	private String verifyType;
	private String settings;
	private String saGroupAuthorize;
	private String createTime;
	public String getSaGroupAuthorize() {
		return saGroupAuthorize;
	}

	public void setSaGroupAuthorize(String saGroupAuthorize) {
		this.saGroupAuthorize = saGroupAuthorize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSubDomain() {
		return subDomain;
	}

	public void setSubDomain(String prefix) {
		this.subDomain = prefix;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String owner) {
		this.register = owner;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public String getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ABD_Group [name=" + name + ", fullName=" + fullName + ", subDomain=" + subDomain + ", logo=" + logo
				+ ", register=" + register + ", verify=" + verify + ", verifyType=" + verifyType + ", saGroupAuthorize="
				+ saGroupAuthorize + ", createTime=" + createTime + "]";
	}

}
