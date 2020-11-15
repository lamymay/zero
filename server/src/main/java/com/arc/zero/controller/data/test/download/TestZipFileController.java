package com.arc.zero.controller.data.test.download;

import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 测试多文件压缩后下载
 *
 * @author May
 * @since 2020/1/15 11:59
 */
@Slf4j
@Controller
@RequestMapping("/test/zip")
public class TestZipFileController {


    /**
     * 打印MultipartFile
     *
     * @param file
     */
    private void printMultipartFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();
        String name = file.getName();
        Resource resource = file.getResource();
        log.debug("name={}", name);
        log.debug("originalFilename={}", originalFilename);
        log.debug("contentType={}", contentType);
        log.debug("size={}", size);
        log.debug("resource={}", resource);
        String description = resource.getDescription();
        log.debug("description={}", description);
        //        try {
        //            URI uri = resource.getURI();
        //            log.debug("uri={}", uri);
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
    }


    /**
     * 文件持久化到磁盘
     *
     * @param file
     * @return
     */
    private boolean transferTo(MultipartFile file) {
        String path = "T:\\data\\output\\" + file.getOriginalFilename();
        File output = new File(path);
        try {
            log.debug(" try");
            file.transferTo(output);
            return true;
        } catch (IOException e) {
            log.debug(" catch");
            e.printStackTrace();
            return false;
        } finally {
            log.debug(" finally");
            log.debug("文件所在路径={}" + path);
        }
    }


    /**
     * 文件以流的形势传输给客户端
     *
     * @param file
     * @param response
     */
    private void responseFile(File file, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        try {
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OutputStream outputStream = null;
        try {
            InputStream inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            long count = 0L;
            for (count = 0L; -1 != (n = inputStream.read(buffer)); count += (long) n) {
                outputStream.write(buffer, 0, n);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.debug("finally");
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输出header
     *
     * @param request
     */
    private void printHeader(HttpServletRequest request) {
        // 拦截 用户，获取用户的ticket 去认证中心校验，然后根据返回值做判断是否继续授权后续操作
        //cas单点登录的用户名就是：_cas_stateful_ ，用户凭证是server传回来的ticket
        // string username = CasAuthenticationFilter.CAS_STATEFUL_IDENTIFIER;
        log.debug("------------------------------------------------------------------------------------------");
        log.debug("IP={} :", request.getRemoteAddr());
        log.debug("x-forwarded-for={} :", request.getHeader("x-forwarded-for"));
        log.debug("Proxy-Client-IP={} :", request.getHeader("Proxy-Client-IP"));
        log.debug("WL-Proxy-Client-IP={} :", request.getHeader("WL-Proxy-Client-IP"));
        log.debug("HTTP_CLIENT_IP={} :", request.getHeader("HTTP_CLIENT_IP"));
        log.debug("HTTP_X_FORWARDED_FOR={} :", request.getHeader("HTTP_X_FORWARDED_FOR"));
        log.debug("------------------------------------------------------------------------------------------");
    }


    //文件下载 固定的一个文件
    @GetMapping("/download/v1")
    @ResponseBody
    public ResponseVo downloadMultiFileInZip(HttpServletRequest request, HttpServletResponse response) {
        //准备文件
        File file = new File("T:\\data\\output\\123.PNG");
        responseFile(file, response);
        return ResponseVo.success();
    }

    //文件下载2
    @RequestMapping("/download/v2")
    public void testV2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        //获得要下载的文件名
        String fileRootPath = "T:\\data\\output\\";
        String zipName = System.currentTimeMillis() + ".zip";
        String zipPath = fileRootPath + zipName;

        //1、获取文件列表 List<File>        List<File> fileList = new ArrayList<>();
        File file1 = new File(fileRootPath + "girl04.jpg");
        File file2 = new File(fileRootPath + "用户导入模板.xlsx");


        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file1));
        byte[] buffer = new byte[1024];
        int read = 0;


        //压缩
        ZipOutputStream zipOutput = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipPath)));
        zipOutput.putNextEntry(new ZipEntry(file1.getName()));
        while ((read = bis.read(buffer)) != -1) {
            zipOutput.write(buffer, 0, read);
        }

        zipOutput.putNextEntry(new ZipEntry(file2.getName()));
        bis = new BufferedInputStream(new FileInputStream(file2));
        while ((read = bis.read(buffer)) != -1) {
            zipOutput.write(buffer, 0, read);
        }

        bis.close();
        zipOutput.close();
        //创建输出流，下载zip
        try (OutputStream out = response.getOutputStream();
             FileInputStream in = new FileInputStream(new File(zipPath));) {
            //设置响应头，控制浏览器下载该文件
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + java.net.URLEncoder.encode(zipName, "UTF-8"));
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } finally {
            try {
                //删除压缩包
                log.debug(" zip下载路径：{}", zipPath);
                File zfile = new File(zipPath);
                zfile.delete();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("清理临时文件出错={}", e);
            }
        }
    }

    //http://127.0.0.1:8001/zero/test/zip/download/v3
    //文件下载3-- 遍历一个文件夹
    //todo 临时文件夹
    @RequestMapping("/download/v3")
    public void testV3(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        //获得要下载的文件名
        String fileRootPath = "T:\\data\\output\\";
        String zipName = System.currentTimeMillis() + ".zip";
        String zipPath = fileRootPath + zipName;

        //1、获取文件列表 List<File>
        List<File> fileList = listFile(fileRootPath);

        if (fileList == null || fileList.size() == 0) {
            throw new BizException(ProjectCodeEnum.FILE_NOT_EXIST_ERROR);
        }

        ZipOutputStream zipOutput = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipPath)));
        BufferedInputStream bis = null;
        byte[] buffer = new byte[1024];
        int read = 0;

        for (File file : fileList) {
            bis = new BufferedInputStream(new FileInputStream(file));

            //压缩
            zipOutput.putNextEntry(new ZipEntry(file.getName()));
            while ((read = bis.read(buffer)) != -1) {
                zipOutput.write(buffer, 0, read);
            }
        }

        bis.close();
        zipOutput.close();
        //创建输出流，下载zip
        try (OutputStream out = response.getOutputStream();
             FileInputStream in = new FileInputStream(new File(zipPath));) {
            //设置响应头，控制浏览器下载该文件
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + java.net.URLEncoder.encode(zipName, "UTF-8"));
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } finally {
            try {
                //删除压缩包
                log.debug(" zip下载路径：{}", zipPath);
                File zfile = new File(zipPath);
//                zfile.delete();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("结果={}", e);
            }
        }
    }


    //http://www.imooc.com/wenda/detail/563351
    private static List<File> listFile(String rootFileFolder) {
        File folder = new File(rootFileFolder);
        File[] listOfFiles = folder.listFiles();
        List<File> fileList = null;
        if (listOfFiles != null && listOfFiles.length > 0) {
            fileList = new LinkedList<>();
        }
        for (File file : listOfFiles) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }
        return fileList;
    }


    public static void main(String[] args) {
        String rootFileFolder = "T:\\data\\output\\";
        List<File> fileList = listFile(rootFileFolder);
        for (File file : fileList) {
            log.debug("文件名称={}",file.getName());
        }
    }
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

            map.forEach(
                    (name, path) -> {
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
                    }

            );
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


    private void zipFile(List<File> files, ZipOutputStream zipOutputStreamut) throws IOException {
        for (File file : files) {
            //从数组中取出单个文件进行压缩
            zipFile(file, zipOutputStreamut);
        }

    }

    private void zipFile(File file, ZipOutputStream zipout) throws IOException {
        if (!file.exists()) {
            return;
        }

        FileInputStream in = new FileInputStream(file);
        BufferedInputStream bins = new BufferedInputStream(in, 512);
        ZipEntry entry = new ZipEntry(file.getName());
        zipout.putNextEntry(entry);
        //向压缩文件中输出数据
        int number;
        byte[] buffer = new byte[512];
        while ((number = bins.read(buffer)) != -1) {
            zipout.write(buffer, 0, number);
        }
        bins.close();
        in.close();
    }

    //下载的方法
    private void downLoadZip(File file, HttpServletResponse response) throws IOException {
        // 以流的形式下载文件。
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file.getPath()));
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        inputStream.close();

        response.reset();
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
        toClient.write(buffer);

        toClient.flush();
        toClient.close();
    }


    /**
     * 功能:压缩多个文件成一个zip文件
     *
     * @param srcfile：源文件列表
     * @param zipfile：压缩后的文件
     */
    public static void zipFiles(File[] srcfile, File zipfile) {
        byte[] buf = new byte[1024];
        try {
            //ZipOutputStream类：完成文件或文件夹的压缩
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                // 给列表中的文件单独命名
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
