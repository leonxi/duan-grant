package com.xiaoji.duan.abd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.xiaoji.duan.abd.entity.ABD_GroupSA;

@Mapper
public interface ABD_GroupSAMapper {

	@Select("SELECT GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, SA_NAME, SA_PREFIX, SA_AUTHORIZE, VERIFY, VERIFYTYPE, SETTINGS, CREATE_TIME FROM ABD_GroupSA WHERE GROUP_SUBDOMAIN = #{subdomain} AND SA_PREFIX = #{prefix}")
	@Results({
		@Result(column="GROUP_NAME", property="groupName"),
		@Result(column="GROUP_FULLNAME", property="groupFullname"),
		@Result(column="GROUP_SUBDOMAIN", property="groupSubdomain"),
		@Result(column="SA_NAME", property="saName"),
		@Result(column="SA_PREFIX", property="saPrefix"),
		@Result(column="SA_AUTHORIZE", property="saAuthorize"),
		@Result(column="VERIFY", property="verify"),
		@Result(column="VERIFYTYPE", property="verifyType"),
		@Result(column="SETTINGS", property="settings"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public ABD_GroupSA findByPK(@Param("subdomain") String subdomain, @Param("prefix") String prefix);
	
	@Select("SELECT GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, SA_NAME, SA_PREFIX, SA_AUTHORIZE, VERIFY, VERIFYTYPE, SETTINGS, CREATE_TIME FROM ABD_GroupSA WHERE GROUP_SUBDOMAIN = #{subdomain} AND SA_PREFIX IN (SELECT SA_PREFIX FROM ABD_GroupSAUser WHERE USER_ID = #{userId})")
	@Results({
		@Result(column="GROUP_NAME", property="groupName"),
		@Result(column="GROUP_FULLNAME", property="groupFullname"),
		@Result(column="GROUP_SUBDOMAIN", property="groupSubdomain"),
		@Result(column="SA_NAME", property="saName"),
		@Result(column="SA_PREFIX", property="saPrefix"),
		@Result(column="SA_AUTHORIZE", property="saAuthorize"),
		@Result(column="VERIFY", property="verify"),
		@Result(column="VERIFYTYPE", property="verifyType"),
		@Result(column="SETTINGS", property="settings"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public List<ABD_GroupSA> findByGroupUserId(@Param("subdomain") String subdomain, @Param("userId") String userId);

	@Insert("INSERT INTO ABD_GroupSA (GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, SA_NAME, SA_PREFIX, SA_AUTHORIZE, VERIFY, CREATE_TIME) " +
			"VALUES(#{groupName}, #{groupFullname}, #{groupSubdomain}, #{saName}, #{saPrefix}, #{saAuthorize}, #{verify}, Now())")
	public int insert(ABD_GroupSA groupSA);
	
	@Delete("DELETE FROM ABD_GroupSA WHERE GROUP_SUBDOMAIN = #{subdomain} AND SA_PREFIX = #{prefix}")
	public int delete(@Param("subdomain") String subdomain, @Param("prefix") String prefix);
}
