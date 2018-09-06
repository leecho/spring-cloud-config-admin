package com.github.leecho.cloud.config.admin.pm.project.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 项目
 * @author LIQIU
 * @date 2018-9-3
 **/
@Entity
@Table(name = "scca_project")
@Data
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 项目名称
	 */
	@Column(length = 100)
	@NotEmpty(message = "项目名称不能为空")
	private String name;

	/**
	 *
	 */
	@Column(length = 100)
	@NotEmpty(message = "项目编号不能为空")
	private String code;

	/**
	 * 项目LOGO
	 */
	@Column(length = 1000)
	private String logo;

	/**
	 * 项目描述
	 */
	@Column(length = 5000)
	@NotEmpty(message = "项目描述不能为空")
	private String description;

	/**
	 * 项目管理员
	 */
	@Column(name = "project_owner")
	private Integer owner;

	private Boolean enable;
}
