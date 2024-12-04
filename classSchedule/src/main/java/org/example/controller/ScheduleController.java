package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Schedule;
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

    @GetMapping("/addClass")
    public Result addClass(@RequestHeader(name = "Authorization") String token) {

        try {
            Map<String, Object> claim = JwtUtils.parseToken(token) ;
            String username = (String) claim.get("username");

            List<String> list = spider.getInfo() ;
            scheduleService.addClass(username,list) ;

            return Result.success() ;
        } catch (Exception e) {
            return Result.error("未登录") ;
        }
    }

    @GetMapping("/classShow")
    public Result<List<Schedule>> showClass(@RequestHeader(name = "Authorization") String token) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token) ;
            String username = (String) claim.get("username");

            List<String> classIds = scheduleService.findClassId(username) ;

            List<Schedule> schedules = scheduleService.showClass(classIds) ;

            return Result.success(schedules) ;
        } catch (Exception e) {
            return Result.error("未登录") ;
        }
    }
}
