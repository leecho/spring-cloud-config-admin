package com.github.leecho.cloud.config.admin.config.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 配置文件变更记录
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Entity
@Data
@Table(name = "scca_config_change")
public class Change {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 所属配置文件
	 */
	@JsonIgnore
	@ManyToOne(targetEntity = Config.class, fetch = FetchType.LAZY)
	private Config config;

	/**
	 * 更改的属性
	 */
	@Column(length = 500, nullable = false)
	private String property;

	/**
	 * 更改类型
	 */
	@Column(length = 2, nullable = false)
	private Integer type;

	/**
	 * 原始值
	 */
	@Column(length = 1000)
	private String originValue;

	/**
	 * 当前值
	 */
	@Column(length = 1000)
	private String currentValue;

	/**
	 * 更改备注
	 */
	@Column(length = 1000, nullable = false)
	private String message;

	/**
	 * 发布版本
	 */
	@ManyToOne(targetEntity = Publish.class, fetch = FetchType.LAZY)
	private Publish publish;


	/**
	 * 是否已发布
	 */
	private Boolean published;

	/**
	 * 修改人
	 */
	@Column(length = 100, nullable = false)
	private String changeBy;

	/**
	 * 修改时间
	 */
	@Column(nullable = false)
	private Date changeDate;
}
