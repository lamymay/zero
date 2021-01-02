package com.arc.zero.service.system;

import com.arc.core.model.domain.system.SysFile;
import com.arc.core.model.request.system.file.SysFileRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * JAVA项目是分层来写的，
 * 这是服务层，目的是处理业务，
 * 文件记录表相关服务
 *
 * @author yechao
 * @date 2018/12/21
 */
public interface SysFileService {

    Long save(SysFile sysFile);

    Boolean deleteById(Long id);

    int deleteByCode(String code);

    int deleteByRequest(Map<String, Object> map);

    /**
     * 删除文件并且清理
     * @param request 删除参数
     * @param cleanDisk
     * @return
     */
    boolean deleteAndCleanFile(SysFileRequest request, boolean cleanDisk);

    int update(SysFile sysFile);

    SysFile get(Long id);

    /**
     * 文件持久化并在数据库做记录
     * 注意文件名称保证不相同，不存在重复文件覆盖问题，同时带来一个问题，前端相同文件重复上传造成服务端资源浪费，建议用定时线程去清理无效的重复文件
     *
     * @param file    文件
     * @param tempDir 目录
     * @return 数据库记录凭据--这里返回的文件路径（toDiskPath 唯一），用于查询
     */
    String writeFileToDiskAndRecord(MultipartFile file, String tempDir);


    List<SysFile> list();

    List<SysFile> list(SysFileRequest request);


    int saveBatch(List<SysFile> sysFileList);

    SysFile getById(Long id);

    SysFile getByCode(String code);

    /**
     * 查询一条数据
     *
     * @param request 请求参数封装
     * @return db中的一条记录
     */
    SysFile getByRequest(SysFileRequest request);

    SysFile getByIdOrCode(String idOrCode);

    /**
     * 分页查询
     *
     * @param request 请求参数封装
     * @return 分页对象
     */
    Page<SysFile> listPage(SysFileRequest request);

}
