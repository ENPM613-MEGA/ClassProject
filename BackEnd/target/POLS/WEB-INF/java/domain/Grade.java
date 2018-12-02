package domain;

import java.io.Serializable;

public class Grade implements Serializable {
    private int userId;
    private int assignmentId;
    private int grade;

    public int getUserId() {
        return userId;
    }
    public int getAssignmentId() {
        return assignmentId;
    }
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }
}
