package com.github.leecho.cloud.config.admin.pm.profile.entity;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 环境
 *
 * @author LIQIU
 * @date 2018-9-5
 **/
@Entity
@Data
@Table(name = "scca_project_profile")
@ApiModel(description = "配置环境")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("ID")
	private Integer id;

	@NotNull(message = "所属项目不能为空")
	@ManyToOne(targetEntity = Project.class)
	@ApiModelProperty("所属项目")
	private Project project;

	@ApiModelProperty("环境编号")
	@NotEmpty(message = "环境编号不能为空")
	@Column(nullable = false)
	private String code;

	@ApiModelProperty("环境名称")
	@NotEmpty(message = "环境名称不能为空")
	@Column(nullable = false)
	private String name;

	@ApiModelProperty("环境描述")
	private String description;

	@ApiModelProperty("是否启用")
	@Column(nullable = false)
	private Boolean enable;
}