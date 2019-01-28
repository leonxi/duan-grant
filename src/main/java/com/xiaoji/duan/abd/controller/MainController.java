package com.xiaoji.duan.abd.controller;

import java.net.URL;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoji.duan.abd.entity.ABD_Group;
import com.xiaoji.duan.abd.entity.ABD_GroupSA;
import com.xiaoji.duan.abd.entity.ABD_GroupUser;
import com.xiaoji.duan.abd.service.ABDService;
import com.xiaoji.duan.abd.service.HttpService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class MainController {

    @Autowired
    private HttpService httpauth;
    
	@Autowired
	private ABDService abdService;

	
    @Value("${zuul.authorize.url}")
    private URL authurl;
    @Value("${zuul.authorize.path}")
    private String authpath;

	@RequestMapping("/{subdomain}/apply")
	public String applytoGroup(@PathVariable String subdomain, Model model) {
		
		ABD_Group group = this.abdService.getGroup(subdomain);
		
		model.addAttribute("group", group);
		
		return "applytogroup";
	}
	
	@RequestMapping("/{subdomain}/{prefix}/apply")
	public String applytoGroupSA(@PathVariable String subdomain, @PathVariable String prefix, Model model) {

		ABD_Group group = this.abdService.getGroup(subdomain);
		ABD_GroupSA groupsa = this.abdService.getGroupSA(subdomain, prefix);
		
		model.addAttribute("group", group);
		model.addAttribute("groupsa", groupsa);
		
		return "applytogroupsa";
	}
	
	@RequestMapping("/data/usergroups")
	@ResponseBody
	public String getUserGroups(HttpServletRequest req, @RequestParam Map<String, Object> params) {
		String username = req.getParameter("username");
		
		if (username == null || "".equals(username)) {
			username = (String) params.get("username");
		}
		
		JSONObject sas = new JSONObject();
		sas.put("code", "");
		sas.put("message", "");
		
		List<ABD_Group> usergroups = abdService.getGroupsByUser(username);

		if (usergroups == null || usergroups.size() < 1) {
			sas.put("length", 0);
			sas.put("data", new JSONObject());
		} else {
			sas.put("length", usergroups.size());
			JSON json = (JSON) JSON.toJSON(usergroups);
			sas.put("data", json);
		}
		
		return sas.toJSONString();
	}

	@RequestMapping("/data/addgroup")
	@ResponseBody
	public String addGroup(HttpServletRequest req) {
		ABD_Group abd_group = new ABD_Group();
		abd_group.setName(req.getParameter("simpleName"));
		abd_group.setFullName(req.getParameter("fullName"));
		abd_group.setRegister(req.getParameter("register"));
		abd_group.setSubDomain(req.getParameter("subDomain").toUpperCase());
		if(req.getParameter("saGroupAuthorize")==null){
			abd_group.setSaGroupAuthorize("UNAUTHORIZED");
		}else{
			abd_group.setSaGroupAuthorize(req.getParameter("saGroupAuthorize"));
		}
		List<MultipartFile> files = ((MultipartHttpServletRequest) req).getFiles("groupLogo");

		return abdService.addGroup(files,abd_group);
	}

	@RequestMapping("/data/usergroupssa")
	@ResponseBody
	public String getUserGroupsSa(@RequestBody String subDomain) {

		System.out.println("usergroupssa -> " + subDomain);
		JSONObject sas = new JSONObject();
		List<ABD_GroupSA> saList=abdService.getSaByGroup(subDomain);
		sas.put("saList",saList);
		return sas.toJSONString();
	}
}
