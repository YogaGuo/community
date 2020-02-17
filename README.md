## 码匠社区

### 资料
* [Spring 文档](https://spring.io/guides/)  
* [Spring Wed 文档](https://spring.io/guides/gs/serving-web-content/)  
* [Bootstrap 文档](https://v3.bootcss.com/getting-started/)  
* [Github OAuth 文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)
### 工具
* [Git 下载地址](https://git-srm.com/download)  
### 数据库脚本  
```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;



```  

