package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private String classId ;
    private String className ;
    private String classPoint ;
    private String classTeacher;
    private String classTimeAndLocation ;
    private Integer week;
    private String day;
    private Integer timeStart;
    private Integer timeEnd ;
    private String location ;
    private String note;


    public String getTeacherName() {
        return classTeacher;
    }

    public void setTeacherName(String teacherName) {
        this.classTeacher = teacherName;
    }

    public String getClassroom() {
        return classTimeAndLocation;
    }

    public void setClassroom(String classroom) {
        this.classTimeAndLocation = classroom;
    }

}
