package com.xiaoji.duan.abd.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.xiaoji.duan.abd.entity.ABD_Group;
import com.xiaoji.duan.abd.entity.ABD_GroupSA;
import com.xiaoji.duan.abd.entity.ABD_GroupSAUser;
import com.xiaoji.duan.abd.entity.ABD_GroupUser;
import com.xiaoji.duan.abd.service.ABDService;
import com.xiaoji.duan.abd.service.HttpService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private HttpService httpauth;
    
	@Autowired
	private ABDService abdService;

    @Value("${zuul.authorize.url}")
    private URL authurl;
    @Value("${zuul.authorize.path}")
    private String authpath;

	@RequestMapping(value={"/", "index"})
    public String index(HttpServletRequest req, Model model) {
		String token = req.getParameter("token");
		String openid = req.getParameter("openid");
		
		String host = req.getHeader("x-forwarded-host");
		
		if (host == null) {
			host = req.getHeader("host");
		}
		
		String subdomain = host.substring(0, host.indexOf('.'));
      
		Cookie[] cookies = req.getCookies();
		if (cookies != null && (token == null || "".equals(token) || openid == null || "".equals(openid))) {
			for (Cookie cookie : cookies) {
				switch(cookie.getName()) {
				case "authorized_user":
    	            token = cookie.getValue();
    	            break;
				case "authorized_openid":
    	            openid = cookie.getValue();
    	            break;
				default:
					break;
				}
			}
		}

		JSONObject userinfo = new JSONObject();

		if (token != null && !"".equals(token) && openid != null && !"".equals(openid)) {
			Map<String, String[]> data = new HashMap<String, String[]>();
			Map<String, Object> res = httpauth.https(authurl + "/" + openid + "/info", data);

			if (res != null && res.get("data") != null && !((JSONObject) res.get("data")).isEmpty()) {
				userinfo = (JSONObject) res.get("data");
			}
		}
		
		if (openid != null && !"".equals(openid)) {
			
			ABD_GroupUser gu = this.abdService.getGroupUser(subdomain, openid);

			if (gu != null) {
				userinfo.put("openid", gu.getUserId());
				userinfo.put("name", gu.getUserName());
			}
		}
		
		if (userinfo.isEmpty()) {
			userinfo.put("name", "");
		}
		userinfo.put("roles", "");
		
		if (openid != null && !"".equals(openid)) {
			
			ABD_GroupSAUser gusa = this.abdService.getGroupSAUser(subdomain, "abd", openid);
			
			if (gusa != null) {
				userinfo.put("roles", gusa.getUserType());
			}
		}
		
		ABD_Group group = this.abdService.getGroup(subdomain);
		
		if (group == null) {
			group = new ABD_Group();
		}
		
		List<ABD_GroupSA> groupsas = this.abdService.getSaByGroup(subdomain);
		
		if (groupsas == null) {
			groupsas = new ArrayList<ABD_GroupSA>();
		}
		
		model.addAttribute("userinfo", userinfo);
		model.addAttribute("currentgroup", group);
		model.addAttribute("currentgroupsas", groupsas);

        return "manage/index";
	}
}
