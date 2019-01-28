package com.xiaoji.duan.abd.entity;

import javax.persistence.Transient;
import java.io.Serializable;

public class ABD_GroupSA implements Serializable {

	private static final long serialVersionUID = 3293182279187852257L;

	private String groupName;
	private String groupFullname;
	private String groupSubdomain;
	private String saName;
	private String saPrefix;
	private String saAuthorize;
	private String verify;
	private String verifyType;
	private String settings;
	private String createTime;
	@Transient
	private String logoPath;
	@Transient
	private String saDescription;

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getSaDescription() {
		return saDescription;
	}

	public void setSaDescription(String saDescription) {
		this.saDescription = saDescription;
	}

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

	public String getSaName() {
		return saName;
	}

	public void setSaName(String saName) {
		this.saName = saName;
	}

	public String getSaPrefix() {
		return saPrefix;
	}

	public void setSaPrefix(String saPrefix) {
		this.saPrefix = saPrefix;
	}

	public String getSaAuthorize() {
		return saAuthorize;
	}

	public void setSaAuthorize(String saAuthorize) {
		this.saAuthorize = saAuthorize;
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
		return "ABD_GroupSA [groupName=" + groupName + ", groupFullname=" + groupFullname + ", groupSubdomain="
				+ groupSubdomain + ", saName=" + saName + ", saPrefix=" + saPrefix + ", saAuthorize=" + saAuthorize
				+ ", verify=" + verify + ", verifyType=" + verifyType + ", createTime=" + createTime + ", logoPath="
				+ logoPath + ", saDescription=" + saDescription + "]";
	}

}
