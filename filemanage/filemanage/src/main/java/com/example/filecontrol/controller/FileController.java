package com.example.filecontrol.controller;

import com.example.filecontrol.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    // 上传文件
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) {
        try {
            fileService.uploadFile(file, path);
            return ResponseEntity.ok("文件上传成功");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("文件上传失败: " + e.getMessage());
        }
    }

    // 删除文件
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("path") String path) {
        if (fileService.deleteFile(path)) {
            return ResponseEntity.ok("文件删除成功");
        } else {
            return ResponseEntity.status(404).body("文件不存在");
        }
    }

    // 创建文件夹
    @PostMapping("/create-folder")
    public ResponseEntity<String> createFolder(@RequestParam("path") String path) {
        if (fileService.createFolder(path)) {
            return ResponseEntity.ok("文件夹创建成功");
        } else {
            return ResponseEntity.status(400).body("文件夹创建失败");
        }
    }

    // 根据文件名搜索
    @GetMapping("/search")
    public ResponseEntity<List<String>> searchFilesByName(@RequestParam("name") String name, @RequestParam("path") String path) {
        List<String> result = fileService.searchFilesByName(name, path);
        return ResponseEntity.ok(result);
    }

}
