# 问题
## Mybatis 在 insert 之后想获取自增的主键 id，但却总是返回1
 
记录一次傻逼的问题， 自己把自己蠢哭：Mybatis 在 insert 之后想获取自增的主键 id，但却总是返回1


错误说明：　　返回的1是影响的行数，并不是自增的主键id；
想要获取自增主键id，需要通过xx.getId()方法获取，因为在mybatis中指定自增主键id封装到了对象的属性中，所以我们需要在对象中来获取

 

代码示例如下：
```xml
<insert id="add"
        useGeneratedKeys="true" keyColumn="id" keyProperty="id"
        parameterType="user">
    INSERT INTO `user` (`name`,sex,register_ts) VALUES (#{name},#{sex},#{registerTs})
<selectKey resultType="int" keyProperty="id" order="AFTER">
    SELECT LAST_INSERT_ID()
</selectKey>
</insert>

```
 

总结：
1. 想要获取自增主键id，应该通过对象的getId()方法，而并不是insert的返回值，insert的返回值表示的是影响行数

2.在mapper.xml中：useGeneratedKeys="true"、keyProperty="id"，这两个属性的作用：

　　共同决定了sql执行后，会将主键封装到id属性上；

　　自增主键封装到了对象的id属性上了，那么想要获取，直接调用对象的getId()方法就可以了
 

## Mybatis源码 参数绑定集合
org.apache.ibatis.session.defaults.DefaultSqlSession.wrapCollection


EnumKeyTypeHandler
https://blog.csdn.net/Jerome_s/article/details/53965293
