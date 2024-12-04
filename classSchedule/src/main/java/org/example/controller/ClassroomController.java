package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Schedule;
import org.example.service.ClassroomService;
import org.example.utils.JwtUtils;
import org.example.utils.spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService ;

    @GetMapping("/searchClassroom")
    public Result<Schedule> searchClassroom(@RequestHeader(name = "Authorization") String token, String classroom, int week, String day, int time) {
        try {
            Map<String, Object> claim = JwtUtils.parseToken(token) ;
            List<Schedule> schedules = classroomService.searchClassroom(classroom) ;

            for (int i = 0; i < schedules.size(); i++) {
                String[] info = schedules.get(i).getClassTimeAndLocation().split(" ") ;
                String realDay = info[0] ;

                int timeStart = Integer.parseInt(String.valueOf(info[1].charAt(0))) ;
                int timeEnd ;
                if(info[1].charAt(3) >= '0' && info[1].charAt(3) <= '9') {
                    timeEnd = Integer.parseInt(info[1].substring(2,4)) ;
                } else {
                    timeEnd = Integer.parseInt(String.valueOf(info[1].charAt(2))) ;
                }

                int weekStart = Integer.parseInt(String.valueOf(info[2].charAt(0))) ;
                int weekEnd ;
                if(info[2].charAt(3) >= '0' && info[2].charAt(3) <= '9') {
                    weekEnd = Integer.parseInt(info[2].substring(2,4)) ;
                } else {
                    weekEnd = Integer.parseInt(String.valueOf(info[2].charAt(2))) ;
                }

                if(week < weekStart || week > weekEnd || !day.equals(realDay) || time < timeStart || time > timeEnd) {
                } else {
                    return Result.success(schedules.get(i)) ;
                }
            }
            return Result.success(null) ;
        } catch (Exception e) {
            return Result.error("未登录") ;
        }
    }
}
