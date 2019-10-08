package com.study.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    // 通过请求进行下载
    public void downloadZip(HttpServletResponse response) throws IOException {
        // 设置下载文件名字, 防止出现乱码
        String fileName = new String(URLEncoder.encode("name.zip", "utf-8"));
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentType("application/octet-stream; charset=UTF-8");
        // 定义一个压缩文件输出流
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
        List<String> paths = new ArrayList<>();
        // --打包
        zipNetworkResource(paths, zipOutputStream);
        // 类似一种保存的操作, 将内存的文件写到本地文件中
        zipOutputStream.flush();
        zipOutputStream.close();
    }

    // 读取网络资源并压缩
    public static void zipNetworkResource(List<String> paths, ZipOutputStream zipOutputStream) throws IOException {
        int i = 1;
        for (String path : paths) {
            // 通过 URL 获取网络资源
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            // 获取输入流
            InputStream inputStream = conn.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 512);
            // 为了防止重名的情况
            zipOutputStream.putNextEntry(new ZipEntry(i + ""));
            i++;
            int nNumber;
            byte[] buffer = new byte[512];
            while ((nNumber = bufferedInputStream.read(buffer)) != -1) {
                // 写入到压缩输入流中
                zipOutputStream.write(buffer, 0, nNumber);
            }
            if (inputStream != null) {
                // 关闭
                bufferedInputStream.close();
                inputStream.close();
            }
        }
    }

    // 读取本地资源并压缩
    public static void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException {
        for (File file : files) {
            // 判断文件是否存在
            if (file.exists()) {
                // 定义一个文件输入流
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 512);
                // 文件可能来自不同的文件夹, 会有重名的情况, 这个地方最好能判断一下
                ZipEntry zipEntry = new ZipEntry(file.getName());
                outputStream.putNextEntry(zipEntry);
                // 向压缩文件中输出数据
                int nNumber;
                byte[] buffer = new byte[512];
                while ((nNumber = bufferedInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, nNumber);
                }
                fileInputStream.close();
                bufferedInputStream.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<File> files = new ArrayList<>();
        files.add(new File("D:\\test\\1.txt"));
        files.add(new File("D:\\test\\2.txt"));
        files.add(new File("D:\\test\\3.txt"));
        File file = new File("D:\\test\\zip.zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream( new FileOutputStream(file));
        zipFile(files, zipOutputStream);
        zipOutputStream.flush();
        zipOutputStream.close();
        System.err.println("下载成功");
    }
}
