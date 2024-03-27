package STUDENTMANAGEMNT;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private String courseCode;
    private List<Student> registeredStudents;

    public Course(String courseName, String courseCode) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.registeredStudents = new ArrayList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void registerStudent(Student student) {
        registeredStudents.add(student);
    }

    public void unregisterStudent(Student student) {
        registeredStudents.remove(student);
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void assignGrades(String[] grades) {
    }

    public boolean isRegistered() {
        return false;
    }
}