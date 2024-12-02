package org.example.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    void addClass(String username, List<String> list) ;
}
