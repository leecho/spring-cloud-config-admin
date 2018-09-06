package com.github.leecho.cloud.config.admin.uc.user.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 用户
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Entity
@Table(name = "scca_user")
@Data
public class User {

	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 用户名
	 */
	@Column(length = 50)
	@NotEmpty(message = "用户名不能为空")
	private String username;

	/**
	 * 邮箱
	 */
	@Column(length = 200)
	@NotEmpty(message = "邮箱不能为空")
	@Email(message = "邮箱格式错误")
	private String email;

	/**
	 * 姓名
	 */
	@Column(length = 100)
	@NotEmpty(message = "姓名不能为空")
	private String name;

	/**
	 * 密码
	 */
	@Column(length = 200)
	@NotEmpty(message = "密码不能为空")
	private String password;

}
