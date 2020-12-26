package vn.com.r2s.fms.model;

public class Answer {
    Integer classId;
    Integer moduleId;
    Integer questionID;
    Integer value;
    String traineeId;

    public Answer(Integer classId, Integer moduleId, Integer questionID, Integer value, String traineeId) {
        this.classId = classId;
        this.moduleId = moduleId;
        this.questionID = questionID;
        this.value = value;
        this.traineeId = traineeId;
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

    public Integer getQuestionId() {
        return questionID;
    }

    public void setQuestionId(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }
}
