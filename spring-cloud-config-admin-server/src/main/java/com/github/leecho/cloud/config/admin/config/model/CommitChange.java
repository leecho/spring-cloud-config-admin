package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "提交的变更")
public class CommitChange extends ChangeDto {

	@ApiModelProperty(value = "修改后的值", required = true)
	private String value;

}
