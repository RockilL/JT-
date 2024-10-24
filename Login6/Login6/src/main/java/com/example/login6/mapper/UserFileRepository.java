package com.example.login6.mapper;

import com.example.login6.domain.User;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserFileRepository {

    private final String filePath = "D:\\JT.TXT"; // 使用绝对路径保存用户数据文件
    private Map<String, User> users = new HashMap<>();

    public UserFileRepository() {
        loadUsers();
    }

    // 加载用户数据
    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");  // 假设每行格式为: id,password
                if (userData.length == 2) {
                    String id = userData[0].trim();
                    String password = userData[1].trim();
                    users.put(id, new User(id, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 保存用户数据到文件
    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users.values()) {
                writer.write(user.getId() + "," + user.getPassword());
                writer.newLine();  // 换行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 根据ID查找用户
    public User selectUser(String id) {
        return users.get(id);
    }

    // 保存新用户
    public void saveUser(User user) {
        users.put(user.getId(), user);
        saveUsers();
    }
}
