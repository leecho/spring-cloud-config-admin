package com.github.leecho.cloud.config.admin.config.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 配置文件草稿
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Entity
@Table(name = "scca_config_draft", indexes = {
}, uniqueConstraints = {@UniqueConstraint(columnNames = {"property", "config_id"})
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Draft {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 配置属性
	 */
	@Column(length = 1000, nullable = false)
	private String property;

	/**
	 * 配置值
	 */
	@Column(length = 1000, nullable = false)
	private String value;

	/**
	 * 原始值
	 */
	@Column(length = 1000, nullable = false)
	private String originValue;

	/**
	 * 所属配置文件
	 */
	@JsonIgnore
	@ManyToOne(targetEntity = Config.class)
	private Config config;

	/**
	 * 描述
	 */
	@Column(length = 1000)
	private String description;

	/**
	 * 是否有更改
	 */
	private Boolean changed;

	/**
	 * 创建者
	 */
	private String createdBy;

	/**
	 * 创建时间
	 */
	private Date createdDate;

	/**
	 * 最后修改时间
	 */
	private Date lastUpdateDate;

	/**
	 * 最后修改人
	 */
	private String lastUpdateBy;
}
