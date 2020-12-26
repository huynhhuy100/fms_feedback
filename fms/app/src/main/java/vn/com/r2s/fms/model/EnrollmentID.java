package vn.com.r2s.fms.model;

public class EnrollmentID {
    Integer classId;
    String traineeId;

    public EnrollmentID() {
    }

    public EnrollmentID(Integer classId, String traineeId) {
        this.classId = classId;
        this.traineeId = traineeId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }
}
