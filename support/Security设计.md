# 注意：  identifier=username        credential=password

# 之前
SELECT * FROM t_sys_user  WHERE username='admin'

#拆分后
SELECT * FROM t_sys_user AS user_
LEFT JOIN t_sys_user_auth    AS user_auth_ ON  user_.id= user_auth_.user_id
WHERE user_auth_.identity_type=1 and user_auth_.identifier='username'


#如果使用第三方登录，则只要判断

说说具体处理，用户发来邮箱/用户名/手机号和密码请求登录的时候，依然是先判断类型，以某用户使用了手机号登录为例，使用
SELECT * FROM user_auth WHERE type=’phone’ and identifier=’手机号’ 查找条目，如有，取出并判断password_hash(密码)是否和该条目的credential相符，相符则通过验证，随后通过user_id获取用户信息。

如果使用第三方登录，则只要判断 SELECT * FROM user_auths WHERE type=’weixin’ and identifier=’微信UserName’ ，如果有记录，则直接登录成功，使用新的token更新原token。假设与微信服务器通信不被劫持的情况下无需判断凭证问题。

通过这个表结构设计，原来扩展登录方式不便的问题被化解



[LDAP](https://www.cnblogs.com/wilburxu/p/9174353.html)


要加快文件拷贝速度，不外乎
［１］　大容量内存做缓冲
［２］　多线程ＩＯ读写 [windows支持异步读写]
［３］　高效率的目录结构扫描函数，并支持同步

因为最终还是要受系统硬件的局限的，软件能做的只有那么多
