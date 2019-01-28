package com.xiaoji.duan.abd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiaoji.duan.abd.service.ABDService;
import com.xiaoji.duan.abd.utils.Utils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/manage/data")
public class ManageApiController {

	@Autowired
	private ABDService abdService;

	@RequestMapping("{subdomain}/users")
	public Map<String, Object> groupUsers(@PathVariable String subdomain) {
		Map<String, Object> rslt = new HashMap<String, Object>();
		
		rslt.put("code", "");
		rslt.put("message", "");
		
		Object data = this.abdService.getGroupUsers(subdomain);
		
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		
		rslt.put("data", data);
		
		return rslt;
	}
	
	@RequestMapping("{subdomain}/{prefix}/users")
	public Map<String, Object> groupSAUsers(@PathVariable String subdomain, @PathVariable String prefix) {
		Map<String, Object> rslt = new HashMap<String, Object>();
		
		rslt.put("code", "");
		rslt.put("message", "");
		
		Object data = this.abdService.getGroupSAUsers(subdomain, prefix);
		
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		
		rslt.put("data", data);
		
		return rslt;
	}
	
	@RequestMapping("{subdomain}/{prefix}/{userId}/grantpass")
	public Map<String, Object> groupSAUserGrantPass(@PathVariable String subdomain, @PathVariable String prefix, @PathVariable String userId) {
		Map<String, Object> rslt = new HashMap<String, Object>();
		
		rslt.put("code", "");
		rslt.put("message", "");
		
		Object data = new HashMap<String, Object>();
		
		int granted = this.abdService.grantGroupSAUser(subdomain, prefix, userId, "VERIFIED");
		
		if (granted <= 0) {
			rslt.put("code", "ABD_200001");
			rslt.put("message", "用户(" + userId + ")授权失败.");
		}
		
		rslt.put("data", data);
		
		return rslt;
	}

	@RequestMapping("{subdomain}/{userId}/groupuser")
	public Map<String, Object> putGroupUserProperty(@PathVariable String subdomain, @PathVariable String userId, @RequestParam(required = false, name = "name") String fieldName, @RequestParam(required = false, name = "value") String value, @RequestParam(required = false, name = "pk") String pk) {
		Map<String, Object> rslt = new HashMap<String, Object>();
		
		rslt.put("code", "");
		rslt.put("message", "");
		
		Object data = new HashMap<String, Object>();
		
		if (Utils.isEmpty(fieldName) || Utils.isEmpty(value)) {
			rslt.put("code", "ABD_200011");
			rslt.put("message", "用户(" + userId + ")属性修改内容为空,修改失败.");
		} else {
			int modified = this.abdService.modifyGroupUser(subdomain, userId, fieldName, value);
			
			if (modified <= 0) {
				rslt.put("code", "ABD_200012");
				rslt.put("message", "用户(" + userId + ")属性修改失败.");
			}
		}
		
		rslt.put("data", data);
		
		return rslt;
	}
	
	@RequestMapping("{subdomain}/{prefix}/{userId}/groupsauser")
	public Map<String, Object> putGroupSAUserProperty(@PathVariable String subdomain, @PathVariable String prefix, @PathVariable String userId, @RequestParam(required = false, name = "name") String fieldName, @RequestParam(required = false, name = "value") String value, @RequestParam(required = false, name = "pk") String pk) {
		Map<String, Object> rslt = new HashMap<String, Object>();
		
		rslt.put("code", "");
		rslt.put("message", "");
		
		Object data = new HashMap<String, Object>();
		
		if (Utils.isEmpty(fieldName) || Utils.isEmpty(value)) {
			rslt.put("code", "ABD_200011");
			rslt.put("message", "用户(" + userId + ")属性修改内容为空,修改失败.");
		} else {
			int modified = this.abdService.modifyGroupSAUser(subdomain, prefix, userId, fieldName, value);
			
			if (modified <= 0) {
				rslt.put("code", "ABD_200012");
				rslt.put("message", "用户(" + userId + ")属性修改失败.");
			}
		}
		
		rslt.put("data", data);
		
		return rslt;
	}
	
	@RequestMapping("{subdomain}/{prefix}/{userId}/grantleave")
	public Map<String, Object> groupSAUserGrantLeave(@PathVariable String subdomain, @PathVariable String prefix, @PathVariable String userId) {
		Map<String, Object> rslt = new HashMap<String, Object>();
		
		rslt.put("code", "");
		rslt.put("message", "");
		
		Object data = new HashMap<String, Object>();
		
		int granted = this.abdService.grantGroupSAUser(subdomain, prefix, userId, "VERIFY");
		
		if (granted <= 0) {
			rslt.put("code", "ABD_200001");
			rslt.put("message", "用户(" + userId + ")授权失败.");
		}
		
		rslt.put("data", data);
		
		return rslt;
	}

	@RequestMapping("{subdomain}/{userId}/grantpass")
	public Map<String, Object> groupUserGrantPass(@PathVariable String subdomain, @PathVariable String userId) {
		Map<String, Object> rslt = new HashMap<String, Object>();
		
		rslt.put("code", "");
		rslt.put("message", "");
		
		Object data = new HashMap<String, Object>();
		
		int granted = this.abdService.grantGroupUser(subdomain, userId, "VERIFIED");
		
		if (granted <= 0) {
			rslt.put("code", "ABD_200001");
			rslt.put("message", "用户(" + userId + ")授权失败.");
		}
		
		rslt.put("data", data);
		
		return rslt;
	}

	@RequestMapping("{subdomain}/{userId}/grantleave")
	public Map<String, Object> groupUserGrantLeave(@PathVariable String subdomain, @PathVariable String userId) {
		Map<String, Object> rslt = new HashMap<String, Object>();
		
		rslt.put("code", "");
		rslt.put("message", "");
		
		Object data = new HashMap<String, Object>();
		
		int granted = this.abdService.grantGroupUser(subdomain, userId, "VERIFY");
		
		if (granted <= 0) {
			rslt.put("code", "ABD_200001");
			rslt.put("message", "用户(" + userId + ")授权失败.");
		}
		
		rslt.put("data", data);
		
		return rslt;
	}
}
