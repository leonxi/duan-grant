package com.xiaoji.duan.abd.entity;

import java.io.Serializable;
import java.security.Principal;

public class UserPrincipal implements Principal, Cloneable, Serializable {

    private final String username; 

    public UserPrincipal(String username) {
        this.username = username;
    }

	@Override
	public String getName() {
		return this.username;
	}

}
