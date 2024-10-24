package com.example.login6.service;

import com.example.login6.domain.User;
import com.example.login6.domain.UserLoginRequest;
import com.example.login6.domain.UserRegisterRequest;
import com.example.login6.mapper.UserFileRepository;
import com.example.login6.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserFileRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(UserRegisterRequest request) {
        String id = request.getId();
        String password = request.getPassword();

        // 检查用户是否已经存在
        User existingUser = userRepository.selectUser(id);
        if (existingUser != null) {
            return "注册失败，用户已存在";
        }

        // 加密密码后存储用户
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.saveUser(new User(id, encodedPassword));
        return "注册成功";
    }


    public String login(UserLoginRequest request) {
        String id = request.getId();
        String password = request.getPassword();

        // 查找用户
        User user = userRepository.selectUser(id);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return "登录失败，用户名或密码错误";
        }

        // 生成并返回JWT令牌
        return jwtUtil.generateToken(id);
    }
}
