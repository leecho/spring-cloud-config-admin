package com.github.leecho.cloud.config.admin.config.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 最终发布的配置项
 *
 * @author LIQIU
 * @date 2018-9-3
 **/
@Entity
@Table(name = "scca_config_item", indexes = {
		@Index(name = "index_config_item_application", columnList = "application,profile,label"),
}, uniqueConstraints = {@UniqueConstraint(columnNames = {"property", "value", "application", "profile", "label"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String property;

	private String value;

	private String application;

	private String profile;

	private String label;

	private String description;

	private String publishBy;

	private Date publishDate;

}
