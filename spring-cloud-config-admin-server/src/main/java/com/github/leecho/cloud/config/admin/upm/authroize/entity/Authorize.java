package com.github.leecho.cloud.config.admin.upm.authroize.entity;

import com.github.leecho.cloud.config.admin.upm.role.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 权限表
 *
 * @author LIQIU
 * @date 2018-9-5
 **/
@Entity
@Data
@Table(name = "scca_authorize")
public class Authorize {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
	private Role role;

	/**
	 * 授权对象实体ID
	 */
	@Column(nullable = false)
	private Integer entityId;

	/**
	 * 授权对象实体类型
	 */
	@Column(nullable = false)
	private String entityType;

	@Column(nullable = false)
	@NotEmpty(message = "权限不能为空")
	private String permission;
}
