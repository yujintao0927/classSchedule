package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping("/register")
    public Result register(String username, String password) {
        User u = userService.findByUserName(username) ;

        if(u == null) {
            userService.register(username,password) ;
        } else {
            return Result.error("用户名已存在") ;
        }
        return Result.success(u) ;
    }

    @PostMapping("/login")
    public Result<String> login(String username, String password) {
        User u = userService.findByUserName(username) ;
        if(u == null) {
            return Result.error("该用户名不存在，请重新注册");
        } else {
            if(u.getPassword().equals(password)) {
                Map<String, Object> claim = new HashMap<>() ;
                claim.put("username", username) ;
                String s = JwtUtils.genToken(claim) ;

                return Result.success(s) ;
            } else {
                return Result.error("密码错误") ;
            }
        }
    }

}
