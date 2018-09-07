package com.github.leecho.cloud.config.admin.security;

import com.github.leecho.cloud.config.admin.config.entity.Config;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
public class SecurityUtils {

	/**
	 * 判断是否授权
	 *
	 * @param profileId
	 * @param permission
	 * @return
	 */
	public static Boolean isGranted(Integer profileId, String permission) {
		Collection<? extends GrantedAuthority> profileAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Iterator<? extends GrantedAuthority> iterable = profileAuthorities.iterator();
		while (iterable.hasNext()) {
			ProfileAuthority profileAuthority = (ProfileAuthority) iterable.next();
			if (profileAuthority.isGrantted(profileId, permission)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否已授权
	 *
	 * @param config
	 * @param permission
	 * @return
	 */
	public Boolean isGranted(Config config, String permission) {
		return isGranted(config.getProfile().getId(), permission);
	}

}
