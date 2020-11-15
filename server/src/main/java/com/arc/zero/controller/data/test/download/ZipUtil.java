package com.arc.zero.controller.data.test.download;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * @author May
 * @since 2020/1/15 12:16
 */
@Slf4j
public class ZipUtil {

    /**
     * 多文件zip压缩下载 （说明：读取文件流到zip流中，之后下载）
     *
     * @param response
     * @param downloadName 下载后的压缩包名，带后缀
     * @param map          存放文件信息的map ，key为该文件压缩后的目录层级路径如：/xxx/xxx/xxx.jpg，为文件名时则在压缩包的顶层
     *                     如：xxx.jpg。 value为下载文件所在的路径
     */
    public static void MultiFileZipDownload(
            HttpServletResponse response,
            String downloadName,
            Map<String, String> map) {
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ZipOutputStream zos = new ZipOutputStream(outputStream);
        try {
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(downloadName, "UTF-8"));
            map.forEach((name, path) -> {
                InputStream is = null;
                BufferedInputStream in = null;
                byte[] buffer = new byte[1024];
                int len;
                //创建zip实体（一个文件对应一个ZipEntry）
                //name --->压缩包的层级路径
                ZipEntry entry = new ZipEntry(name);
                try {
                    //获取需要下载的文件流
                    File file = new File(path);
                    is = new FileInputStream(file);
                    in = new BufferedInputStream(is);
                    zos.putNextEntry(entry);
                    //文件流循环写入ZipOutputStream
                    while ((len = in.read(buffer)) != -1) {
                        zos.write(buffer, 0, len);
                    }
                } catch (Exception e) {
                    log.info("下载全部附件--压缩文件出错", e);
                } finally {
                    if (entry != null) {
                        try {
                            zos.closeEntry();
                        } catch (Exception e) {
                            log.info("下载全部附件--zos实体关闭失败", e);
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Exception e) {
                            log.info("下载全部附件--文in流关闭失败", e);
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception e) {
                            log.info("下载全部附件-is流关闭失败", e);
                        }
                    }
                }
            });
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (Exception e2) {
                    log.info("关闭zos流时出现错误{}", e2);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e2) {
                    log.info("关闭outputStream时出现错误{}", e2);
                }
            }

        }
    }


    public static void extZipFile(String zipFileName, String extPlace) throws IOException {
        FileInputStream is = new FileInputStream(new File(zipFileName));
        ZipInputStream zipIs = new ZipInputStream(is);
        ZipEntry entry = null;
        while ((entry = zipIs.getNextEntry()) != null) {
            String entryName = entry.getName();
            System.out.println("entryName= " + entryName);
            if (entry.isDirectory()) {
                File file = new File(extPlace + entryName);
                file.mkdirs();
            } else {
                FileOutputStream os = new FileOutputStream(extPlace + entryName);
                // Transfer bytes from the ZIP file to the output file
                byte[] buf = new byte[2048];
                int len;
                while ((len = zipIs.read(buf)) > 0) {
                    os.write(buf, 0, len);
                }
            }
        }
    }

    public static void main(String[] args) {
        //准备请求参数  body 与header

        // 1 body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        //        MultipartFile multipartFile = new MultipartFile();

        File file = new File("T:\\data\\girl04.jpg");
        byte[] byteArray = null;
        ByteArrayResource byteArrayResource = new ByteArrayResource(byteArray);

//        FileSystemResource fileSystemResource = new FileSystemResource(file);
        body.add("file", byteArrayResource);

        //2 headers
        HttpHeaders headers = new HttpHeaders();
        ArrayList<MediaType> acceptMediaTypes = new ArrayList<>();
        acceptMediaTypes.add(APPLICATION_JSON);
        headers.setAccept(acceptMediaTypes);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        long contentLength = byteArrayResource.contentLength();
        System.out.println(contentLength);
        System.out.println(contentLength);
        System.out.println(contentLength);
//        headers.add("Content-Length", MediaType.APPLICATION_JSON.toString());
        headers.setContentType(MediaType.parseMediaType("multipart/form-data;charset=UTF-8"));
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);


        //发送请求
        String url = "http://127.0.0.1:8001/zero/test/zip/upload/v1";
        ResponseEntity<Object> responseEntity = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity, Object.class);

        //测试返回数据是什么  ResponseEntity
        System.out.println(responseEntity);
        System.out.println(responseEntity.getStatusCodeValue());
        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.getBody());
    }
}
/*


//
response.setHeader("Content-Disposition", "attachment;fileName=" +  new String(downloadName.getBytes(),"iso-8859-1"));

//        兼容写法

        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko") ) {
        // win10 ie edge 浏览器 和其他系统的ie
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(downloadName, "UTF-8"));
        } else {
        // 其他浏览器
        response.setHeader("Content-Disposition", "attachment;fileName=" +  new String(downloadName.getBytes(),"iso-8859-1"));
        }

*/
