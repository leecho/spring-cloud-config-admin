package com.github.leecho.cloud.config.admin.upm.role;

import lombok.Data;

import javax.persistence.*;

/**
 * 角色
 * @author LIQIU
 * @date 2018-9-5
 **/
@Entity
@Table(name = "scca_role")
@Data
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String code;

	private String name;

	private String description;

}
