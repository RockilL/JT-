package com.example.filecontrol.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {

    // 文件上传
    public void uploadFile(MultipartFile file, String path) throws IOException {
        File destination = new File(path + "/" + file.getOriginalFilename());
        if (!destination.exists()) {
            destination.mkdirs();
        }
        file.transferTo(destination);
    }

    // 文件删除
    public boolean deleteFile(String path) {
        File file = new File(path);
        return file.exists() && file.delete();
    }

    // 创建文件夹
    public boolean createFolder(String path) {
        File folder = new File(path);
        return folder.exists() || folder.mkdirs();//&&：逻辑 AND 操作符。它会首先检查 file.exists() 是否为 true，如果为 true，则继续执行 file.delete()；否则，直接返回 false
    }

    // 根据文件名搜索
    public List<String> searchFilesByName(String name, String path) {
        List<String> result = new ArrayList<>();
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().contains(name)) {
                        result.add(file.getAbsolutePath());
                    }
                }
            }
        }
        return result;
    }

    // 根据日期搜索文件

}
