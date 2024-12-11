package org.example.service.Impl;

import org.example.mapper.ClassroomMapper;
import org.example.pojo.Schedule;
import org.example.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomMapper classroomMapper ;

    @Override
    public List<Schedule> searchClassroom(String classroom) {
        List<Schedule> result = new ArrayList<>() ;
        List<Schedule> schedules = classroomMapper.searchClassroom(classroom) ;
        for(Schedule schedule : schedules) {
            result.addAll(parseTimeAndLocation(schedule));
        }
        return result ;
    }


    private List<Schedule> parseTimeAndLocation(Schedule schedule) {
        List<Schedule> schedules = new ArrayList<>();
        String timeAndLocation = schedule.getClassTimeAndLocation();
        if (timeAndLocation == null) {
            schedules.add(schedule);
            return schedules;
        }

        try {
            // 处理多个时间段的情况
            String[] timeLocations = timeAndLocation.split(",");
            for (String timeLocation : timeLocations) {
                String[] parts = timeLocation.trim().split(" ");

                Schedule newSchedule = cloneSchedule(schedule);

                // 设置星期
                newSchedule.setDay(parts[0]); // 如 "周一"

                // 设置节次
                String[] times = parts[1].replace("节", "").split("-");
                newSchedule.setTimeStart(Integer.parseInt(times[0])); // 取第一节课的时间
                newSchedule.setTimeEnd(Integer.parseInt(times[1]));

                // 设置周次
                String weekPart = parts[2].replace("周", "");
                boolean isSingle = timeLocation.contains("单");
                boolean isDouble = timeLocation.contains("双");

                if (weekPart.contains("-")) {
                    String[] weeks = weekPart.split("-");
                    int startWeek = Integer.parseInt(weeks[0]);
                    String endWeekStr = weeks[1];
                    if (endWeekStr.contains("(")) {
                        endWeekStr = endWeekStr.substring(0, endWeekStr.indexOf("("));
                    }
                    int endWeek = Integer.parseInt(endWeekStr);

                    // 根据单双周生成对应的周次
                    for (int i = startWeek; i <= endWeek; i++) {
                        if (isSingle && i % 2 == 0) continue; // 单周跳过双周
                        if (isDouble && i % 2 == 1) continue; // 双周跳过单周
                        Schedule weekSchedule = cloneSchedule(newSchedule);
                        weekSchedule.setWeek(i);
                        // 设置教室
                        if (parts.length > 3) {
                            weekSchedule.setClassroom(parts[3]);
                        }
                        schedules.add(weekSchedule);
                    }
                } else if (weekPart.contains(",")) {
                    // 处理形如 "3,7,11,15周" 的情况
                    String[] weeks = weekPart.split(",");
                    for (String week : weeks) {
                        Schedule weekSchedule = cloneSchedule(newSchedule);
                        weekSchedule.setWeek(Integer.parseInt(week));
                        // 设置教室
                        if (parts.length > 3) {
                            weekSchedule.setClassroom(parts[3]);
                        }
                        schedules.add(weekSchedule);
                    }
                } else {
                    newSchedule.setWeek(Integer.parseInt(weekPart));
                    // 设置教室
                    if (parts.length > 3) {
                        newSchedule.setClassroom(parts[3]);
                    }
                    schedules.add(newSchedule);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("解析时间地点信息失败: " + timeAndLocation);
            schedules.add(schedule); // 如果解析失败，添加原始数据
        }

        return schedules;
    }

    private Schedule cloneSchedule(Schedule original) {
        Schedule clone = new Schedule();
        clone.setClassId(original.getClassId());
        clone.setClassName(original.getClassName());
        clone.setClassPoint(original.getClassPoint());
        clone.setClassTeacher(original.getClassTeacher());
        clone.setClassTimeAndLocation(original.getClassTimeAndLocation());
        clone.setDay(original.getDay());
        clone.setTimeStart(original.getTimeStart());
        clone.setTimeEnd(original.getTimeEnd());
        clone.setClassroom(original.getClassroom());
        return clone;
    }
}
