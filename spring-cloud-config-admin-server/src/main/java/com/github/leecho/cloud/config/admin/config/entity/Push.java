package com.github.leecho.cloud.config.admin.config.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "推送记录")
public class Push {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("推送ID")
	private Integer id;

	@JsonIgnore
	@ManyToOne(targetEntity = Config.class)
	private Config config;

	@Column(length = 200, nullable = false)
	@ApiModelProperty("推送地址")
	private String destination;

	@Column(length = 1000)
	@ApiModelProperty("推送结果")
	private String result;

	/**
	 * 是否成功
	 */
	@Column(nullable = false)
	@ApiModelProperty("推送是否成功")
	private Boolean success;

	@Column(length = 50, nullable = false)
	@ApiModelProperty("推送人")
	private String pushedBy;

	@ApiModelProperty("推送时间")
	@Column(nullable = false)
	private Date pushedDate;

}
