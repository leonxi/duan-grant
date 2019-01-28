package com.xiaoji.duan.abd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xiaoji.duan.abd.entity.ABD_GroupUser;

@Mapper
public interface ABD_GroupUserMapper {

	@Select("SELECT GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, USER_NAME, USER_ID, USER_TYPE, USER_VERIFY, CREATE_TIME FROM ABD_GroupUser WHERE GROUP_SUBDOMAIN = #{subdomain} AND USER_ID = #{userId}")
	@Results({
		@Result(column="GROUP_NAME", property="groupName"),
		@Result(column="GROUP_FULLNAME", property="groupFullname"),
		@Result(column="GROUP_SUBDOMAIN", property="groupSubdomain"),
		@Result(column="USER_NAME", property="userName"),
		@Result(column="USER_ID", property="userId"),
		@Result(column="USER_TYPE", property="userType"),
		@Result(column="USER_VERIFY", property="userVerify"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public ABD_GroupUser findByPK(@Param("subdomain") String subdomain, @Param("userId") String userId);
	
	@Select("SELECT GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, USER_NAME, USER_ID, USER_TYPE, USER_VERIFY, CREATE_TIME FROM ABD_GroupUser WHERE GROUP_SUBDOMAIN = #{subdomain}")
	@Results({
		@Result(column="GROUP_NAME", property="groupName"),
		@Result(column="GROUP_FULLNAME", property="groupFullname"),
		@Result(column="GROUP_SUBDOMAIN", property="groupSubdomain"),
		@Result(column="USER_NAME", property="userName"),
		@Result(column="USER_ID", property="userId"),
		@Result(column="USER_TYPE", property="userType"),
		@Result(column="USER_VERIFY", property="userVerify"),
		@Result(column="CREATE_TIME", property="createTime")
	})
	public List<ABD_GroupUser> findByGroup(@Param("subdomain") String subdomain);
	
	@Insert("INSERT INTO ABD_GroupUser (GROUP_NAME, GROUP_FULLNAME, GROUP_SUBDOMAIN, USER_NAME, USER_ID, USER_TYPE, USER_VERIFY, CREATE_TIME) " +
			"VALUES(#{groupName}, #{groupFullname}, #{groupSubdomain}, #{userName}, #{userId}, #{userType}, #{userVerify}, Now())")
	public int insert(ABD_GroupUser group);
	
	@Update("UPDATE ABD_GroupUser SET USER_NAME = #{username} WHERE GROUP_SUBDOMAIN = #{subdomain} AND USER_ID = #{userid}")
	public int updateUserName(@Param("username") String username, @Param("subdomain") String subdomain, @Param("userid") String userid);

	@Update("UPDATE ABD_GroupUser SET USER_VERIFY = #{userVerify} WHERE GROUP_SUBDOMAIN = #{subdomain} AND USER_ID = #{userid}")
	public int updateUserVerify(@Param("subdomain") String subdomain, @Param("userid") String userid, @Param("userVerify") String userVerify);
	
	@Update("UPDATE ABD_GroupUser SET ${fieldWithValue} WHERE GROUP_SUBDOMAIN = #{subdomain} AND USER_ID = #{userid}")
	public int updateGroupUser(@Param("subdomain") String subdomain, @Param("userid") String userid, @Param("fieldWithValue") String fieldWithValue);
}
