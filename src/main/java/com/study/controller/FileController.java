package com.study.controller;

import com.study.utils.UUIDGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {

        }
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String path = sdf.format(new Date()) + "/" + UUIDGenerator.uuidLowerCase32() + suffix;
        File f = new File("/home/file/" + path);
        // 设置权限
        f.setWritable(true, false);
        f.mkdirs();
        file.transferTo(f);
        return "http://119.3.7.25:8080/file/" + path;
    }

    @GetMapping("/{time}/{url}")
    public HttpServletResponse download(@PathVariable String time, @PathVariable String url, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File("/home/file/" + time + "/" + url);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream; charset=UTF-8");
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
