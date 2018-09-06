package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "检测变更操作")
public class DetectOperation {

	@ApiModelProperty(name = "配置文件ID", required = true)
	private Integer configId;

	@ApiModelProperty(name = "文件格式", allowableValues = "yaml,properties", required = true)
	private String format;

	@ApiModelProperty(name = "文件内容", required = true)
	private String content;

}
