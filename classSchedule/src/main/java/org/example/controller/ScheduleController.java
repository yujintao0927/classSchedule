package org.example.controller;

import org.example.pojo.Result;
import org.example.service.ScheduleService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.utils.spider;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService ;
//
//    @GetMapping("/inf")
//    public Result<String> list(@RequestHeader(name = "Authorization") String token) {
//        Map<String, Object> claim = null;
//        try {
//            claim = JwtUtils.parseToken(token);
//            return Result.success("所有数据") ;
//        } catch (Exception e) {
//            return Result.error("未登录") ;
//        }
//    }

    @GetMapping("/addClass")
    public Result addClass(@RequestHeader(name = "Authorization") String token) {

        try {
            Map<String, Object> claim = JwtUtils.parseToken(token) ;
            String username = (String) claim.get("username");

            List<String> list = spider.getClassInf() ;
            scheduleService.addClass(username,list) ;

            return Result.success() ;
        } catch (Exception e) {
            return Result.error("未登录") ;
        }
    }
}
