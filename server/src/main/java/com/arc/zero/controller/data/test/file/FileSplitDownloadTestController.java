package com.arc.zero.controller.data.test.file;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author yechao
 * @date 2020/10/21 11:01 上午
 */
public class FileSplitDownloadTestController {


    //分片上传：将源文件按长度分为N片，一片一片上传。
    //断点续传：文件在传输过程式中被中断后,在重新传输时,可以从上次的断点处开始传输。
    //HTTP1.1协议（RFC2616）中定义了断点续传相关的HTTP头 Range和Content-Range字段，故实现断点续传就是要能提交Content-Range（返回代码是206）
    //
    //断点续下载
    public static void main(String[] args) throws IOException {
        //断点续传下载文件
        URL url = new URL("http://www.baidu.com/todo.zip");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设置断点续传的范围
        connection.setRequestProperty("RANGE", "bytes=0-20000");
        //获得输入流
        InputStream input = connection.getInputStream();
        RandomAccessFile accessFile = new RandomAccessFile("todo.zip", "rw");
        //从bytes指定的位置开始存文件
        long pos = 2000;
        //定位到指定位置
        accessFile.seek(pos);
        byte[] b = new byte[1024];
        int nRead;
        //从输入流中读入字节流，然后写到文件中
        while ((nRead = input.read(b, 0, 1024)) > 0) {
            accessFile.write(b, 0, nRead);
        }
    }

    /**
     * 断点续传文件下载线程
     */
    public class BreakDownloadThread extends Thread {
        long[] startPos, endPos;
        SplitDownloadThread[] childThreads;
        long fileLength;
        boolean firstDown = true;
        File tempFile;
        DataOutputStream output;   //输出到文件的输出流
        String mUrl;
        String filePath, fileName;
        boolean stopped  =false;
        public BreakDownloadThread(String url, String filePath, String fileName, int splitCount) {
            this.mUrl = url;
            this.filePath = filePath;
            this.fileName = fileName;
            tempFile = new File(filePath + File.separator + fileName + ".dat");
            if (tempFile.exists()) {
                firstDown = false;
                //读取上次下载到的位置
                readPos();
            } else {
                startPos = new long[splitCount];
                endPos = new long[splitCount];
            }
        }
        @Override
        public void run() {
            try {
                if (firstDown) { //第一次下载先获取一下文件的大小
                    fileLength = getFileSize();
                    if (fileLength == -1) {
                        System.err.println("File   Length   is   not   known!");
                    } else if (fileLength == -2) {
                        System.err.println("File   is   not   access!");
                    } else {
                        // 设置各段要下载的文件位置
                        for (int i = 0; i < startPos.length; i++) {
                            startPos[i] = (long) (i * (fileLength / startPos.length));
                        }
                        for (int i = 0; i < endPos.length - 1; i++) {
                            endPos[i] = startPos[i + 1];
                        }
                        endPos[endPos.length - 1] = fileLength;
                    }
                }
                //启动子线程
                childThreads = new SplitDownloadThread[startPos.length];
                for (int i = 0; i < startPos.length; i++) {
                    //int threadID, String url, String name, long startPos, long endPos
                    childThreads[i] = new SplitDownloadThread(i, mUrl, filePath + File.separator + fileName, startPos[i], endPos[i]);
                    childThreads[i].start();
                }
                //等待子线程结束
                //是否结束while循环
                boolean breakWhile = false;
                while (!stopped) {
                    writePos();
                    Thread.sleep(500);
                    breakWhile = true;
                    for (int i = 0; i < startPos.length; i++) {
                        if (!childThreads[i].downOver) {
                            breakWhile = false;
                            break;
                        }
                    }
                    if (breakWhile)  break;
                }
                System.out.println("文件下载结束！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //获得文件长度
        public long getFileSize() {
            int nFileLength = -1;
            try {
                URL url = new URL(mUrl);
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                int responseCode = httpConnection.getResponseCode();
                if (responseCode >= 400) {
                    return -2;
                }
                String sHeader;
                for (int i = 1; ; i++) {
                    sHeader = httpConnection.getHeaderFieldKey(i);
                    if (sHeader != null) {
                        if (sHeader.equals("Content-Length")) {
                            nFileLength = Integer.parseInt(httpConnection.getHeaderField(sHeader));
                            break;
                        }
                    } else
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return nFileLength;
        }
        //保存下载信息（文件指针位置）
        private void writePos() {
            try {
                output = new DataOutputStream(new FileOutputStream(tempFile));
                output.writeInt(startPos.length);
                for (int i = 0; i < startPos.length; i++) {
                    output.writeLong(childThreads[i].startPos);
                    output.writeLong(childThreads[i].endPos);
                }
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //读取保存的下载信息（文件指针位置）
        private void readPos() {
            try {
                DataInputStream input = new DataInputStream(new FileInputStream(tempFile));
                int nCount = input.readInt();
                startPos = new long[nCount];
                endPos = new long[nCount];
                for (int i = 0; i < startPos.length; i++) {
                    startPos[i] = input.readLong();
                    endPos[i] = input.readLong();
                }
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //分片下载的小线程
        public class SplitDownloadThread extends Thread {
            String mUrl;
            long startPos, endPos;
            int threadID;
            boolean stopped = false, downOver = false;
            RandomAccessFile accessFile;
            public SplitDownloadThread(int threadID, String url, String name, long startPos, long endPos) throws IOException {
                this.threadID = threadID;
                this.mUrl = url;
                this.startPos = startPos;
                this.endPos = endPos;
                accessFile = new RandomAccessFile(name, "rw");
                accessFile.seek(startPos);
            }
            @Override
            public void run() {
                while (startPos < endPos && !stopped) {
                    try {
                        URL url = new URL(mUrl);
                        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                        String sProperty = "bytes=" + startPos + "-";
                        httpConnection.setRequestProperty("RANGE", sProperty);
                        InputStream input = httpConnection.getInputStream();
                        byte[] b = new byte[1024];
                        int nRead;
                        //下载到endPos结束该线程
                        while ((nRead = input.read(b, 0, 1024)) > 0 && startPos < endPos && !stopped) {
                            startPos += write(b, 0, nRead);
                        }
                        downOver = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            public synchronized int write(byte[] b, int nStart, int nLen) {
                int n = -1;
                try {
                    accessFile.write(b, nStart, nLen);
                    n = nLen;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return n;
            }
        }
    }

}


