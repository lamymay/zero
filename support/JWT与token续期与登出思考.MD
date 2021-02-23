
## token与jwt
（1）简单的说，token只是一个简单标识，协助server以此标识识别用户身份，可能会检索db获得用户信息然后进行后续业务交互
        例:生成常用token做为redis key，value存用户信息或者存用户id，若检索出来用户信息则说明用户身份有效。
（2）jwt是自含用户身份信息的一个token，可以利用算法解析出来token字符串中的用户信息，减少系统IO次数提高效率 。 其实从名字都可以看出两个都是token，token与session，token的最大区别就是服务端不保存任何信息，即:无状态。


## 半个月内免登录设计？（实际上就是如何实现 jwt refreshToken）
过期时间太长风险较大，建议采用自动刷新token方式来做： jwt设计中就预留了一个字段有效期，可以理用此时间来做。


## jwt登出失效设计？
方案一：
登出时候，客户端清除本地缓存token，下次与server交互无token则需要重写登录，此时系统依然是无状态的。

方案二：
server要知道哪些token是有效的，才能判定token是有效还是无效，需要借助存储系统记录，
登录时候做标记，登出时将标记删除，
例：登录相关的信息比如用户id存储在redis中，并设置过期时间。当下次访问时，从jwt中解析出用户id去redis中查找，若查到则就删除此key，表示此token无效了；查不到亦表示token失效。主要token续期逻辑需要刷新此key


## 思考如何向客户端刷新token

## 思考并发请求时候会怎样



相关文章：
https://blog.csdn.net/superdog007/article/details/80704234
https://www.cnblogs.com/lyzg/p/6028341.html






