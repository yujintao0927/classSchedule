package org.example.service;

import org.example.pojo.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    void addClass(String username, List<String[]> list) ;

    List<Schedule> showClass_graphDisplay(String username);

    List<Schedule> showClass_textDisplay(String username) ;

}
