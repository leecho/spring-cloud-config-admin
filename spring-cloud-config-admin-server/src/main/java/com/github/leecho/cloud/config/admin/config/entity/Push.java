package com.github.leecho.cloud.config.admin.config.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LIQIU
 * @date 2018-9-7
 **/
@Entity
@Data
@Builder
@Table(name = "scca_config_push")
public class Push {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(targetEntity = Config.class)
	private Config config;

	@Column(length = 200, nullable = false)
	private String destination;

	@Column(length = 1000)
	private String result;

	private Boolean success;

	@Column(length = 100, nullable = false)
	private String pushedBy;

	@Column(nullable = false)
	private Date pushedDate;

}
