# 模仿天猫的购物网站

模仿天猫购物网站，是学习 https://how2j.cn 上的实践项目。

前端的页面、Javascript等是从how2j上下载来的，后端是自己实现的。



> 网站效果可以参考：https://how2j.cn/tmall/

若要在本地部署该项目，还需在以下链接中下载图片资源：

> 网站图片下载：链接：https://pan.baidu.com/s/1bS6qMZxxLyRbILAs4wNn6g 提取码：mqcs 



## 项目功能

项目功能分为前台的购物网站和后台管理系统。

1. 前台：模仿天猫的购物网站

   1. 分类列表展示、分类商品展示
   2. 商品搜索、商品详情显示、商品属性显示
   3. 购物车
   4. 发起订单、确认收货
   5. 评价商品
   6. 用户注册、用户登录

2. 后台管理系统

   1. 分类管理、属性管理、商品管理
   2. 用户管理
   3. 订单管理

   

## 使用技术

- 前端：HTML、CSS、JavaScript、jQuery、Bootstrap
- 后端框架：Spring、SpringMVC、MyBatis
- 其他：Maven



## 数据库表关系图

| 表名          |  中文含义  | 介绍                                                |
| ------------- | :--------: | --------------------------------------------------- |
| Category      |   分类表   | 存放分类信息，如女装，平板电视，沙发等              |
| Property      |   属性表   | 存放属性信息，如颜色，重量，品牌，厂商，型号等      |
| Product       |   产品表   | 存放产品信息，如LED40EC平板电视机，海尔EC6005热水器 |
| PropertyValue |  属性值表  | 存放属性值信息，如重量是900g,颜色是粉红色           |
| ProductImage  | 产品图片表 | 存放产品图片信息，如产品页显示的5个图片             |
| Review        |   评论表   | 存放评论信息，如买回来的蜡烛很好用，么么哒          |
| User          |   用户表   | 存放用户信息，如斩手狗，千手小粉红                  |
| Order         |   订单表   | 存放订单信息，包括邮寄地址，电话号码等信息          |
| OrderItem     |  订单项表  | 存放订单项信息，包括购买产品种类，数量等            |

![db-table-relation](README\db-table-relation.png)



## 页面执行流程（以admin_category_list为例）

![admin-category-list](README\admin-category-list.png)