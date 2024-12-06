package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Schedule;
import org.example.service.TeacherService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/search")
    public Result<List<Schedule>> searchByTeacherName(
            String teacherName,
            @RequestHeader(name = "Authorization") String token
    ) {
        if (teacherName == null || teacherName.trim().isEmpty()) {
            return Result.error("教师姓名不能为空");
        }

        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);
            List<Schedule> schedules = teacherService.search(teacherName);
            return Result.success(schedules);
        } catch (Exception e) {
            return Result.error("查询失败：" + e.getMessage());
        }
    }
}
