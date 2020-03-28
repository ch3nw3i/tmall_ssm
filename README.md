# 模仿天猫的购物网站

模仿天猫购物网站，参考 https://how2j.cn 上的实践项目。

前端的页面、Javascript等是从how2j下载的示例项目，没买课程，自己实现的后端功能。

> 网站效果可以参考：https://how2j.cn/tmall/



若要在本地部署该项目，需要下载图片资源：

> **网站图片下载：**
>
> 链接：https://pan.baidu.com/s/1bS6qMZxxLyRbILAs4wNn6g 提取码：mqcs 



## 项目功能

项目功能分为天猫购物网站和后台管理系统。

> 前台：模仿天猫的购物网站
>
> > 分类列表展示、分类商品展示
> >
> > 商品搜索、商品详情显示、商品属性显示
> >
> > 购物车
> >
> > 发起订单、确认收货
> >
> > 评价商品
> >
> > 用户注册、用户登录
>
> 
>
> 后台管理系统
>
> > 分类管理、属性管理、商品管理
> >
> > 用户管理
> >
> > 订单管理



## 使用技术

> Java: `Java SE`
> 前端： `HTML`,`CSS`, `JavaScript`, `JQuery`,`AJAX`, `Bootstrap`
> J2EE：`Tomcat`, `Servlet`, `JSP`, `Filter`
> 框架：`Spring`，`Spring MVC`，`Mybatis`，`SSM整合`
> 数据库：`MySQL`
> 开发工具: `IDEA` ,`Maven`



## 数据库表设计

MySQL转储文件位于 `sql\tmall_ssm.sql`

### 数据库表

| 表名          |  中文含义  |
| ------------- | :--------: |
| category      |   分类表   |
| property      |   属性表   |
| product       |   产品表   |
| propertyvalue |  属性值表  |
| productimage  | 产品图片表 |
| review        |   评论表   |
| user          |   用户表   |
| order         |   订单表   |
| orderitem     |  订单项表  |
| cart          |  购物车表  |



### 表之间关系

![db-table-relation](raw.githubusercontent.com/ch3nw3i/tmall_ssm/README/db-table-relation.png)



## 项目结构

> tmall_ssm
>
> > .idea
> >
> > src
> >
> > > main
> > >
> > > > java/com.how2j.tmall
> > > >
> > > > > controller包：控制器，负责响应、转发、处理请求
> > > > >
> > > > > mapper包：数据库操作接口
> > > > >
> > > > > pojo：实体类
> > > > >
> > > > > service包：service接口
> > > > >
> > > > > > impl包：service接口的实现类
> > > > >
> > > > > util包：工具包
> > >
> > > resources
> > >
> > > > mapper：具体sql语句，对应mapper包的接口所定义的方法
> > > >
> > > > applicationContext.xml：Spring、MyBatis配置文件
> > > >
> > > > jdbc.properties：数据库连接参数
> > > >
> > > > springMVC.xml：SpringMVC配置文件
> > >
> > > webapp
> > >
> > > > admin：后台管理系统入口页面
> > > >
> > > > css
> > > >
> > > > img：分类、商品图片，需要另行下载
> > > >
> > > > js
> > > >
> > > > WEB-INF
> > > >
> > > > > jsp
> > > > >
> > > > > > admin：后台管理系统页面
> > > > > >
> > > > > > fore：购物网站页面
> > > > > >
> > > > > > include：子页面
> > > > >
> > > > > web.xml：加载配置文件，定义拦截器，配置servlet
> > > >
> > > > index.jsp
> >
> > .gitignore：标记git需要忽略的文件或目录
> >
> > pom.xml：maven配置文件
> >
> > README.md：项目说明文件
> >
> > README：存放README.md文件的图片



## 页面执行流程

以admin_category_list为例：

![admin-category-list](raw.githubusercontent.com/ch3nw3i/tmall_ssm/README/admin-category-list.png)