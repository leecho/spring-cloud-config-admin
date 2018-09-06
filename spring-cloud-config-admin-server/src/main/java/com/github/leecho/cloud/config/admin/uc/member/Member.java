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

	@ManyToOne(targetEntity = Project.class)
	private Project project;

	/**
	 * 关联用户
	 */
	@ManyToOne(targetEntity = User.class)
	private User user;

}
