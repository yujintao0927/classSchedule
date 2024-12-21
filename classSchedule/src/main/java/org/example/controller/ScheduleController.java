package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Schedule;
import org.example.service.ScheduleService;
import org.example.utils.JwtUtils;
import org.example.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/importProcess")
    public Result<String> importProcess(@RequestHeader(name = "Authorization") String token) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);
            String username = (String) claim.get("username");
            System.out.println("importProcess: 开始为用户 " + username + " 导入课程");

            spider.initialization();
            String qrCodePath = spider.getQRCode();
            System.out.println("importProcess: 获取到二维码路径: " + qrCodePath);
            if (qrCodePath == null) {
                System.out.println("importProcess: 二维码路径为空");
                return Result.error("获取二维码失败");
            }
            return Result.success(qrCodePath);

        } catch (Exception e) {
            System.out.println("importProcess: 发生异常 - " + e.getMessage());
            e.printStackTrace();
            return Result.error("获取二维码失败：" + e.getMessage());
        }
    }

    @GetMapping("/addClass")
    public Result addClass(@RequestHeader(name = "Authorization") String token) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);
            String username = (String) claim.get("username");
            System.out.println("addClass: 正在为用户 " + username + " 导入课程");

            System.out.println("addClass: 开始获取课程信息");
            List<String[]> list = spider.getInfo();
            System.out.println("addClass: 爬虫获取到的课程ID及时间地点列表: " + list);

            if(list.isEmpty()) {
                System.out.println("addClass: 获取到的课程列表为空");
                return Result.error("未导入成功") ;
            }
            
            System.out.println("addClass: 清除用户之前的选课记录");
            scheduleMapper.deleteUserCourses(username);

            System.out.println("addClass: 开始添加新课程");
            scheduleService.addClass(username, list);
            System.out.println("addClass: 课程导入完成");

            return Result.success();
        } catch (Exception e) {
            System.out.println("addClass: 发生异常 - " + e.getMessage());
            e.printStackTrace();
            return Result.error("导入失败：" + e.getMessage());
        }
    }

    @GetMapping("/checkLoginStatus")
    public Result<Boolean> checkLoginStatus() {
        try {
            boolean isLoggedIn = spider.checkLoginStatus();
            return Result.success(isLoggedIn);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("检查登录状态失败：" + e.getMessage());
        }
    }

    @GetMapping("/classShow")
    public Result<List<Schedule>> showClass(@RequestHeader(name = "Authorization") String token) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);
            String username = (String) claim.get("username");
            System.out.println("Showing classes for user: " + username);

            List<Schedule> schedules = scheduleService.showClass_textDisplay(username);
            System.out.println("Retrieved schedules: " + schedules);

            return Result.success(schedules);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("加载课程失败：" + e.getMessage());
        }
    }

    @GetMapping("/QRCode")
    public Result<String> getQRCode(@RequestHeader(name = "Authorization") String token) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);

            spider.initialization();
            return Result.success(spider.getQRCode());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("加载课程失败：" + e.getMessage());
        }
    }


}
