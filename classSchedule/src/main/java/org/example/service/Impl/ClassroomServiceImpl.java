package org.example.service.Impl;

import org.example.mapper.ClassroomMapper;
import org.example.pojo.Schedule;
import org.example.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomMapper classroomMapper ;

    @Override
    public List<Schedule> searchClassroom(String classroom) {

        return classroomMapper.searchClassroom(classroom) ;
    }
}
