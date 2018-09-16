package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "发布配置操作")
public class PublishRequest {

	@NotNull(message = "配置文件ID不能为空")
	@ApiModelProperty(value = "配置文件ID", required = true)
	private Integer configId;

	@NotEmpty(message = "发布内容不能空")
	@ApiModelProperty(value = "发布内容", required = true)
	private String message;

}
