package com.github.leecho.cloud.config.admin.config.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "发布记录")
public class Publish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("发布ID")
	private Integer id;

	@JsonIgnore
	@ManyToOne(targetEntity = Config.class, fetch = FetchType.LAZY)
	private Config config;

	@Column(length = 100, nullable = false)
	@ApiModelProperty("是否为当前版本")
	private Boolean current;

	@Column(length = 100, nullable = false)
	@ApiModelProperty("版本")
	private Integer version;

	/**
	 * 发布的配置文件内容
	 */
	@Column(length = 5000, nullable = false)
	@ApiModelProperty("发布内容")
	private String content;

	/**
	 * 发布日志
	 */
	@Column(length = 2000, nullable = false)
	@ApiModelProperty("发布备注")
	private String message;

	@Column(nullable = false, length = 50)
	@ApiModelProperty("发布人")
	private String publishBy;

	@Column(nullable = false)
	@ApiModelProperty("发布时间")
	private Date publishDate;
}
