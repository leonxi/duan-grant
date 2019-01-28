package com.xiaoji.duan.abd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoji.duan.abd.entity.ABD_Group;
import com.xiaoji.duan.abd.entity.ABD_GroupSA;
import com.xiaoji.duan.abd.entity.ABD_GroupSAUser;
import com.xiaoji.duan.abd.entity.ABD_GroupUser;
import com.xiaoji.duan.abd.service.ABDService;
import com.xiaoji.duan.abd.service.HttpService;
import com.xiaoji.duan.abd.utils.Utils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/grant")
public class ApiController {
    
	@Autowired
    private HttpService httpauth;
    
	@Autowired
	private ABDService abdService;
	
	@RequestMapping("{subdomain}/{prefix}/accessable")
	public Map<String, Object> accessable(HttpServletRequest req, @PathVariable("subdomain") String subdomain, @PathVariable("prefix") String prefix, @RequestBody(required = false) Map<String, Object> params) {
		
		JSONObject user = null;

		if (params != null && params.get("openid") != null && params.get("name") != null) {
			String openid = (String) ((ArrayList) params.get("openid")).get(0);
			String name = (String) ((ArrayList) params.get("name")).get(0);

			if (openid == null) {
				Cookie[] cookies = req.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						switch(cookie.getName()) {
						case "authorized_openid":
		    	            openid = cookie.getValue();
		    	            break;
						default:
							break;
						}
					}
				}

			}
			
			System.out.println(openid + " - " + name);
			
			if (!"".equals(openid) && openid != null && !"".equals(name) && name != null) {
				user = new JSONObject();
				user.put("openid", openid);
				user.put("name", name);
			}
		}
		
		Map<String, Object> accessable = new HashMap<String, Object>();
		accessable.put("code", "");
		accessable.put("message", "");
		
		boolean isLogin = false;
		boolean isAccessable = false;
		boolean isGroupUserApply = false;
		boolean isGroupSAUserApply = false;
		String verifyType = "WEIXIN";
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		// 查询组织
		ABD_Group group = this.abdService.getGroup(subdomain);

		if (group == null) {
			// 组织不存在
			accessable.put("code", "ABD_000001");
			accessable.put("message", "组织(" + subdomain + ")不存在！");
		}
		
		// 查询短应用
		ABD_GroupSA groupSA = this.abdService.getGroupSA(subdomain, prefix);
		
		if (group != null && groupSA == null) {
			// 短应用不存在
			accessable.put("code", "ABD_000002");
			accessable.put("message", "组织(" + subdomain + ")不存在短应用(" + prefix + ")。");
		}

		// 组织用户
		ABD_GroupUser groupUser = null;

		// 组织应用用户
		ABD_GroupSAUser groupSAUser = null;
		
		if (group != null && groupSA != null) {

			// 存在被访问的组织和组织短应用
			if (user == null) {

				// 未登录
				if ("AUTOVERIFY".equals(group.getVerify())
						&& !"AUTHORIZED".equals(groupSA.getSaAuthorize())
						&& "AUTOVERIFY".equals(groupSA.getVerify())) {
					// 自动验证组织
					isAccessable = true;
				} else {
					isLogin = true;
					verifyType = groupSA.getVerifyType();
				}
				
			} else {
				// 已登录
				groupUser = this.abdService.getGroupUser(subdomain, user.getString("openid"));
				
				groupSAUser = this.abdService.getGroupSAUser(subdomain, prefix, user.getString("openid"));
				
				if ("AUTOVERIFY".equals(group.getVerify())
						&& "AUTOVERIFY".equals(groupSA.getVerify())) {
					// 自动验证组织
					isAccessable = true;
					
					if (groupUser == null) {
						// 自动加入组织
						this.abdService.addGroupUser(group, user);
					}
					
					if (groupSAUser == null) {
						// 自动加入短应用
						this.abdService.addGroupSAUser(groupSA, user);
					}
					
				} else if ("AUTOVERIFY".equals(group.getVerify())
						&& !"AUTOVERIFY".equals(groupSA.getVerify())) {

					if (groupUser == null) {
						// 自动加入组织
						this.abdService.addGroupUser(group, user);
					}
					
					if (groupSAUser == null) {
						
						isGroupSAUserApply = true;
						
						// 申请加入组织短应用
						this.abdService.addGroupSAVerifyUser(groupSA, user);
					} else {
						if ("VERIFIED".equals(groupSAUser.getUserVerify())) {

							// 已通过加入组织短应用申请
							isAccessable = true;
						} else if ("VERIFY".equals(groupSAUser.getUserVerify())) {
							
							// 已申请加入组织短应用
							isGroupSAUserApply = true;
						}
					}
				} else if (!"AUTOVERIFY".equals(group.getVerify())
						&& "AUTOVERIFY".equals(groupSA.getVerify())) {
					
					if (groupUser == null) {
						isGroupUserApply = true;

						// 申请加入组织
						this.abdService.addGroupVerifyUser(group, user);
					} else {
						if ("VERIFIED".equals(groupUser.getUserVerify())) {
							
							// 已通过加入组织申请
							isAccessable = true;
							
							if (groupSAUser == null) {
								// 自动加入短应用
								this.abdService.addGroupSAUser(groupSA, user);
							}

						} else if ("VERIFY".equals(groupUser.getUserVerify())) {

							// 已申请加入组织
							isGroupUserApply = true;
						}
					}
				} else if (!"AUTOVERIFY".equals(group.getVerify())
						&& !"AUTOVERIFY".equals(groupSA.getVerify())) {
					
					if (groupUser == null) {
						isGroupUserApply = true;

						// 申请加入组织
						this.abdService.addGroupVerifyUser(group, user);
					} else {

						// 已申请过加入组织
						if ("VERIFIED".equals(groupUser.getUserVerify())) {
							
							// 已通过加入组织申请
							if (groupSAUser == null) {
								isGroupSAUserApply = true;
								
								// 申请加入短应用
								this.abdService.addGroupSAVerifyUser(groupSA, user);
							} else {
								if ("VERIFIED".equals(groupSAUser.getUserVerify())) {

									isAccessable = true;
								} else if ("VERIFY".equals(groupSAUser.getUserVerify())) {

									isGroupSAUserApply = true;
								}
							}

						} else if ("VERIFY".equals(groupUser.getUserVerify())) {

							// 已申请加入组织
							isGroupUserApply = true;
						}
					}
				}
			}
		}

		// 确认角色权限
		if (isAccessable) {
			// 请求参数没有请求URI
			if (params != null && params.get("uri") != null) {
				String uri = (String) ((ArrayList) params.get("uri")).get(0);
				System.out.println(subdomain + " - " + prefix + " : uri(" + uri + ")");

				// 请求参数没有请求URI
				if (uri != null && !"".equals(uri.trim())) {
					JSONObject shortapplication = this.httpauth.getJSONObject("http://sa-" + prefix.toLowerCase() + ":8080/" + prefix.toLowerCase() + "/shortapplication.json");
		
					// 请求短应用没有设置shortapplication.json文件
					if (shortapplication != null && shortapplication.containsKey("paths")) {
						System.out.println(shortapplication.toString());
						
						JSONArray paths = shortapplication.getJSONArray("paths");
						
						for (int i = 0; i < paths.size(); i++) {
							JSONObject path = paths.getJSONObject(i);
							
							String pathRegex = path.getString("path");
							
							if (Utils.urlPatternMatch(uri, pathRegex)) {
								JSONArray grants = path.getJSONArray("grants");
								
								// 没有访问角色设置，都可访问
								if (grants == null || grants.size() == 0) {
									break;
								}
								
								// 非登陆用户，无法访问
								if (groupSAUser == null) {
									
									isAccessable = false;
									isLogin = true;
									
								// 登陆用户，确认访问权限
								} else {
									String userType = groupSAUser.getUserType() == null ? "" : groupSAUser.getUserType().toLowerCase();
									boolean isGranted = false;
									
									for (int j = 0; j < grants.size(); j++) {
										JSONObject grant = grants.getJSONObject(j);
										String role = grant.getString("role");
										
										// 授权用户，可以访问
										if (userType.contains(role.toLowerCase())) {
											isGranted = true;
										}
									}
									
									if (!isGranted) {
										isAccessable = false;
										isGroupSAUserApply = true;
									}
								}
								
								break;
							}
						}
					}
				}
			}
		}
		
		// 设置访问权限结果
		if (isLogin) {
			data.put("tologin", true);
			data.put("verifyType", verifyType);
		} else {
			data.put("tologin", false);
		}
		
		if (isAccessable) {
			data.put("accessable", true);
		} else {
			data.put("accessable", false);
		}
		
		if (isGroupUserApply) {
			data.put("groupuser_apply", true);
		} else {
			data.put("groupuser_apply", false);
		}
		
		if (isGroupSAUserApply) {
			data.put("groupsauser_apply", true);
		} else {
			data.put("groupsauser_apply", false);
		}
		
		accessable.put("data", data);
		
		System.out.println(subdomain + " - " + prefix + " : isAccessable(" + isAccessable + "), isGroupUserApply(" + isGroupUserApply + "), isGroupSAUserApply(" + isGroupSAUserApply + "), isLogin(" + (isLogin ? (isLogin + " : " + verifyType) : isLogin) + ")");
		
		return accessable;
	}
}
