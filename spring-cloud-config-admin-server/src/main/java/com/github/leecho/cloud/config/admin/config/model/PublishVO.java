package com.github.leecho.cloud.config.admin.config.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LIQIU
 * @date 2018-9-5
 **/
@Data
@ApiModel(description = "发布记录")
public class PublishVO {

	@ApiModelProperty("发布人")
	private String publishBy;

	@ApiModelProperty("发布时间")
	private String publishDate;

	@ApiModelProperty("版本号")
	private String version;

	@ApiModelProperty("发布内容")
	private String message;

	@ApiModelProperty("当前版本")
	private Boolean current;

}
