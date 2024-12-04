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
}
