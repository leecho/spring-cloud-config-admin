package com.github.leecho.cloud.config.admin.config.entity;

import com.github.leecho.cloud.config.admin.pm.profile.entity.Profile;
import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author LIQIU
 * @date 2018-9-3
 **/
@Entity
@Data
@Table(name = "scca_config", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"project_id", "profile_id", "label"})
})
@ApiModel(description = "配置文件")
public class Config {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("配置文件ID")
	private Integer id;

	/**
	 * 所属项目
	 */
	@ManyToOne(targetEntity = Project.class)
	@NotNull(message = "所属项目不能为空")
	@ApiModelProperty(value = "所属项目", required = true)
	private Project project;

	/**
	 * 环境
	 */
	@ManyToOne(targetEntity = Profile.class)
	@NotNull(message = "环境不能为空")
	@ApiModelProperty(value = "配置文件环境", required = true)
	private Profile profile;

	/**
	 * 标签
	 */
	@Column(length = 100, nullable = false)
	@NotEmpty(message = "标签不能为空")
	@ApiModelProperty(value = "配置文件标签", required = true)
	private String label;

	/**
	 * 描述
	 */
	@ApiModelProperty("配置文件描述")
	private String description;

}
