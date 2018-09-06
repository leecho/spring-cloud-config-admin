package com.github.leecho.cloud.config.admin.config.model;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author LIQIU
 * @date 2018-9-4
 **/
public class ConfigDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("配置文件ID")
	private Integer id;

	/**
	 * 所属项目
	 */
	@NotNull(message = "所属项目不能为空")
	@ApiModelProperty("所属项目")
	private Project project;

	/**
	 * 环境
	 */
	@NotEmpty(message = "环境不能为空")
	@ApiModelProperty(value = "配置文件环境",required = true)
	private String profile;

	/**
	 * 标签
	 */
	@Column(length = 100, nullable = false)
	@NotEmpty(message = "标签不能为空")
	@ApiModelProperty(value = "配置文件标签",required = true)
	private String label;

	/**
	 * 描述
	 */
	@ApiModelProperty("配置文件描述")
	private String description;


}
