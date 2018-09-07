package com.github.leecho.cloud.config.admin.pm.project.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 项目
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Entity
@Table(name = "scca_project", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"code"})
})
@Data
@ApiModel(description = "项目")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("ID")
	private Integer id;

	/**
	 * 项目名称
	 */
	@Column(length = 100)
	@NotEmpty(message = "项目名称不能为空")
	@ApiModelProperty("项目名称")
	private String name;

	/**
	 *
	 */
	@Column(length = 100)
	@NotEmpty(message = "项目编号不能为空")
	@ApiModelProperty("项目编号")
	private String code;

	/**
	 * 项目LOGO
	 */
	@Column(length = 1000)
	@ApiModelProperty("项目LOGO")
	private String logo;

	/**
	 * 项目描述
	 */
	@Column(length = 5000)
	@ApiModelProperty("项目描述")
	private String description;

	@ApiModelProperty("是否启用")
	private Boolean enable;
}
