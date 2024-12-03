package org.example.service;

import org.example.pojo.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {
    List<Schedule> search(String teacherName);
}
