package com.arc.zero.test.uri;


//import cn.hutool.core.util.URLUtil;

import java.net.URL;

/**
 * @author yechao
 * @date 2020/9/10 9:17 下午
 */
public class TestUri {


    public static void main(String[] args) {

        //fun1();
        //fun2();
        fun3();
        //fun4();
        fun5();

    }

    private static void fun1() {
        System.out.println("--------------- 预期全部是 true------------------");
        System.out.println(verifyUri("https://www.baidu.com"));
        System.out.println(verifyUri("http://www.baidu.com"));
        System.out.println(verifyUri("baidu.com"));
        System.out.println("----------------------------------");
        System.out.println(verifyUri("https://www.z.cn"));
        System.out.println(verifyUri("http://www.z.cn"));
        System.out.println(verifyUri("http://www.z.cn:444/index"));
        System.out.println(verifyUri("www.z.cn"));
        System.out.println(verifyUri("z.cn"));
        System.out.println("----------------------------------");
        System.out.println(verifyUri("https://127.0.0.1:8080/info"));
        System.out.println(verifyUri("http://127.0.0.1:8080/info"));
        System.out.println(verifyUri("127.0.0.1:8080/info"));
    }

//    private static void fun2() {
//        System.out.println("--------------- 预期全部是 true------------------");
//        System.out.println(verifyUri2("https://www.baidu.com"));
//        System.out.println(verifyUri2("http://www.baidu.com"));
//        System.out.println(verifyUri2("baidu.com"));
//        System.out.println("----------------------------------");
//        System.out.println(verifyUri2("https://www.z.cn"));
//        System.out.println(verifyUri2("http://www.z.cn"));
//        System.out.println(verifyUri2("http://www.z.cn:444/index"));
//        System.out.println(verifyUri2("www.z.cn"));
//        System.out.println(verifyUri2("z.cn"));
//        System.out.println("----------------------------------");
//        System.out.println(verifyUri2("https://127.0.0.1:8080/info"));
//        System.out.println(verifyUri2("http://127.0.0.1:8080/info"));
//        System.out.println(verifyUri2("127.0.0.1:8080/info"));
//    }

    private static boolean fun3() {
        try {
//            String uri = "baidu.com";//no protocol: baidu.com
            String uri = "https://baidu.com";//
            URL url = new URL(uri);
            System.out.println(url);

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        //        return "http".equals(url.getProtocol());
    }


//    private static void fun4() {
//        System.out.println("--------------- 预期全部是 true------------------");
//        System.out.println(isUri("https://www.baidu.com"));
//        System.out.println(isUri("http://www.baidu.com"));
//        System.out.println(isUri("baidu.com"));
//        System.out.println("----------------------------------");
//        System.out.println(isUri("https://www.z.cn"));
//        System.out.println(isUri("http://www.z.cn"));
//        System.out.println(isUri("http://www.z.cn:444/index"));
//        System.out.println(isUri("www.z.cn"));
//        System.out.println(isUri("z.cn"));
//        System.out.println("----------------------------------");
//        System.out.println(isUri("https://127.0.0.1:8080/info"));
//        System.out.println(isUri("http://127.0.0.1:8080/info"));
//        System.out.println(isUri("127.0.0.1:8080/info"));
//    }


    private static void fun5() {
        boolean compareTime = false;
        String[] schemes = {"http","https"}; // DEFAULT schemes ="http","https","ftp"
//        UrlValidator urlValidator = new UrlValidator(schemes);
//        if (urlValidator.isValid("ftp://foo.bar.com/")) {
//            System.out.println("URL is valid");
//        } else {
//            System.out.println("URL is invalid");
//        }


    }

    public static boolean verifyUri(String uri) {
        URL url;
        try {
            url = new URL(uri);
        } catch (Exception e1) {
            return false;
        }
        return "http".equals(url.getProtocol());
    }

//    public static boolean isUri(String uri) {
//        try {
//            String withProtocol = cn.hutool.core.util.URLUtil.normalize(uri);
//            System.out.println("原始为=" + uri + "\n变换后=" + withProtocol);
//            URL url = new URL(withProtocol);
//            return true;
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return false;
//        }
//    }

//    public static boolean verifyUri2(String uri) {
//        //URLUtil.url 通过一个字符串形式的URL地址创建对象
//        //URLUtil.getURL 主要获得ClassPath下资源的URL，方便读取Classpath下的配置文件等信息。
//
//        //URLUtil.normalize 标准化化URL链接。对于不带http:
//        //头的地址做简单补全。
//        String url = "http://www.hutool.cn//aaa/bbb";
//        // 结果为：http://www.hutool.cn/aaa/bbb
//        String normalize = cn.hutool.core.util.URLUtil.normalize(url);
//
//        url = "http://www.hutool.cn//aaa/\\bbb?a=1&b=2";
//        // 结果为：http://www.hutool.cn/aaa/bbb?a=1&b=2
//        normalize = URLUtil.normalize(url);
//        //URLUtil.encode 封装URLEncoder.encode，将需要转换的内容（ASCII码形式之外的内容），用十六进制表示法转换出来，并在之前加上 % 开头。
//        String body = "366466 - 副本.jpg";
//        // 结果为：366466%20-%20%E5%89%AF%E6%9C%AC.jpg
//        String encode = URLUtil.encode(body);
//        //URLUtil.decode 封装URLDecoder.decode，将 % 开头的16进制表示的内容解码。
//        //URLUtil.getPath 获得path部分 URI -> http://www.aaa.bbb/search?scope=ccc&q=ddd PATH -> /search
//        //URLUtil.toURI 转URL或URL字符串为URI。
//        return false;
//    }
}


/*
fun Context.openBrowser(url: String) {
    val intent = Intent()
    intent.action = "android.intent.action.VIEW"
    intent.data = Uri.parse(url)
    startActivity(intent)
}

//启动android默认浏览器 不指定浏览器，此时若设备设置了默认浏览器，则打开的默认浏览器，否则如果设备中有多个浏览器则会弹窗让用户选择要打开哪个浏览器
Uri uri = Uri.parse("https://www.baidu.com");
Intent intent = new Intent(Intent.ACTION_VIEW, uri);
startActivity(intent);


https://www.codenong.com/1600291/
* */
