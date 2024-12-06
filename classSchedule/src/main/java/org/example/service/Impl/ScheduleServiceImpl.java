package org.example.service.impl;

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
    private ScheduleMapper scheduleMapper ;

    @Override
    public void addClass(String username, List<String> list) {
        for(int i = 0 ; i < list.size() ; i++) {
            scheduleMapper.addClass(username,list.get(i)) ;
        }
    }

    @Override
    public List<String> findClassId(String username) {
        return scheduleMapper.findClassId(username) ;
    }

    @Override
    public List<Schedule> showClass(List<String> classId) {
        List<Schedule> schedules = new ArrayList<>() ;
        for (int i = 0; i < classId.size(); i++) {
            schedules.add(scheduleMapper.findClassById(classId.get(i)).get(0)) ;
        }
        return schedules ;
    }
}
