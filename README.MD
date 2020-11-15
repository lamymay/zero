# 项目说明

todo 合并 配置 settings.jar
ProjectViewSharedSettings

###### 学习本身就是值得学习的，所以我们需要不断学习
##方法
1. 定位问题，给出解决方案
2. 有力执行，及时反馈并修正
3. 总结记录，并继续

# 这个项目是做甚的
1. 一个博客系统，功用：发表/修改文章，评论，等功能
2. 用户权限系统，关注、屏蔽等功能
3. 致力于原创分享。

用户模块：
-----------------
注册
登录
绑定第三方账号
找回密码
修改密码
访问权限
用户组




文章模块：
-----------------
文章概览列表
文章帖子
用户留言
发表动态
发送私信


积分模块：
-----------------
评论积分
连续登录
优质文章
等级提升




文件模块
-----------------
文件上传/下载 及其权限设计控制


推送模块
-----------------
短信
邮件
定时提醒


系统模块
-----------------
关键操作记录：如： 1. 文章保存至后端数据库 2. 用户登录 3. 找回/修改密码
系统运行中的日志聚合处理： 运维工具开发 




最终效果参考
-----------------

[简书](https://www.jianshu.com)

[CSDN](https://www.csdn.net)



[博客百科介绍](https://baike.baidu.com/item/博客/124?fromtitle=blog&fromid=70149&fr=aladdin)

[开发资料](https://github.com/lamymay/zero/tree/master/about)



### 目录以及端口说明

`一些文件所在位置`

/com.arc.zero/about/

### 端口，
`web 项目向公网暴露（最终由nginx反向代理）`

【开发使用】端口80
【线上使用】端口80

`MySQL数据库为：`

【开发使用】127.0.0.1:3306    MySQL8.0 root@admin

`NoSQL数据库为：Redis `

【开发使用】127.0.0.1:6379    无

`注意环境`

dev
local
test
prod




-----

### 编码规范要求

#### controller 接口定义的相关要求
1. uri路径禁止使用驼峰表示
2. 用名词单数
3. HttpMethod 一律使用 GET 或者 POST
4. update的API中参数id 是用什么方式来传递？ 放在在body中还是放在URL中== 都放！


#### 命名
1、关于ms与数据库交互中的Service层，DAO层的命名

要求：
DAO层        
遵循一定命名规范（@todo 待定！！！）

Service层    
Object get(Long id);// 业务中很多id 是没有意义的情况下，则参数应该是 String code
Object getByXXX(XXX xxx);//是以某个条件来获取一个对象
List<Object> list();//获取一个list
List<Object> listByXXX(XXX xxx);//获取一个list，条件为xxx
List<Object> pageByXXX(XXX xxx);//获取分页数据，条件为xxx

DELETE
delete                 约定不指明的情况下一般都是物理删除，(Physically)
deleteLogical     是逻辑删除，update了某个状态  




2、实体类
例如：
2.1 实体类 XXX                            cn.net.yodoo.gree.common.entities.system.User                      表示是对应一张表 t_sys_User
2.2 传输层实体类 XXXDto           cn.net.yodoo.gree.common.model.dto.system.UserDto           表示 与User相关的 一个传输层实体类
2.3 查询条件实体类 XXXQuery    cn.net.yodoo.gree.common.model.query.system.UserQuery   表示 与User相关的 一个查询条件对象
2.4 返回页面的实体类 XXXVo      cn.net.yodoo.gree.common.model.response.MicroServiceResponse                  表示  




#### 接口参数校验
要求：参数校验由web层controller接收参数时通过注解校验，aop会自动完成错误返回对象



Bean Validation 中内置的 constraint    
@Null   被注释的元素必须为 null    
@NotNull    被注释的元素必须不为 null    
@AssertTrue     被注释的元素必须为 true    
@AssertFalse    被注释的元素必须为 false    
@Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值    
@Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值    
@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
@Size(max=, min=)   被注释的元素的大小必须在指定的范围内    
@Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内    
@Past   被注释的元素必须是一个过去的日期    
@Future     被注释的元素必须是一个将来的日期    
@Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式    
    
Hibernate Validator 附加的 constraint    
@NotBlank(message =)   验证字符串非null，且长度必须大于0    
@Email  被注释的元素必须是电子邮箱地址    
@Length(min=,max=)  被注释的字符串的大小必须在指定的范围内    
@NotEmpty   被注释的字符串的必须非空    
@Range(min=,max=,message=)  被注释的元素必须在合适的范围内
  




#### 接口文档，1、由swagger2自动生成 2、word/excel格式的文档交付前抽时间完成
注意：1项目中需要在主启动类中注解引入 2yml配置中需要配置参数 3项目需要给默认content-path
要求：写相关注解参数

#### 统一异常处理，抛出由上层统一aop捕获生成固定对象并设置错误信息（code、msg、等）
异常抛出统一使用自定义异常，系统会自动捕捉传递信息，组装对象返回
需要捕捉处理的异常使用BusinessException，自动处理的异常使用BusinessRuntimeException，
如有特殊异常处理需要，可继承以上异常类，并在ExceptionHandle中定义处理逻辑。

微服务层异常抛出处理：
抛出异常应按分层处理，如ms-api抛出ApiRuntimeException(ErrorCode projectCode)，
在ErrorCode中定义异常的具体内部代码及描述，定义应尽量保证复用并遵守定义规则（见类中注释）。
ErrorCode对应ResultCode为返回外部返回码，定义时注意匹配关系。
异常处理会自动组装MicroServiceResponse对象，在web层可依据返回对象接收微服务异常code & msg。

Web层异常应使用WebRuntimeException处理，目前使用自定义异常会统一做UNKNOWN_EXCEPTION("0199", "服务器异常，请稍后重试")来处理。

#### 状态码管理
工具可以实现按位来管理状态，具体可参考代码中注释
主要包含common.util.StatusUtil和common.enums.StatusDefinition
StatusUtil                          中包含常用方法封装
StatusDefinition               中定义状态管理
StatusUtilTest                   是对应单元测试示例，可以参考






GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE,

事实上，http有以下几种请求：

GET 请求获取由Request-URI所标识的资源，get请求会在URL中显示请求的资源，一般只用于数据的读取
POST 在Request-URI所标识的资源后附加新的数据。
PUT 请求服务器存储一个资源，并用Request-URI作为其标识。PUT请求会向指定资源位置上传其最新内容，PUT方法是幂等的方法。通过该方法客户端可以将指定资源的最新数据传送给服务器取代指定的资源的内容。
DELETE 请求服务器删除由Request-URI所标识的资源。DELETE请求用于请求服务器删除所请求URI（统一资源标识符，Uniform Resource Identifier）所标识的资源。DELETE请求后指定资源会被删除，DELETE方法也是幂等的。
HEAD 请求获取由Request-URI所标识的资源的响应消息报头。HEAD方法与GET方法一样，都是向服务器发出指定资源的请求。但是，服务器在响应HEAD请求时不会回传资源的内容部分，即：响应主体。这样，我们可以不传输全部内容的情况下，就可以获取服务器的响应头信息。HEAD方法常被用于客户端查看服务器的性能。
OPTIONS 请求查询服务器的性能，或查询与资源相关的选项和需求。OPTIONS请求与HEAD类似，一般也是用于客户端查看服务器的性能。 这个方法会请求服务器返回该资源所支持的所有HTTP请求方法，该方法会用’*'来代替资源名称，向服务器发送OPTIONS请求，可以测试服务器功能是否正常。JavaScript的XMLHttpRequest对象进行CORS跨域资源共享时，就是使用OPTIONS方法发送嗅探请求，以判断是否有对指定资源的访问权限。
TRACE请求服务器回送收到的请求信息，主要用语测试或诊断。TRACE请求服务器回显其收到的请求信息，该方法主要用于HTTP请求的测试或诊断。
CONNECT能够将连接改为管道方式的代理服务器。通常用于SSL加密服务器的链接与非加密的HTTP代理服务器的通信。
————————————————
原文链接：https://blog.csdn.net/Tracy_frog/article/details/82903513


-------------------------------------------



![image](https://img.shields.io/badge/Spring%20Cloud-%E2%98%85%E2%98%85%E2%98%85-green.svg)


##  [spring-cloud-study 微服务组件学习](http://blog.csdn.net/moshowgame)



<table>
<tbody>
<tr>
<td>工程名</td>  <td>描述</td>  <td>端口</td>
</tr>
<tr>
<td>spring-cloud-study-eureka</td>  <td>服务发现与注册中心</td>  <td>8888</td>
</tr>
<tr>
<td>spring-cloud-study-zuul</td>  <td>动态转发路由器</td>  <td>7777</td>
</tr>
<tr>
<td>spring-cloud-study-demo</td>  <td>DEMO项目(含websocket，json，不含jpa)</td>  <td>9999</td>
</tr>
<tr>
<td>spring-cloud-study-configcenter</td>  <td>配置中心</td>  <td>5555</td>
</tr>
<tr>
<td>spring-cloud-study-jpa</td>  <td>JPA(hibernate实现)</td>  <td>4444</td>
</tr>
<tr>
<td>spring-cloud-study-feign</td>  <td>微服务远程调用</td>  <td>6666</td>
</tr>
<tr>
<td>spring-cloud-study-redis</td>  <td>热点数据缓存</td>  <td>2222</td>
</tr>
<tr>
<td>spring-cloud-study-jms</td>  <td>JMS(Java消息服务，ActiveMQ实现)</td>  <td>1111</td>
</tr>
<tr>
<td>spring-cloud-study-mybatisplus</td>  <td>mybatisplus(mybatis的加强版)</td>  <td>3333</td>
</tr>
<tr>
<td>spring-cloud-study-poi</td>  <td>poi导入</td>  <td>8899</td>
</tr>
<td>spring-cloud-study-aop</td>  <td>aop切面编程</td>  <td>9998</td>
</tr>
<td>spring-cloud-study-udp</td>  <td>netty udp</td>  <td>9898</td>
</tr>
</tbody></table>
<br>

### 一、运行环境

<br>
 - 版本:Spring-Cloud 2.0<br>
 - 环境:JDK1.8(8~10都可以)<br>
 - 编码:UTF-8<br>
 - IDE:Spring Tool Suit(STS)<br>

```
 <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <java.version>1.8</java.version>
  </properties>
```

### 二、有关项目启动和配置的说明

 1. 最先启动的是spring-cloud-study-eureka，因为它是注册中心，大多数微服务必须依赖于它才能实现必要的功能。 <br>
 2. 接着zuul路由中心，启用spring-cloud-study-zuul，并配置yml文件即可(已经带了一点小配置，可根据实际情况修改)。 <br>
 3. 然后启用spring-cloud-study-demo，这是一个demo项目<br>
 4. 可以启用spring-cloud-study-configcenter，这里可以从yml或者其他地方读取并统一配置变量<br>
 5. 可以启用spring-cloud-study-jpa，配置一下yml里面数据库连接池的地址，默认是127.0.0.1:3306 root/root，
启动项目可以自动建表，使用init方法可以自动初始化语句，无需自己动数据库<br>
 6. 可以启动spring-cloud-study-feign，他会远程调用demo的内容<br>
 7. 可以单独启动spring-cloud-study-redis需要自己启动一个redis，参考https://blog.csdn.net/moshowgame/article/details/80792774<br>
 8. 可以单独启动spring-cloud-study-jms，已经内置ActiveMQ，也可以自己额外配置,详情请看https://blog.csdn.net/moshowgame/article/details/80836621<br>
### 三、使用说明

eureka
----
注册中心这个优先启动，是一切微服务的基础也可以修改配置，进行集群，这里默认单机单例 <br>
<table><tbody>
<tr><td>http://127.0.0.1:8888/eureka</td> <td>注册中心</td></tr><br>
</tbody></table>

zuul
----
分发方式一，分发搭配/api1，修改yml就可以了，推荐用指定serviceId的<br>
分发方式二，分发路由，直接根据serviceId访问，无需配置<br>
转发的ServiceId是根据项目配置的spring:application:name: spring-cloud-study-demo 来的<br>
<table><tbody>
<tr><td>http://127.0.0.1:7777/spring-cloud-study-demo/demo/index</td> <td>自动分发请求</td></tr>
<tr><td>http://127.0.0.1:7777/api2/demo/index</td> <td>路由转发请求到配置文件配置的/api2上</td></tr>
<tr><td>http://127.0.0.1:7777/api1/demo/socket/222</td> <td>路由转发请求到配置文件配置的/api1上</td></tr>
</tbody></table>


demo
----
正常的访问请求而已，返回json什么的
还支持websocket，对WebSocket不懂的可以看我这个文章https://blog.csdn.net/moshowgame/article/details/80275084 <br>
<table><tbody>
<tr><td>http://127.0.0.1:9999/demo/socket/222</td> <td>websocket请求页面</td></tr>
<tr><td>http://127.0.0.1:9999/demo/index</td> <td>json数据返回</td></tr>
<tr><td>http://127.0.0.1:9999/demosocket/222</td> <td>socket请求地址</td></tr>
<tr><td>http://127.0.0.1:9999/basepath</td><td>获取微服务路径 </td></tr>
</tbody></table>

configcenter
----
配置中心,用于读取公共配置文件
<table><tbody>
<tr><td>http://127.0.0.1:5555/getparam</td><td>获取变量  </td></tr>
<tr><td>http://127.0.0.1:5555/application/dev</td><td>获取application-dev.yml的变量 </td></tr>
</tbody></table>

jpa
----
已经脱离eureka，需要加入的话自己去掉pom和启动器的eureka相关注释即可。另外，只要数据库连接正常，启动项目可以自动建表，使用init方法可以自动初始化数据，无需自己动数据库 <br>
<table><tbody>
<tr><td>http://127.0.0.1:4444/jpa/user/init/8899</td><td>初始化8899的用户 <br>
<tr><td>http://127.0.0.1:4444/jpa/user/roles/8899</td><td>获取8899用户的角色 <br>
</tbody></table>


feign
----
feign用于远程调用微服务，这里用来调用demo的内容，请先启动eureka+demo再启动这个<br>
具体配置可以看我的文章https://blog.csdn.net/moshowgame/article/details/80616624
<table><tbody>
<tr><td>http://127.0.0.1:9999/demo/getData/222?data=hahahha</td><td></td></tr>
<tr><td>http://127.0.0.1:6666/feign/remote/demo/getData/222?data=springcloudstudy</td><td>远程调用demo </td></tr>
<tr><td>http://127.0.0.1:7777/spring-cloud-study-feign/feign/remote/demo/getData/222?data=springcloudstudybyzuul</td><td>理由转发+远程调用demo </td></tr>
</tbody></table>

redis
----
操作实名参考https://blog.csdn.net/moshowgame/article/details/80792774
<table><tbody>
<tr><td>
【放入缓存】</td><td>
http://localhost:2222/redis/item/2</td><td>{"itemId":2,"itemName":"德玛西亚2"}</td></tr>
<tr><td>
【放入缓存】</td><td>
http://localhost:2222/redis/item/3</td><td>{"itemId":3,"itemName":"德玛西亚3"}</td></tr>
<tr><td>
【读取缓存(可以去RedisDesktopManager看一下，已经有数据了)】</td><td>
http://localhost:2222/redis/item/2</td><td>{"itemId":2,"itemName":"德玛西亚2"}</td></tr>
<tr><td>
【读取缓存(可以去RedisDesktopManager看一下，已经有数据了)】</td><td>
http://localhost:2222/redis/item/3</td><td>{"itemId":3,"itemName":"德玛西亚3"}</td></tr>
<tr><td>
【放入list缓存】</td><td>
http://localhost:2222/redis/item/all</td><td>[{"itemId":666,"itemName":"德玛西亚666"},{"itemId":999,"itemName":"德玛西亚999"}]</td></tr>
<tr><td>
【读取list缓存（这是一个空方法，只是因为有了缓存才有数据）】</td><td>
http://localhost:2222/redis/item/all2</td><td>[{"itemId":666,"itemName":"德玛西亚666"},{"itemId":999,"itemName":"德玛西亚999"}]</td></tr>
<tr><td>
【更新缓存】</td><td>
http://localhost:2222/redis/item/2/update</td><td>{"itemId":2,"itemName":"德玛西亚XXX2"}</td></tr>
<tr><td>
【重新读取缓存】</td><td>
http://localhost:2222/redis/item/2</td><td>{"itemId":2,"itemName":"德玛西亚XXX2"}</td></tr>
<tr><td>
【EL表达式指定KEY】</td><td>
访问地址 http://localhost:2222/redis/item/object?itemId=4&itemName=XXXX</td><td>  
返回结果 {"itemId":4,"itemName":"XXXX"}</td></tr>
<tr><td>
【获取缓存（结果不变还是XXXX不是4444）】</td><td>
访问地址 http://localhost:2222/redis/item/object?itemId=4&itemName=4444</td><td>  
返回结果 {"itemId":4,"itemName":"XXXX"}</td></tr>
</tbody></table>

jms
----
[概念和操作参考](https://blog.csdn.net/moshowgame/article/details/80836621)

<table><tbody>
<tr><td>
发送消息</td><td>
http://localhost:1111/jms/email/send</td><td>控制台显示Received <Email{to=info@example.com, body=Hello}>
</td></tr>
</tbody></table>

mybatis-plus
----
Mybatis-Plus 是一款 Mybatis 动态 SQL 自动注入 Mybatis 增删改查 CRUD 操作中间件， 减少你的开发周期优化动态维护 XML 实体字段，无入侵全方位 ORM 辅助层让您拥有更多时间陪家人。<br>

具体配置可以看我的文章https://blog.csdn.net/moshowgame/article/details/81008485
<table><tbody>
<tr><td>http://127.0.0.1:3333/mybatisplus/user/init</td><td>数据初始化</td></tr>
<tr><td>http://127.0.0.1:3333/mybatisplus/user/find</td><td>通过QueryWarrap构造器查询</td></tr>
</tbody></table>

poi-tl
----
很多时候我们网站或者系统需要提供一些word文件，例如证明.docx或者订单.docx等文件供用户下载打印等。

用Java操作word文档，毫无疑问，当下最流行apache poi，对于poi如何操作word文档，这里不作过多介绍。

这里主要讲解如何通过一个制作好的word模板文件，通过数据填充，生成加工好的word文件。

具体可以看我的文章https://blog.csdn.net/moshowgame/article/details/81702029
<table><tbody>
<tr><td>http://localhost:8899/poi/generateWord</td><td>通过Word模板生成Word文件</td></tr>
</tbody></table>

aop
----
Aspect Oriented Programming(AOP)，面向切面编程，是一个比较热门的话题。AOP主要实现的目的是针对业务处理过程中的切面进行提取，它所面对的是处理过程中的某个步骤或阶段，以获得逻辑过程中各部分之间低耦合性的隔离效果。比如我们最常见的就是日志记录了，不可能每个业务都去加写日志发的功能吧，我们就用切面，将需要记录日志的地方切一下，配置自动记录日志即可。

具体可以看我的文章https://blog.csdn.net/moshowgame/article/details/85814808
<table><tbody>
<tr><td>http://localhost:9998/aop/index</td><td>查看控制台AOP情况</td></tr>
</tbody></table>

aop
----
UDP是用户数据报协议（User Datagrame Protocol,UDP）的简称，主要作用是将网络数据流压缩成数据报的形式，提供面向事务的简单信息传送服务。
<table><tbody>
<tr><td>服务端运行方法</td><td>运行ChineseProverbServer的main方法</td></tr>
<tr><td>客户端运行方法</td><td>运行ChineseProverbClient的main方法</td></tr>
</tbody></table>

###四、版本更新
<br><br>

<table><tbody>
<tr><td>
20190122 update:<br>
- 新增基于Netty的UDP模块。
</td></tr>
<tr><td>20190105 update:<br>
- 新增AOP模块，可以练习AOP功能。<br>
- 更新SpringBoot到2.0.5版本。<br>
- 更新lombok，修复某些JDK不兼容问题<br>
</td></tr>
<tr><td>20181112 update:<br>
- 优化mybatis-plus模块的导入，使用mybatis-plus stater简化操作<br>
</td></tr>
<tr><td>20180815 update:<br>
- new POI-TL project<br>
</td></tr>
<tr><td>20180711 update:<br>
- new MybatisPlus project<br>
</td></tr>
<tr><td>20180627 update:<br>
- new Jms project<br>
</td></tr>
<tr><td>20180624 update:<br>
- new Redis project<br>
</td></tr>
<tr><td>20180609 update:<br>
- new feign project<br>
- 优化说明<br>
</td></tr>
<tr><td>20180603 update:<br>
- new jpa project<br>
- 优化调整<br>
</td></tr>
<tr><td>20180602 update:<br>
- 优化调整<br>
- new configcenter project<br>
</td></tr>
</tbody></table>
