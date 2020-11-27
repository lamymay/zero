package com.arc.zero.utils;

import com.arc.core.model.domain.system.SysFile;
import com.arc.zero.mapper.system.SysFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件清理
 *
 * @author may
 * @since 2019/12/10 13:02
 */
@Service
@Slf4j
@Async
public class FileCleaner {

    @Resource
    private SysFileMapper sysFileMapper;

    @Async
    public void clean(SysFile sysFile) {
        if (sysFile == null) {
            return;
        }

        //sysFile
        String localPath = sysFile.getPath();
        if (localPath != null) {
            File file = new File(localPath);
            boolean delete = file.delete();
            log.info("文件name={},id={},code={},路径={}删除{}", sysFile.getName(), sysFile.getCode(), sysFile.getPath(), (delete ? "成功" : "失败"));
        }

    }

    public static void main(String[] args) {
        //改进 查到后并发调用两个线程同时删除文件以及库中数据并发记录操作流水
        //处理
        //        Vector
        //        ArrayList;
        //        HashMap
        //        EnumSet
        //        Properties
        //        Iterable
        Set<String> set = new HashSet();
        set.add("hello world");
        boolean a = set.add("hello 冰湖一角");
        boolean b = set.add("hello 冰湖一角");
        System.out.println(a + "--" + b);
        System.out.println("集合中元素个数：" + set.size());
        System.out.println("集合中元素为：" + set.toString());

                System.out.println(96/24);
    }

    private Date updateAwardTime(Date awardTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String tempDateString = format.format(awardTime);
            return format.parse(tempDateString);
        } catch (Exception exception) {
            log.error("夺宝后台,保存奖品异常{}", exception);
            throw new RuntimeException("时间转换异常");
        }
    }
}


