package com.example.login6.controller;

import com.example.login6.domain.User;
import com.example.login6.domain.UserLoginRequest;
import com.example.login6.domain.UserRegisterRequest;
import com.example.login6.mapper.UserFileRepository;
import com.example.login6.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("user")
@RestController
public class Usercontroller {

    @Autowired
    private UserFileRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("register")
    public String register(@RequestBody UserRegisterRequest request) {
        String id = request.getId();
        String password = request.getPassword();

        log.info("id:{}", id);
        log.info("password:{}", password);

        User existingUser = userRepository.selectUser(id);
        if (existingUser != null) {
            return "注册失败，用户已存在";
        }

        userRepository.saveUser(new User(id, password));
        return "注册成功";
    }

    @PostMapping("login")
    public String login(@RequestBody UserLoginRequest request) {
        String id = request.getId();
        String password = request.getPassword();

        log.info("id:{}", id);
        log.info("password:{}", password);

        User user = userRepository.selectUser(id);
        if (user == null || !user.getPassword().equals(password)) {
            return "登录失败，用户名或密码错误";
        }

        String token = jwtUtil.generateToken(id);
        return "登录成功，令牌: " + token;
    }
}
