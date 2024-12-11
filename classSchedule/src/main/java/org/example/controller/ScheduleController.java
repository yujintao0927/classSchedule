package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Schedule;
import org.example.service.ScheduleService;
import org.example.utils.JwtUtils;
import org.example.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.example.utils.spider;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
@CrossOrigin
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService ;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @GetMapping("/addClass")
    public Result addClass(@RequestHeader(name = "Authorization") String token) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);
            String username = (String) claim.get("username");
            System.out.println("正在为用户 " + username + " 导入课程");

            List<String[]> list = spider.getInfo();
            System.out.println("爬虫获取到的课程ID及时间地点列表: " + list);
            
            // 先清除该用户之前的选课记录
            scheduleMapper.deleteUserCourses(username);
            
            scheduleService.addClass(username, list);
            System.out.println("课程导入完成");

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("导入失败：" + e.getMessage());
        }
    }

    @GetMapping("/classShow")
    public Result<List<Schedule>> showClass(@RequestHeader(name = "Authorization") String token) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);
            String username = (String) claim.get("username");
            System.out.println("Showing classes for user: " + username);

            List<String> classIds = scheduleService.findClassId(username);
            List<String> classTimeAndLocation = scheduleService.findClassTimeAndLocation(username);
            List<String> teacherName = scheduleService.findClassTeacherName(username) ;

            System.out.println("Found class IDs: " + classIds);
            System.out.println("Found class TimeAndLocation: " + classTimeAndLocation);

            List<Schedule> schedules = scheduleService.showClass_textDisplay(classIds,classTimeAndLocation,teacherName);
            System.out.println("Retrieved schedules: " + schedules);

            return Result.success(schedules);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("未登录");
        }
    }
}
