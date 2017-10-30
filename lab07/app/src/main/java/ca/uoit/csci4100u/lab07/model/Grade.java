package ca.uoit.csci4100u.lab07.model;

public class Grade {
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCourseComponent() {
        return courseComponent;
    }

    public void setCourseComponent(String courseComponent) {
        this.courseComponent = courseComponent;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Grade(int studentId, String courseComponent, float mark) {
        setStudentId(studentId);
        setCourseComponent(courseComponent);
        setMark(mark);
    }

    private int studentId;
    private String courseComponent;
    private float mark;

    @Override
    public String toString() {
        return getStudentId() + " " + getCourseComponent() + " " + getMark();
    }
}
