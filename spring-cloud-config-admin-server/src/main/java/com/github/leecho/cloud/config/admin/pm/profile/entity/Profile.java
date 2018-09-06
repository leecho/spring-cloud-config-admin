package com.github.leecho.cloud.config.admin.pm.profile.entity;

import com.github.leecho.cloud.config.admin.pm.project.entity.Project;
import lombok.Data;

import javax.persistence.*;

/**
 * 环境
 * @author LIQIU
 * @date 2018-9-5
 **/
@Entity
@Data
@Table(name = "scca_project_profile")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(targetEntity = Project.class)
	private Project project;

	private String code;

	private String name;

	private String description;

	private Boolean enable;
}