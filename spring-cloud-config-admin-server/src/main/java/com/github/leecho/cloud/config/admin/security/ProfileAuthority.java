package com.github.leecho.cloud.config.admin.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
@Data
public class ProfileAuthority implements GrantedAuthority {

	private Integer profileId;

	private String permission;

	public ProfileAuthority(Integer profileId, String permission) {
		Assert.notNull(profileId, "Profile id must not be null");
		Assert.notNull(permission, "permission not be null");
		this.profileId = profileId;
		this.permission = permission;
	}

	@Override
	public String getAuthority() {
		return permission;
	}

	public boolean isGrantted(Integer profileId, String permission) {
		return this.profileId.equals(profileId) && this.permission.equals(permission);
	}
}
