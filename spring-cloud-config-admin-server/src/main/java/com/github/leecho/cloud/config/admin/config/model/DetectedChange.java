package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "检测后的变更记录")
public class DetectedChange extends ChangeDto {

	@ApiModelProperty("当前值")
	private String currentValue;

	@ApiModelProperty("原始值")
	private String originValue;

}
