package com.xiaoji.duan.abd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xiaoji.duan.abd.entity.ABD_GroupSAUser;

@Mapper
public interface ABD_GroupSAUserMapper {

	@Select("SELECT GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, SA_NAME, SA_PREFIX, USER_NAME, USER_ID, USER_TYPE, USER_VERIFY, CREATE_TIME FROM ABD_GroupSAUser WHERE GROUP_SUBDOMAIN = #{subdomain} AND SA_PREFIX = #{prefix} AND USER_ID = #{userId}")
	@Results({
		@Result(column="GROUP_NAME", property="groupName"),
		@Result(column="GROUP_FULLNAME", property="groupFullname"),
		@Result(column="GROUP_SUBDOMAIN", property="groupSubdomain"),
		@Result(column="SA_NAME", property="saName"),
		@Result(column="SA_PREFIX", property="saPrefix"),
		@Result(column="USER_NAME", property="userName"),
		@Result(column="USER_ID", property="userId"),
		@Result(column="USER_TYPE", property="userType"),
		@Result(column="USER_VERIFY", property="userVerify"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public ABD_GroupSAUser findByPK(@Param("subdomain") String subdomain, @Param("prefix") String prefix, @Param("userId") String userId);
	
	@Select("SELECT GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, SA_NAME, SA_PREFIX, USER_NAME, USER_ID, USER_TYPE, USER_VERIFY, CREATE_TIME FROM ABD_GroupSAUser WHERE GROUP_SUBDOMAIN = #{subdomain} AND SA_PREFIX = #{prefix}")
	@Results({
		@Result(column="GROUP_NAME", property="groupName"),
		@Result(column="GROUP_FULLNAME", property="groupFullname"),
		@Result(column="GROUP_SUBDOMAIN", property="groupSubdomain"),
		@Result(column="SA_NAME", property="saName"),
		@Result(column="SA_PREFIX", property="saPrefix"),
		@Result(column="USER_NAME", property="userName"),
		@Result(column="USER_ID", property="userId"),
		@Result(column="USER_TYPE", property="userType"),
		@Result(column="USER_VERIFY", property="userVerify"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public List<ABD_GroupSAUser> findByGroupSA(@Param("subdomain") String subdomain, @Param("prefix") String prefix);
	
	@Insert("INSERT INTO ABD_GroupSAUser (GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, SA_NAME, SA_PREFIX, USER_NAME, USER_ID, USER_TYPE, USER_VERIFY, CREATE_TIME) " +
			"VALUES(#{groupName}, #{groupFullname}, #{groupSubdomain}, #{saName}, #{saPrefix}, #{userName}, #{userId}, #{userType}, #{userVerify}, Now())")
	public int insert(ABD_GroupSAUser group);
	
	@Update("UPDATE ABD_GroupSAUser SET USER_NAME = #{username} WHERE GROUP_SUBDOMAIN = #{subdomain} AND USER_ID = #{userid}")
	public int updateGroupSAUserName(@Param("username") String username, @Param("subdomain") String subdomain, @Param("userid") String userid);

	@Update("UPDATE ABD_GroupSAUser SET USER_VERIFY = #{userVerify} WHERE GROUP_SUBDOMAIN = #{subdomain} AND SA_PREFIX = #{prefix} AND USER_ID = #{userid}")
	public int updateGroupSAUserVerify(@Param("subdomain") String subdomain, @Param("prefix") String prefix, @Param("userid") String userid, @Param("userVerify") String userVerify);

	@Update("UPDATE ABD_GroupSAUser SET ${fieldWithValue} WHERE GROUP_SUBDOMAIN = #{subdomain} AND SA_PREFIX = #{prefix} AND USER_ID = #{userid}")
	public int updateGroupSAUser(@Param("subdomain") String subdomain, @Param("prefix") String prefix, @Param("userid") String userid, @Param("fieldWithValue") String fieldWithValue);
}
