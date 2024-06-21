# 描述
## springboot+mybatis_plus+mybatisx代码生成器插件+jpa
## 此项目在springboot基础上 整合mybatis
## 在以上基础上再增加mybatisX代码生成器插件
## 再以上基础上再加上jpa，用于根据实体类自动生成表

### 此项目的特点即是：数据库访问采用mybati_splus，但是为了方便建表，<br>引入jpa,所以实际开发流程是先写jpa实体类，通过jpa自动建表，然后通过mybatisX代码生成器，将表反向生成mybatis相关的代码。