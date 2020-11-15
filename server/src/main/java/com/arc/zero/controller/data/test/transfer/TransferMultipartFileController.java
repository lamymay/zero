package com.arc.zero.controller.data.test.transfer;

import com.arc.core.model.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 转发MultipartFile 文件测试
 * aServer
 * bServer
 * client send file to aServer than deliver to bServer.
 *
 * @author May
 * @since 2020/1/15 16:48
 */
@Slf4j
@Controller
public class TransferMultipartFileController {


    //接受前段上传的文件数据 转发给 v1 处理
    @PostMapping("/server/a")
    @ResponseBody
    public ResponseVo prepareFileV0(MultipartFile file) throws IOException {
        long t1 = System.currentTimeMillis();

        // 1 body
        //方法一：重新实现 ByteArrayResource 并复写   @Override   public String getFilename() {
        ByteArrayResource resource = new MultipartFileResource(file);
        // 方法二：接受到文件流后先暂时持久化到本地临时文件夹，然后转发
        //FileSystemResource resource = new FileSystemResource(new File("文件本地磁盘路径"));
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);

        //2、 headers
        HttpHeaders headers = new HttpHeaders();

        //设置 接受的类型
        ArrayList<MediaType> acceptMediaTypes = new ArrayList<>();
        acceptMediaTypes.add(MediaType.APPLICATION_JSON);

        //2.1 设置header 等效 headers.add("Accept", APPLICATION_JSON.toString());
        headers.setAccept(acceptMediaTypes);

        //2.2 设置header 是表单提交 等效 headers.setContentType(MediaType.parseMediaType("multipart/form-data;charset=UTF-8"));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        //3、构造请求体 body+header
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);

        //4、发送请求
        String url = "http://127.0.0.1:8001/zero/test/zip/upload/v1";
        ResponseEntity<Object> responseEntity = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity, Object.class);

        //测试返回数据是什么  ResponseEntity
        log.debug("BIO cost time", System.currentTimeMillis() - t1);
        log.debug("结果={}", responseEntity);
        log.debug("getStatusCodeValue结果={}", responseEntity.getStatusCodeValue());
        log.debug("getBody结果={}", responseEntity.getBody());
        log.debug("getHeaders结果={}", responseEntity.getHeaders());
        return ResponseVo.success(true);
    }


    //========================  模拟 bServer

    //数据准备
    @PostMapping("/server/b")
    @ResponseBody
    public ResponseVo prepareFileForDownloadMultiFileInZip(HttpServletRequest request, MultipartFile file) {
        long t1 = System.currentTimeMillis();
        //1、上传文件,简单校验
        if (file == null || file.isEmpty()) {
            return ResponseVo.failure("file is null");
        }

        //2、文件持久化到磁盘
        String path = "T:\\data\\output\\" + file.getOriginalFilename();
        log.debug("文件所在路径={}" + path);
        boolean result = false;
        try {
            log.debug(" try");
            file.transferTo(new File(path));
            result = true;
        } catch (IOException e) {
            log.debug("执行到catch,{}", e);
        } finally {
            log.debug("执行到finally");
        }


        //3、响应一个返回数据--这里为了测试header 数据做了获取封装后返回了，（获取到请求头数据在响应回去）
        Map<Object, Object> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            log.debug("请求头重的key/value={}/{}", key, value);
            map.put(key, value);
        }
        map.put(result, result);
        map.put("BIO cost time", System.currentTimeMillis() - t1);
        map.put("file size", file.getSize());
        map.put("file name", file.getName());
        map.put("file originalFilename", file.getOriginalFilename());
        map.put("file contentType", file.getContentType());

        return ResponseVo.success(map);
    }


    /**
     * 参考：
     * https://github.com/spring-projects/spring-framework/issues/18147
     *
     * @author May
     * @since 2020/1/15 15:32
     */
    class MultipartFileResource extends ByteArrayResource {

        private String filename;

        public MultipartFileResource(MultipartFile multipartFile) throws IOException {
            super(multipartFile.getBytes());
            this.filename = multipartFile.getOriginalFilename();
        }

        @Override
        public String getFilename() {
            return this.filename;
        }
    }
}


//    private class MultipartInputStreamFileResource extends InputStreamResource {
//
//        private final String filename;
//
//        public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
//            super(inputStream);
//            this.filename = filename;
//        }
//
//        @Override
//        public String getFilename() {
//            return this.filename;
//        }
//
//        @Override
//        public long contentLength() throws IOException {
//            return -1; // we do not want to generally read the whole stream into memory ...
//        }
//    }
