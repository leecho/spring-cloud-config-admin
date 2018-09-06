package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "变更记录")
public class ChangeVO extends ChangeDto {


	@ApiModelProperty("原始值")
	private String originValue;

	@ApiModelProperty("当前值")
	private String currentValue;


	@ApiModelProperty("变更备注")
	private String message;

	/**
	 * 修改人
	 */
	@ApiModelProperty("修改人")
	private String changeBy;

	/**
	 * 修改时间
	 */
	@ApiModelProperty("修改时间")
	private Date changeDate;
}
