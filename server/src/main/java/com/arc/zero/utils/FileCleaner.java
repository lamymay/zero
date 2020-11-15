package com.arc.zero.utils;

import com.arc.core.model.domain.system.SysFile;
import com.arc.zero.mapper.system.SysFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
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
    public void clean(@NotNull Long id) {
        //改进 查到后并发调用两个线程同时删除文件以及库中数据并发记录操作流水
        //
        if (id == null) {
            return;
        }
        SysFile sysFile = sysFileMapper.get(id);
        if (sysFile == null) {
            return;
        }
        //处理
        //需要三个线程


//        Vector
//        ArrayList;
//        HashMap
//        EnumSet
//        Properties

//        Iterable
    }

    public static void main(String[] args) {
        Set<String> set = new HashSet();
        set.add("hello world");
        boolean a = set.add("hello 冰湖一角");
        boolean b = set.add("hello 冰湖一角");
        System.out.println(a+"--"+b);
        System.out.println("集合中元素个数：" + set.size());
        System.out.println("集合中元素为：" + set.toString());
    }
}


