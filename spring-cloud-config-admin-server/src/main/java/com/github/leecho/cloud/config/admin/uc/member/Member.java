package com.github.leecho.cloud.config.admin.uc.member;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import com.github.leecho.cloud.config.admin.uc.user.entity.User;
import com.github.leecho.cloud.config.admin.upm.role.Role;
import lombok.Data;

import javax.persistence.*;

/**
 * 成员
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Entity
@Data
@Table(name = "scca_member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(targetEntity = Role.class)
	private Role role;

	/**
	 * 所属对象实体ID
	 */
	@Column(nullable = false)
	private Integer entityId;

	/**
	 * 所属对象实体类型
	 */
	@Column(nullable = false)
	private String entityType;

	/**
	 * 关联用户
	 */
	@ManyToOne(targetEntity = User.class)
	private User user;

}
