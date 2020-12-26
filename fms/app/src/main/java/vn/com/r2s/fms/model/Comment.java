package vn.com.r2s.fms.model;

public class Comment {
    Integer classId;
    Integer moduleId;
    String traineeId;
    String comment;

    public Comment(Integer classId, String traineeId, Integer moduleId, String comment) {
        this.classId = classId;
        this.moduleId = moduleId;
        this.traineeId = traineeId;
        this.comment = comment;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
