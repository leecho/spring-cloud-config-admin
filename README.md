# spring-cloud-config-admin


在Spring Cloud的微服务架构方案中虽然提供了Spring Cloud Config来担任配置中心的角色，但是该项目的功能在配置的管理层面还是非常欠缺的。初期我们可以依赖选取的配置存储系统（比如：Gitlab、Github）给我们提供的配置管理界面来操作所有的配置信息，但是这样的管理还是非常粗粒度的，因此这个项目的目的就是解决这个问题，希望提供一套基于Spring Cloud Config配置中心的可视化管理系统。

# 版本依赖

- Spring Boot<2.0.3-RELEASE>
- Spring Cloud<Finchley.SR1>

# 特性

## 1.0.0(开发中)

- 基于数据库存储配置项
- 提供可视化管理平台进行配置管理
- 多应用、多环境、多版本配置文件管理
- 修改留痕，记录配置项所有修改轨迹
- 支持多种配置编辑模式：列表格式、YAML格式、PROPERTIES格式
- 使用Spring Cloud Bus集成进行配置实时推送、支持灰度发布、配置回滚
- 集成WebHook功能，可以集成钉钉等通讯工具进行实时消息推送
- 基于RBCA权限模型进行权限控制

## 目前该版本尚未开发完成，源码仅供参考