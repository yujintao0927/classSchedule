package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Schedule;
import org.example.service.ClassroomService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @GetMapping("/searchClassroom")
    public Result<List<Schedule>> searchClassroom(
            @RequestHeader(name = "Authorization") String token,
            String classroom,
            Integer week
    ) {
        if (classroom == null || classroom.trim().isEmpty()) {
            return Result.error("教室地点不能为空");
        }
        
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token);
            List<Schedule> schedules = classroomService.searchClassroom(classroom);

            List<Schedule> result = new ArrayList<>() ;
            for(Schedule schedule : schedules) {
                if (Objects.equals(schedule.getWeek(), week)) {
                    result.add(schedule) ;
                }
            }
//
//            if (week != null && schedules != null) {
//                schedules = schedules.stream()
//                    .filter(schedule -> {
//                        String timeLocation = schedule.getClassTimeAndLocation();
//                        if (timeLocation == null) return false;
//
//                        String[] parts = timeLocation.split(" ");
//                        if (parts.length < 3) return false;
//
//                        try {
//                            String weekInfo = parts[2];
//                            int weekStart = Integer.parseInt(String.valueOf(weekInfo.charAt(0)));
//                            int weekEnd;
//                            if (weekInfo.charAt(3) >= '0' && weekInfo.charAt(3) <= '9') {
//                                weekEnd = Integer.parseInt(weekInfo.substring(2, 4));
//                            } else {
//                                weekEnd = Integer.parseInt(String.valueOf(weekInfo.charAt(2)));
//                            }
//                            return week >= weekStart && week <= weekEnd;
//                        } catch (Exception e) {
//                            return false;
//                        }
//                    })
//                    .toList();
//            }
            
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("查询失败：" + e.getMessage());
        }
    }
}
