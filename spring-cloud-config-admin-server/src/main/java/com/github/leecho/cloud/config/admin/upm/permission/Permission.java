package com.github.leecho.cloud.config.admin.upm.permission;

import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;
import com.github.leecho.cloud.config.admin.upm.role.Role;
import lombok.Data;

import javax.persistence.*;

/**
 * 权限表
 *
 * @author LIQIU
 * @date 2018-9-5
 **/
@Entity
@Data
@Table(name = "scca_permission")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
	private Role role;

	@ManyToOne(targetEntity = Profile.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private Profile profile;

}
