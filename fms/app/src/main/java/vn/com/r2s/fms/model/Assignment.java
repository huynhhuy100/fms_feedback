package vn.com.r2s.fms.model;

public class Assignment {
    private String registrationCode;
    private int classId;
    private int moduleId;
    private String trainerId;

    public Assignment() {

    }

    public Assignment(int classId, int moduleId, String trainerId) {
        this.classId = classId;
        this.moduleId = moduleId;
        this.trainerId = trainerId;
    }

    public Assignment(int classId, int moduleId, String trainerId, String registrationCode) {
        this.registrationCode = registrationCode;
        this.classId = classId;
        this.moduleId = moduleId;
        this.trainerId = trainerId;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }
}
