package com.xiaoji.duan.abd.utils;

import java.util.regex.PatternSyntaxException;

public class Utils {
public static void main(String[] args) {
	System.out.println(urlPatternMatch("/abd/manage/index", "/manage/**"));
}
	public static boolean urlPatternMatch(String uri, String pattern) {
		boolean matched = false;
		
		try {
			if (pattern.startsWith("/")) {
				pattern = "/[a-zA-Z]{3}" + pattern;
			}
			pattern = pattern.replace("**", ".*");
			pattern = pattern.replace("*.", ".+\\.");
			pattern = pattern.replace("/*", "/[^/]*");
			System.out.println(uri + " match with " + pattern);
			matched = uri.matches(pattern);
		} catch (PatternSyntaxException e) {
			e.printStackTrace();
		}
		
		return matched;
	}
	
	public static boolean isEmpty(String value) {
		return value == null || "".equals(value.trim());
	}
}
