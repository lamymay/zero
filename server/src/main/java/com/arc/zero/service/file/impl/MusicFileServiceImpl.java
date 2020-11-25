package com.arc.zero.service.file.impl;

import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.domain.system.SysFile;
import com.arc.utils.file.FileUtil;
import com.arc.zero.service.file.MusicFileService;
import com.arc.zero.service.system.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 叶超
 * @since 2020/3/4 13:31
 */
@Slf4j
@Service
public class MusicFileServiceImpl implements MusicFileService {

    @Autowired
    private SysFileService sysFileService;

    @Override
    public Integer init(Map<String, Object> map) {
        String path = (String) map.get("path");
        log.info("扫描文件的基础路径path={}", path);
        List<File> fileList = FileUtil.listFileByDir(path);

        if (fileList == null || fileList.size() == 0) {
            return 0;
        }

        //insert data to db  & crete  domainList
        List<SysFile> sysFileList = new ArrayList<>();
        for (File file : fileList) {
            SysFile sysFile = new SysFile();
            sysFile.setCreateTime(new Date());
            sysFile.setUpdateTime(new Date());
            sysFile.setName(file.getName());
            sysFile.setCode(file.getPath());
            sysFile.setPath(file.getPath());

            //由  SysFileService 维护
            sysFile.setUri(file.getPath());
            sysFile.setLength(file.length());
            sysFileList.add(sysFile);
        }
        return sysFileService.saveBatch(sysFileList);
    }


    @Override
    public Boolean deleteByCode(String code) {
        if (code == null) {
            throw new BizException(ProjectCodeEnum.ILLEGAL_PARAMETER);
        }
        SysFile sysFile = sysFileService.getByCode(code);
        if (sysFile == null) {
            return false;
        }
        // 删除物理磁盘上的文件
        try {
            String path = sysFile.getPath();
            log.debug("文件路径是={}", path);
            File tempFile = new File(path);
            if (tempFile.isFile()) {
                boolean delete = tempFile.delete();
                log.info("删除物理磁盘上的文件,删除结果={}", delete);
                if (!delete) {
                    throw new BizException(ProjectCodeEnum.FILE_DELETE_ERROR);
                }
            } else if (tempFile.isDirectory()) {
                log.warn("文件路径是={}，操作停止并返回失败，原因：文件夹删除不允许", tempFile.getPath());
                return false;
            } else {
                log.warn("文件路径是={}，操作停止并返回失败，原因：文件不存在 else else", tempFile.getPath());
            }
        } catch (BizException e) {
            throw e;
        } catch (Exception e) {
            throw new BizException(ProjectCodeEnum.FILE_OPERATE_ERROR, e);
        }
        //删除记录
        return sysFileService.deleteById(sysFile.getId());
    }
}
