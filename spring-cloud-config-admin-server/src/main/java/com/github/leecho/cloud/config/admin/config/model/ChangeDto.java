package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
public class ChangeDto {

	@ApiModelProperty(value = "更改的属性", required = true)
	private String property;

	@ApiModelProperty(value = "更改类型", required = true, allowableValues = "create,update,delete")
	private String type;

}
