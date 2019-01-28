package com.xiaoji.duan.abd.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ArrayUtils;

import com.alibaba.fastjson.JSONObject;
import com.xiaoji.duan.abd.entity.ABD_Group;
import com.xiaoji.duan.abd.entity.ABD_GroupSA;
import com.xiaoji.duan.abd.entity.ABD_GroupSAUser;
import com.xiaoji.duan.abd.entity.ABD_GroupUser;
import com.xiaoji.duan.abd.mapper.ABD_GroupMapper;
import com.xiaoji.duan.abd.mapper.ABD_GroupSAMapper;
import com.xiaoji.duan.abd.mapper.ABD_GroupSAUserMapper;
import com.xiaoji.duan.abd.mapper.ABD_GroupUserMapper;

@Service
public class ABDService {
	@Value("${picture.address}")
	private  String picture;
	private final ABD_GroupMapper groupMapper;
	private final ABD_GroupSAMapper groupSAMapper;
	private final ABD_GroupUserMapper groupUserMapper;
	private final ABD_GroupSAUserMapper groupSAUserMapper;
	
	public ABDService(ABD_GroupMapper groupMapper,
			ABD_GroupSAMapper groupSAMapper,
			ABD_GroupUserMapper groupUserMapper,
			ABD_GroupSAUserMapper groupSAUserMapper) {
		this.groupMapper = groupMapper;
		this.groupUserMapper = groupUserMapper;
		this.groupSAMapper = groupSAMapper;
		this.groupSAUserMapper = groupSAUserMapper;
	}
	
	public List<ABD_GroupUser> getGroupUsers(String subdomain) {
		return this.groupUserMapper.findByGroup(subdomain);
	}
	
	public List<ABD_GroupSAUser> getGroupSAUsers(String subdomain, String prefix) {
		return this.groupSAUserMapper.findByGroupSA(subdomain, prefix);
	}
	
	public List<ABD_Group> getGroupsByUser(String username) {
		return this.groupMapper.findByUser(username);
	}
	
	public ABD_Group getGroup(String subdomain) {
		return this.groupMapper.findByPK(subdomain);
	}
	
	public ABD_GroupSA getGroupSA(String subdomain, String prefix) {
		return this.groupSAMapper.findByPK(subdomain, prefix);
	}
	
	public ABD_GroupUser getGroupUser(String subdomain, String userId) {
		return this.groupUserMapper.findByPK(subdomain, userId);
	}
	
	public ABD_GroupSAUser getGroupSAUser(String subdomain, String prefix, String userId) {
		return this.groupSAUserMapper.findByPK(subdomain, prefix, userId);
	}
	
	public int addGroupUser(ABD_Group group, JSONObject user) {
		ABD_GroupUser groupUser = new ABD_GroupUser();
		
		groupUser.setGroupName(group.getName());
		groupUser.setGroupFullname(group.getFullName());
		groupUser.setGroupSubdomain(group.getSubDomain());
		groupUser.setUserName(user.getString("name"));
		groupUser.setUserId(user.getString("openid"));
		groupUser.setUserType("NORMAL");
		groupUser.setUserVerify("VERIFIED");

		return this.groupUserMapper.insert(groupUser);
	}
	
	public int addGroupVerifyUser(ABD_Group group, JSONObject user) {
		ABD_GroupUser groupUser = new ABD_GroupUser();
		
		groupUser.setGroupName(group.getName());
		groupUser.setGroupFullname(group.getFullName());
		groupUser.setGroupSubdomain(group.getSubDomain());
		groupUser.setUserName(user.getString("name"));
		groupUser.setUserId(user.getString("openid"));
		groupUser.setUserType("NORMAL");
		groupUser.setUserVerify("VERIFY");
		
		return this.groupUserMapper.insert(groupUser);
	}
	
	public int addGroupSAUser(ABD_GroupSA groupSA, JSONObject user) {
		ABD_GroupSAUser groupSAUser = new ABD_GroupSAUser();

		groupSAUser.setGroupName(groupSA.getGroupName());
		groupSAUser.setGroupFullname(groupSA.getGroupFullname());
		groupSAUser.setGroupSubdomain(groupSA.getGroupSubdomain());
		groupSAUser.setSaName(groupSA.getSaName());
		groupSAUser.setSaPrefix(groupSA.getSaPrefix());
		groupSAUser.setUserName(user.getString("name"));
		groupSAUser.setUserId(user.getString("openid"));
		groupSAUser.setUserType("NORMAL");
		groupSAUser.setUserVerify("VERIFIED");
		
		return this.groupSAUserMapper.insert(groupSAUser);
	}

	public int addGroupSAVerifyUser(ABD_GroupSA groupSA, JSONObject user) {
		ABD_GroupSAUser groupSAUser = new ABD_GroupSAUser();

		groupSAUser.setGroupName(groupSA.getGroupName());
		groupSAUser.setGroupFullname(groupSA.getGroupFullname());
		groupSAUser.setGroupSubdomain(groupSA.getGroupSubdomain());
		groupSAUser.setSaName(groupSA.getSaName());
		groupSAUser.setSaPrefix(groupSA.getSaPrefix());
		groupSAUser.setUserName(user.getString("name"));
		groupSAUser.setUserId(user.getString("openid"));
		groupSAUser.setUserType("NORMAL");
		groupSAUser.setUserVerify("VERIFY");

		return this.groupSAUserMapper.insert(groupSAUser);
	}
	/*添加组织信息*/
	@Transactional
	public String addGroup(List<MultipartFile> files, ABD_Group abd_group){
		String subDomain = abd_group.getSubDomain();

		if(subDomain.substring(0,1).equals("A") || subDomain.substring(0,1).equals("X") ){
			return "子域名格式不正确";
		}
		//判断组织名是否重复
		List<ABD_Group> list = this.groupMapper.findByUser(abd_group.getRegister());
		for(ABD_Group ag : list){
			if(ag.getName().equals(abd_group.getName())){
				return "组织已存在";
			}
		}
		//判断域名是否重复
		List<ABD_Group> subDomainList = this.groupMapper.findBySubDomain(abd_group.getSubDomain());
		if(subDomainList.size() > 0){
			return "域名已被使用";
		}

		String[] fileType = {"png","jpg","jpeg","gif"};
		//判断是否有文件
		if(files == null || !(files.size()>0)){
			return "文件未选择！";
		}
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(date);

		MultipartFile multipartFile = files.get(0);
		try{
			String newFileName = saveLogo(multipartFile,dateString);
			abd_group.setCreateTime(dateString);
			abd_group.setLogo(dateString+"/"+newFileName);
			groupMapper.insertGroup(abd_group);
			return "录入成功";
		}catch (SQLException e){
			return e.getMessage();
		}catch (Exception e){
			return e.getMessage();
		}

	}
	@Transactional
	public String saveLogo(MultipartFile file ,String date) throws Exception{
		String[] fileType = {"png","jpg","jpeg","gif"};
		String logoPath=picture+date;
		String oldName=file.getOriginalFilename();

		if(ArrayUtils.contains(fileType,oldName.substring(oldName.lastIndexOf(".")+1).toLowerCase())){
			String newoldName= UUID.randomUUID().toString()+"."+oldName.substring(oldName.lastIndexOf(".")+1);
			File targetFile = new File(logoPath);
			File logo = new File(logoPath,newoldName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				logo.createNewFile();
				file.transferTo(logo);
				return newoldName;
			} catch (IOException e) {
				throw new RuntimeException("文件保存失败");
			}

		}else{
			throw new RuntimeException("文件格式不匹配");
		}

	}
	public List<ABD_GroupSA> getSaByGroup(String subDomain){
		List<ABD_GroupSA> saList=groupMapper.findSaByGroup(subDomain);
		List<ABD_GroupSA> saOutList=new ArrayList<>();
		if(saList!=null&&saList.size()>0){
			for (ABD_GroupSA groupSa:saList) {
				Map<String,String> saAbout=groupMapper.findByFrefix(groupSa.getSaPrefix());
				if (saAbout != null) {
					groupSa.setLogoPath(saAbout.get("LOGO"));
					groupSa.setSaDescription(saAbout.get("DESCRIPTION"));
				}
				saOutList.add(groupSa);
			}
		}
		return saList;
	}

	public List<ABD_Group> getUserGroupInstances(String userId) {
		return this.groupMapper.findByUserId(userId);
	}

	public List<ABD_GroupSA> getUserGroupSAInstances(String subdomain, String userId) {
		return this.groupSAMapper.findByGroupUserId(subdomain, userId);
	}
	
	@Transactional
	public int updateGroupUserName(String subdomain, String userid, String username) {
		int effect = 0;
		System.out.println(subdomain + " - " + userid + " - " + username);
		effect = this.groupUserMapper.updateUserName(username, subdomain, userid);
		
		if (effect > 0)
			effect = this.groupSAUserMapper.updateGroupSAUserName(username, subdomain, userid);
		else
			this.groupSAUserMapper.updateGroupSAUserName(username, subdomain, userid);
		
		return effect;
	}
	
	public int addGroupSA(ABD_GroupSA groupsa) {
		return this.groupSAMapper.insert(groupsa);
	}
	
	public int deleteGroupSA(String subdomain, String prefix) {
		return this.groupSAMapper.delete(subdomain, prefix);
	}
	
	public int grantGroupUser(String subdomain, String userid, String userVerify) {
		return this.groupUserMapper.updateUserVerify(subdomain, userid, userVerify);
	}

	public int grantGroupSAUser(String subdomain, String prefix, String userid, String userVerify) {
		return this.groupSAUserMapper.updateGroupSAUserVerify(subdomain, prefix, userid, userVerify);
	}
	
	public int modifyGroupUser(String subdomain, String userid, String fieldName, String value) {
		String fieldWithValue = " " + fieldName + " = '" + value + "' ";
		return this.groupUserMapper.updateGroupUser(subdomain, userid, fieldWithValue);
	}

	public int modifyGroupSAUser(String subdomain, String prefix, String userid, String fieldName, String value) {
		String fieldWithValue = " " + fieldName + " = '" + value + "' ";
		return this.groupSAUserMapper.updateGroupSAUser(subdomain, prefix, userid, fieldWithValue);
	}
}
