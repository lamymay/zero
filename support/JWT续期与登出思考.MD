

1.token与jwt的区别
    （1）简单的说，token只是一个标识，以token加redis为例，服务端将token保存在redis中，客服端访问时带上token，如果在redis中能够查到这个token，说明身份有效。

    （2）jwt不需要查库，本身已经包含了用户的相关信息，可以直接通过服务端解析出相关的信息，与session，token的最大区别就是服务端不保存任何信息。

2.如何实现jwt续期
在jwt中保存过期时间，解析时进行判定，如果即将超时则重新设置过期时间返回一个新的jwt给客户端。

3.jwt登出失效
登出时将相关的信息比如用户名存储在redis中，并设置过期时间。当再次访问时，从jwt中解析出用户名去redis中查找，如果存在则表示此jwt已登出失效。这里需要注意的是，如果用此方法，则验证jwt是否登出应该放在第一位。思考一个场景，如果redis中存储的是用户名，那么当用户登出后，redis中已经有了相应的用户名，当用户再次登录时，解析jwt发现此用户已登出，则jwt失效，所以在登录时要清空相关的登出缓存。
--------------------- 
原文：https://blog.csdn.net/qq_28726483/article/details/80922334 


半个月内免登陆，这里就要使用到了refreshToken了，jwt设计思想很到位：设置发给前端的token一个有效期，比如2个小时，2个小时候前端发来的token就会失效，这个时候我们根据发来的token判断下，如果这个token在2个小时外，并在刷新token的有效期内（比如半个月内），那么我们在给前端返回数据的时候返回一个新token，前端接到这个token存储起来，当再次请求的时候，发送新的token，如此周而复始，只要你在半个月内没有间断去进入系统，那么完全不需要去进行登录的操作。



---
这个问题找了在github里面看到了issue但是无人回答，jwt肯定不会发两个新的token回去的，那麽肯定会有一个token不仅是失效了，刷新当前token之后，产生新的token，旧token加入到了backlist中了，无法使用，那么另外一个请求自然无法成功。我这里使用Redis解决的，将旧token作为键，新token作为值，设置一个30秒过期的时间。当第二个请求来的时候，已经知道token在backlist中了，我们可以去redis查询下是否存在这么个旧token，存在的话放行。

https://blog.csdn.net/superdog007/article/details/80704234
https://www.cnblogs.com/lyzg/p/6028341.html

   }
   
 
