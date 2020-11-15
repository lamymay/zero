package com.arc.zero.service.system.impl;

import com.arc.core.model.domain.system.SysFile;
import com.arc.core.model.request.system.file.FileRequest;
import com.arc.core.model.request.system.file.SysFilePageRequest;
import com.arc.core.model.request.system.file.SysFileRequest;
import com.arc.zero.mapper.system.SysFileMapper;
import com.arc.zero.service.system.SysFileService;
import com.arc.utils.file.FileUtil;
import com.arc.zero.utils.FileCleaner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yechao
 * @since 2019/1/13 19:41
 */
@Slf4j
@Service
public class SysFileServiceImpl implements SysFileService {

    @Value("${spring.profiles:local}")
    private String profile;

    @Value("${server.port:80}")
    private int port;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    final static String protocol = "http://";

    @Resource
    private SysFileMapper sysFileMapper;


    @Override
    public Long save(SysFile sysFile) {

        if (sysFile.getCode() == null || sysFile.getCode().trim().length() == 0) {
            log.debug(" 异常   sysFile.getCode() == nul");
            return null;
        }

        // todo 获取ip
        //String addr = "127.0.0.1";
        String addr = null;
        log.debug("结果={}", profile);
        log.info("profile={}", profile);
        log.info("profile={}", profile);
        log.info("profile={}", profile);

        if ("self".equals(profile)) {
            addr = "122.51.110.127";
            //因为数据库账号密码的问题  self [限制为%]与 160[限制指定IP] 的是不一样的 当本地调试的时候需要切换 注释
        } else if ("160".equals(profile)) {
            addr = "122.51.110.127";
        } else {
            addr = "127.0.0.1";
        }

        String os = System.getenv("OS");
        log.debug("os 结果={}", os);
        log.debug("os 结果={}", os);
        log.info("os 结果={}", os);
        log.info("os 结果={}", os);

        if ("Windows_NT".equals(os)) {
            addr = "127.0.0.1";
        }

        sysFile.setLocation(profile);
        sysFile.setHost(protocol + addr + ":" + port);
        //todo  sysFile.getCode()  =  文件名称+后缀   绝对路径  就是 path     资源名称 就是 uri+ 文件全名称
        sysFile.setUri(protocol + addr + ":" + port + contextPath + "/file/" + sysFile.getName() + sysFile.getSuffix());

        return sysFileMapper.save(sysFile) == 0 ? null : sysFile.getId();
    }

    @Override
    public Boolean delete(Long id) {
        return sysFileMapper.delete(id)!=0;
    }

    @Override
    public int update(SysFile sysFile) {
        return sysFileMapper.update(sysFile);
    }

    @Override
    public SysFile get(Long id) {
        return sysFileMapper.get(id);
    }

    @Override
    public SysFile getByCode(String code) {
        return sysFileMapper.getByCode(code);
    }

    @Override
    public List<SysFile> list() {
        return sysFileMapper.list();
    }

    /**
     * 文件持久化并在数据库做记录
     * 注意文件名称保证不相同，不存在重复文件覆盖问题，同时带来一个问题，前端相同文件重复上传造成服务端资源浪费，建议用定时线程去清理无效的重复文件
     * 记录日志
     * 判合法性，非空，大小，格式
     * 1、文件写入磁盘,注意文件不会被覆盖，因为不存在同名文件
     * 2、描述信息记录数据库
     *
     * @param file    文件
     * @param tempDir 目录
     * @return 数据库标记的code，用于查询
     */
    @Override
    public String writeFileToDiskAndRecord(MultipartFile file, String tempDir) {
        //返回code

        log.debug("getName={}", file.getName());//变量的名称 file
        log.debug("getResource={}", file.getResource());//MultipartFile resource [file]
        log.debug("getContentType={}", file.getContentType());
        //image/jpeg
        // application/vnd.openxmlformats-officedocument.wordprocessingml.document

        log.debug("getOriginalFilename={}", file.getOriginalFilename());//记录.docx


        //需求判断文件是否为空
        if (file != null && !file.isEmpty()) {
            log.debug("文件上传入参: 类型={}，名称={}，尺寸={} bytes", file.getContentType(), file.getOriginalFilename(), file.getSize());

            //文件落地--文件名称 文路径
            //存在该文件夹吗？
            //是文件夹吗？
            File outFile = new File(tempDir);

            if (!outFile.exists()) {
                // boolean mkdir() :  创建此抽象路径名指定的目录。 父级路径若不存在则不会创建该目录。
                // boolean mkdirs() :  创建此抽象路径名指定的目录，包括创建必需但不存在的父目录。父级路径若不存在则会创建该目录。
                boolean mkdirs = outFile.mkdirs();
                if (!mkdirs) {
                    throw new RuntimeException("文件夹不存在，并创建失败，文件终止保存");
                }
            }

            //getAbsolutePath()  方法去除了干扰   ./   ../
            String writeFile = outFile.getAbsolutePath() + File.separator + FileUtil.builtTargetFileName(file.getOriginalFilename());
            String toDiskPath = null;
            try {
                toDiskPath = FileUtil.writeToDisk(file.getInputStream(), writeFile);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("error={}", e);
            }
            if (toDiskPath != null) {
                //描述信息记录数据库
                SysFile sysFile = createSysFile(file, toDiskPath);
                sysFile.setVersion(1);
                // todo 获取ip
                //String addr = "127.0.0.1";
                String addr = null;
                log.debug("结果={}", profile);
                log.info("profile={}", profile);
                log.info("profile={}", profile);
                log.info("profile={}", profile);

                if ("self".equals(profile)) {
                    addr = "122.51.110.127";
                    //因为数据库账号密码的问题  self [限制为%]与 160[限制指定IP] 的是不一样的 当本地调试的时候需要切换 注释
                } else if ("160".equals(profile)) {
                    addr = "122.51.110.127";
                } else {
                    addr = "127.0.0.1";
                }

                String os = System.getenv("OS");
                log.debug("os 结果={}", os);
                log.debug("os 结果={}", os);
                log.info("os 结果={}", os);
                log.info("os 结果={}", os);

                if ("Windows_NT".equals(os)) {
                    addr = "127.0.0.1";
                }

//                try {
//                    addr = InetAddress.getLocalHost().getHostAddress();//获得本机IP
//                    log.debug("Java获取本机ip和服务器ip={}",addr);
//                    log.debug("Java获取本机ip和服务器ip={}",addr);
//                    log.info("Java获取本机ip和服务器ip={}",addr);
//                    log.info("Java获取本机ip和服务器ip={}",addr);
//                    log.info("Java获取本机ip和服务器ip={}",addr);
//                } catch (UnknownHostException e) {
////                    e.printStackTrace();
//                    throw new BizException(e);
//                }
                //todo  sysFile.getCode()  =  文件名称+后缀   绝对路径  就是 path     资源名称 就是 uri+ 文件全名称
                sysFile.setUri(protocol + addr + ":" + port + contextPath + "/file/" + sysFile.getCode());

                Long id = this.save(sysFile);
                return id == null ? null : toDiskPath;
            }
        }
        return null;

    }

    /**
     * 构建可入库的数据
     *
     * @param file
     * @param toDiskPath
     * @return
     */
    private SysFile createSysFile(MultipartFile file, String toDiskPath) {
        SysFile sysFile = new SysFile();
        //全名
        String originalFilename = file.getOriginalFilename();

        sysFile.setName(originalFilename);
        sysFile.setNote("文件上传");
        String suffix = "";
        int lastIndexOf = originalFilename.lastIndexOf(".");
        if (lastIndexOf != -1) {
            suffix = originalFilename.substring(lastIndexOf + 1, originalFilename.length());
        }
        sysFile.setSuffix(suffix);
        sysFile.setLength(file.getSize());
        sysFile.setPath(toDiskPath);
        int index = toDiskPath.lastIndexOf(File.separator);
        sysFile.setCode(toDiskPath.substring(index + 1, index + 45 + 1));
        sysFile.setCreateTime(new Date());
        sysFile.setState(1);

        return sysFile;
    }

    @Override
    public SysFile getByRequest(SysFileRequest request) {
        return sysFileMapper.getByRequest(request);
    }

    public static void main(String[] args) {
        System.out.println(File.separator);
        System.out.println("1553674217365a397a335a8c54dce9d422da32ddbbef6".length());
        String temp = "C:\\Users\\X\\Desktop\\UP\\A\\155367377470183239d3c40fb4abebf0efd383e20d854_DSC_0007.JPG";
        //45
        int index = temp.lastIndexOf("\\");

        String code = temp.substring(index + 1, index + 45 + 1);
        System.out.println(code);
    }

    @Override
    public List<SysFile> listByRequest(SysFileRequest request) {
        return sysFileMapper.listByRequest(request);
    }

    @Override
    public List<SysFile> list(SysFileRequest request) {
        return sysFileMapper.listByRequest(request);
    }


    @Override
    public Page<SysFile> listPgae(SysFilePageRequest request) {
        List<SysFile> list = sysFileMapper.list();
        int total = sysFileMapper.count();
        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getPageSize());

        PageImpl<SysFile> sysFiles = new PageImpl<>(list, pageable, total);
        //  todo 修正分页数据的封装    PageArc pageArc = new PageArc(request.getCurrentPage(), total, list);
        return sysFiles;
    }

    @Override
    public SysFile getById(Long id) {
        return sysFileMapper.get(id);
    }

    @Override
    public int deleteByCode(String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        return deleteByRequest(map);
    }

    @Override
    public int deleteByRequest(Map<String, Object> map) {
        return sysFileMapper.deleteByRequest(map);
    }


    @Override
    public void deleteByRequest(FileRequest request) {
        //删除数据库记录
        // 删除磁盘上文件

        //两种方案：
        // 1、立即删除--先查然后在删除文件--最后删除表中数据----->改进 查到后并发调用两个线程同时删除文件以及库中数据并发记录操作流水
        // 2、update表中数据的标识后、由其他方法异步删除
        Long id = request.getId();
        concurrencyClean(id);

//        int delete = sysFileMapper.delete  (request.getId());
    }

    @Autowired
    FileCleaner fileCleaner;

    private void concurrencyClean(Long id) {
        fileCleaner.clean(id);
    }


    @Override
    public int saveBatch(List<SysFile> sysFileList) {
        int result = 0;
        for (SysFile sysFile : sysFileList) {
            Long save = save(sysFile);
            if (save != null) {
                result = result + 1;
            }
        }
        return result;
    }

    @Override
    public SysFile getByIdOrCode(String idOrCode) {
        log.debug("文件下载，参数接受 code / id={}", idOrCode);
        Long id = null;
        SysFile sysFile = null;
        try {
            id = Long.valueOf(idOrCode);
            sysFile = sysFileMapper.get(id);
        } catch (Exception e) {
            log.error("文件下载中，code 转换为 id error=", e);
        }

        if (sysFile == null) {
            // 尝试用 code去精确匹配
            sysFile = sysFileMapper.getByCode(idOrCode);
        }
        return sysFile;
    }
}
