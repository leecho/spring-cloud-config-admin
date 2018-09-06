package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "回滚配置操作")
public class RollbackOperation {

	@NotNull(message = "配置文件不能为空")
	@ApiModelProperty(value = "配置文件", required = true)
	private Integer configId;

	@ApiModelProperty(value = "发布版本ID，可以回滚到指定版本", required = false)
	private Integer publishId;

	@NotNull(message = "回滚说明不能为空")
	@ApiModelProperty(value = "回滚说明", required = true)
	private String message;

}
