package com.xiaoji.duan.abd.mapper;

import java.util.List;
import java.util.Map;

import com.xiaoji.duan.abd.entity.ABD_GroupSA;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.xiaoji.duan.abd.entity.ABD_Group;

@Mapper
public interface ABD_GroupMapper {

	@Select("SELECT NAME, FULLNAME, SUBDOMAIN, LOGO, REGISTER, VERIFY, VERIFYTYPE, SETTINGS, CREATE_TIME FROM ABD_Group WHERE SUBDOMAIN = #{subdomain}")
	@Results({
		@Result(column="NAME", property="name"),
		@Result(column="FULLNAME", property="fullName"),
		@Result(column="SUBDOMAIN", property="subDomain"),
		@Result(column="LOGO", property="logo"),
		@Result(column="REGISTER", property="register"),
		@Result(column="VERIFY", property="verify"),
		@Result(column="VERIFYTYPE", property="verifyType"),
		@Result(column="SETTINGS", property="settings"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public ABD_Group findByPK(@Param("subdomain") String subdomain);

	@Select("SELECT NAME, FULLNAME, SUBDOMAIN, LOGO, REGISTER, VERIFY, VERIFYTYPE, SETTINGS, CREATE_TIME FROM ABD_Group WHERE REGISTER = #{username} ORDER BY CREATE_TIME DESC")
	@Results({
		@Result(column="NAME", property="name"),
		@Result(column="FULLNAME", property="fullName"),
		@Result(column="SUBDOMAIN", property="subDomain"),
		@Result(column="LOGO", property="logo"),
		@Result(column="REGISTER", property="register"),
		@Result(column="VERIFY", property="verify"),
		@Result(column="VERIFYTYPE", property="verifyType"),
		@Result(column="SETTINGS", property="settings"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public List<ABD_Group> findByUser(@Param("username") String username);

	@Select("SELECT NAME, FULLNAME, SUBDOMAIN, LOGO, REGISTER, VERIFY, VERIFYTYPE, SETTINGS, CREATE_TIME FROM ABD_Group WHERE SUBDOMAIN IN (SELECT GROUP_SUBDOMAIN FROM ABD_GroupUser WHERE USER_ID = #{userId})")
	@Results({
		@Result(column="NAME", property="name"),
		@Result(column="FULLNAME", property="fullName"),
		@Result(column="SUBDOMAIN", property="subDomain"),
		@Result(column="LOGO", property="logo"),
		@Result(column="REGISTER", property="register"),
		@Result(column="VERIFY", property="verify"),
		@Result(column="VERIFYTYPE", property="verifyType"),
		@Result(column="SETTINGS", property="settings"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public List<ABD_Group> findByUserId(@Param("userId") String userId);

	@Insert("INSERT INTO ABD_Group (NAME, FULLNAME, SUBDOMAIN, LOGO, REGISTER, VERIFY, CREATE_TIME) " +
			"VALUES(#{name}, #{fullName}, #{subDomain}, #{logo}, #{register}, ${verify}, Now())")
	public int insert(ABD_Group group);
	@Select("SELECT NAME, FULLNAME, SUBDOMAIN, LOGO, REGISTER, CREATE_TIME FROM ABD_Group WHERE SUBDOMAIN = #{domain}")
	public List<ABD_Group> findBySubDomain(@Param("domain") String subDomain);
	@Insert("INSERT INTO ABD_GROUPSA (GROUP_NAME,GROUP_FULLNAME,GROUP_SUBDOMAIN,SA_NAME,SA_PREFIX,VERIFY,CREATE_TIME) " +
			"VALUES(#{groupName},#{groupFullName},#{groupSubDomain},#{saName},#{saPrefix},#{verify},#{createTime})")
	public int insertGroupSa(ABD_GroupSA abd_groupSa);
	@Insert("INSERT INTO ABD_Group (NAME, FULLNAME, SUBDOMAIN, LOGO, REGISTER, AUTHORIZE, CREATE_TIME) " +
			"VALUES(#{name}, #{fullName}, #{subDomain}, #{logo}, #{register}, #{saGroupAuthorize},#{createTime} )")
	public int insertGroup(ABD_Group group);
	@Select("SELECT LOGO,DESCRIPTION FROM ABC_ShortApplication WHERE PREFIX = #{prefix}")
	public Map<String,String> findByFrefix(@Param("prefix") String saPerfiex);
	@Select("select * from ABD_GROUPSA WHERE GROUP_SUBDOMAIN = #{subDomain}")
	@Results({
			@Result(column="GROUP_NAME", property="groupName"),
			@Result(column="GROUP_FULLNAME", property="groupFullname"),
			@Result(column="GROUP_SUBDOMAIN", property="groupSubdomain"),
			@Result(column="SA_NAME", property="saName"),
			@Result(column="SA_PREFIX", property="saPrefix"),
			@Result(column="USER_VERIFY", property="userVerify"),
			@Result(column="SETTINGS", property="settings"),
			@Result(column="CREATE_TIME", property="createTime")
	})
	public List<ABD_GroupSA> findSaByGroup(String subDomain);
}
