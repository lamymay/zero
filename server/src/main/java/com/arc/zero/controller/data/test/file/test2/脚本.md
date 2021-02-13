


chmod a+x kill_by_port

//http://127.0.0.1:80001
//http://y8hapn.natappfree.cc
//kill_by_port

又:
    这是找到的脚本， 和你发我的一样， 应该也要赋权限就能执行了

又:
    chmod a+x kill_by_port


#!/bin/bash
read -p "输入 你要kill的端口号: " a

for i in `lsof -i:$a | awk '{print $2}' | grep -v 'PID'`;do kill -9 $i;done



1、查询端口
lsof -i :端口
2、杀掉端口
kill -9 进程ID


脚本
---

*.lastupdated

@echo off
rem create by NettQun
  
rem 这里写你的仓库路径
set REPOSITORY_PATH=D:\Java\maven-repository\maven-aliyun\repository
rem 正在搜索...
for /f "delims=" %%i in ('dir /b /s "%REPOSITORY_PATH%\*lastUpdated*"') do (
    echo %%i
    del /s /q "%%i"
)
rem 搜索完毕
pause
    2、cleanLastUpdated.sh（linux版本）

# create b




# create by NettQun
 
# 这里写你的仓库路径
REPOSITORY_PATH=~/Documents/tools/repository
echo 正在搜索...
find $REPOSITORY_PATH -name "*lastUpdated*" | xargs rm -fr
echo 搜索完


