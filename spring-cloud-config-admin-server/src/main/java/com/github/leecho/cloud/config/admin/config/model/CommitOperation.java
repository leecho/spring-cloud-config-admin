package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 提交请求
 *
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "提交操作")
public class CommitOperation {

	@ApiModelProperty(value = "配置文件ID", required = true)
	@NotNull(message = "配置文件ID不能为空")
	private Integer configId;

	@ApiModelProperty(value = "更改记录", required = true)
	@NotEmpty(message = "更改记录不能为空")
	private List<CommitChange> changes;

	@ApiModelProperty(value = "更改备注", required = true)
	@NotEmpty(message = "备注信息不能为空")
	private String message;
}
