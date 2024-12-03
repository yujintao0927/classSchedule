package org.example.service.Impl;

import org.example.mapper.TeacherMapper;
import org.example.pojo.Schedule;
import org.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper ;

    @Override
    public List<Schedule> search(String teacherName) {
        return teacherMapper.search(teacherName);
    }
}
