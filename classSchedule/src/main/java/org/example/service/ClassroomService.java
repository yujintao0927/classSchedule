package org.example.service;

import org.example.pojo.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClassroomService {
     List<Schedule> searchClassroom(String classroom);
}
