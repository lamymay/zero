用axios发送请求获取reponse header中的数据
在使用CORS解决跨域的请求中，默认只能取到
Content-Language
Content-Type
Expires
Last-Modified
Pragma
五个reponse header 值。
如果想获取到其他的值，需要服务器在header中设置
Access-Control-Expose-Headers : sessionID ， key1 , key2

```$java
             response.setContentType("image/jpg");
                response.setHeader("content-disposition", "attachment;filename=" + fileName);
                response.setHeader("filename", fileName);
                response.setHeader("Access-Control-Expose-Headers ", "Content-Disposition,filename");

```
[参考原文链接](https://blog.csdn.net/weixin_39205240/article/details/82531305)

