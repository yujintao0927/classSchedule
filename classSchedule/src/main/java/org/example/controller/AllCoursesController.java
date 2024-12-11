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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
public class AllCoursesController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/allCourses")
    public Result<List<Schedule>> getAllCourses(@RequestHeader(name = "Authorization") String token) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);
            String username = (String) claim.get("username");
            
            // 获取用户所有的课程ID
            List<String> classIds = scheduleService.findClassId(username);
            List<String> classTimeAndLocation = scheduleService.findClassTimeAndLocation(username);
            
            // 获取所有课程的详细信息
            List<Schedule> schedules = scheduleService.showClass_graphDisplay(classIds,classTimeAndLocation);
            
            return Result.success(schedules);
        } catch (Exception e) {
            return Result.error("获取课程信息失败");
        }
    }
} 