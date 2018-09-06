package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "推送配置操作")
public class PushOperation {

	@ApiModelProperty("配置文件ID")
	@NotNull(message = "配置文件不能为空")
	private Integer configId;

	@ApiModelProperty("推送目标")
	private List<String> destinations;

	@ApiModelProperty("推送备注")
	@NotEmpty(message = "备注不能为空")
	private String message;

}
