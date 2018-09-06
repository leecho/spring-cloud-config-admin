package com.github.leecho.cloud.config.admin.config.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 配置文件归档
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Data
@Entity
@Table(name = "scca_config_publish", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"config_id", "version"})
})
public class Publish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(targetEntity = Config.class, fetch = FetchType.LAZY)
	private Config config;

	@Column(length = 100, nullable = false)
	private Boolean current;

	@Column(length = 100, nullable = false)
	private Integer version;

	/**
	 * 发布的配置文件内容
	 */
	@Column(length = 5000, nullable = false)
	private String content;

	/**
	 * 发布日志
	 */
	@Column(length = 2000, nullable = false)
	private String message;

	private String publishBy;

	private Date publishDate;
}
