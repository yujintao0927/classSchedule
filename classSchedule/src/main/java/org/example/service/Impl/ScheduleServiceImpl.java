package org.example.service.Impl;

import org.example.mapper.ScheduleMapper;
import org.example.pojo.Schedule;
import org.example.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public void addClass(String username, List<String[]> list) {
        for(int i = 0 ; i < list.size() ; i++) {
            String[] courseInfo = list.get(i);
            // 直接使用爬虫获取的数据
            String classId = courseInfo[0];
            String timeLocation = courseInfo[1];
            String teacherName = courseInfo[2];
            String className = courseInfo[3];  // 获取课程名称
            
            // 创建新的Schedule对象
            Schedule schedule = new Schedule();
            schedule.setClassId(classId);
            schedule.setClassTimeAndLocation(timeLocation);
            schedule.setTeacherName(teacherName);
            
            // 添加到数据库
            scheduleMapper.addClass(username, 
                schedule.getClassId(),
                schedule.getClassTimeAndLocation(),
                schedule.getTeacherName(),
                "0",  // 学分默认为"0"
                className  // 使用爬取到的课程名称
            );
        }
    }

    @Override
    public List<Schedule> showClass_graphDisplay(String username) {
        List<Schedule> result = new ArrayList<>();

        List<Schedule> schedules = scheduleMapper.findClassFromMap(username) ;

        for (Schedule schedule : schedules) {
            result.addAll(parseTimeAndLocation(schedule));
        }

        return result;
    }

    @Override
    public List<Schedule> showClass_textDisplay(String userName) {
        return scheduleMapper.findClassFromMap(userName);
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
            String[] timeSlots = timeAndLocation.split(",");
            for (String timeSlot : timeSlots) {
                // 分割字符串，例如 "周五 9-10节 3周,7周,11周,15周 逸A-117"
                String[] parts = timeSlot.trim().split(" ");
                if (parts.length < 3) {
                    continue;
                }

                Schedule newSchedule = cloneSchedule(schedule);
                // 设置星期
                newSchedule.setDay(parts[0]);

                // 处理节数
                String[] times = parts[1].replace("节", "").split("-");
                if (times.length >= 2) {
                    newSchedule.setTimeStart(Integer.parseInt(times[0]));
                    newSchedule.setTimeEnd(Integer.parseInt(times[1]));
                } else if (times.length == 1) {
                    newSchedule.setTimeStart(Integer.parseInt(times[0]));
                    newSchedule.setTimeEnd(Integer.parseInt(times[0]));
                }

                // 设置地点（最后一个部分）
                newSchedule.setLocation(parts[parts.length - 1]);

                // 处理周数
                for (int i = 2; i < parts.length - 1; i++) {
                    if (parts[i].contains("周")) {
                        String weekPart = parts[i];
                        boolean isSingle = weekPart.contains("单");
                        boolean isDouble = weekPart.contains("双");
                        
                        if (weekPart.contains("-")) {
                            // 处理连续周，如 "1-17周"
                            String[] weekRange = weekPart.replace("周", "").replace("(单)", "").replace("(双)", "").split("-");
                            int startWeek = Integer.parseInt(weekRange[0]);
                            int endWeek = Integer.parseInt(weekRange[1]);
                            
                            for (int week = startWeek; week <= endWeek; week++) {
                                if ((isSingle && week % 2 == 1) || (isDouble && week % 2 == 0) || (!isSingle && !isDouble)) {
                                    Schedule weekSchedule = cloneSchedule(newSchedule);
                                    weekSchedule.setWeek(week);
                                    schedules.add(weekSchedule);
                                }
                            }
                        } else {
                            // 处理离散周，如 "3周,7周,11周,15周"
                            String[] weeks = weekPart.split(",");
                            for (String week : weeks) {
                                week = week.replace("周", "").replace("(单)", "").replace("(双)", "");
                                try {
                                    Schedule weekSchedule = cloneSchedule(newSchedule);
                                    weekSchedule.setWeek(Integer.parseInt(week));
                                    schedules.add(weekSchedule);
                                } catch (NumberFormatException e) {
                                    // 忽略无法解析的周数
                                }
                            }
                        }
                    }
                }

                if (schedules.isEmpty()) {
                    schedules.add(schedule);
                }
            }
        } catch (Exception e) {
            System.out.println("解析时间地点信息失败: " + timeAndLocation);
            schedules.add(schedule);
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
