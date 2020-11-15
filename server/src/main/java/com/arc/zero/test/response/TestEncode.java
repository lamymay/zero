package com.arc.zero.test.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author {author}
 * @since 2019/11/23 14:34
 */
public class TestEncode {

    public static void main(String[] args) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        keygen.initialize(512);
        KeyPair kp = keygen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        System.out.println("KEY:\n" + bytesToHexString(publicKey.getEncoded()) + "\n");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        System.out.println("RESULT:\n" + bytesToHexString(cipher.doFinal("ilanyu".getBytes())) + "\n");
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getUser() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "user");
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }


    //需要多加response参数，但这样的控制器都必须为void，有诸多限制。
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws IOException {
        response.setStatus(500);
        response.getWriter().append("server error");
    }


    //前后端分离开发一般都会自定义返回的数据格式。
    //都会包含msg,code,data三个属性。
    //可用上述思路实现以下工具类。实现自定义返回的状态码，需将返回格式设置为以下类的格式，将返回数据传入data


    //前后端分离开发一般都会自定义返回的数据格式。
    //都会包含msg,code,data三个属性。
    //可用上述思路实现以下工具类。实现自定义返回的状态码，需将返回格式设置为以下类的格式，将返回数据传入data
}
