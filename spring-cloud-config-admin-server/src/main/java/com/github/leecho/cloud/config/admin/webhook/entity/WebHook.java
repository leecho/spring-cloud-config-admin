package com.github.leecho.cloud.config.admin.webhook.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 钩子，用于集成通知
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Data
@Entity
@Table(name = "scca_webhook")
public class WebHook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String api;

	private String template;

	private String triggers;

}
