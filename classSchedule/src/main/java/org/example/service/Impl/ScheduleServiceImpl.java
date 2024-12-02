package org.example.service.Impl;

import org.example.mapper.ScheduleMapper;
import org.example.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
