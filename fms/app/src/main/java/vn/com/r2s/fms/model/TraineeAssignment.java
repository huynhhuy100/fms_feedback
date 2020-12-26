package vn.com.r2s.fms.model;

public class TraineeAssignment {
    private String registrationCode;
    private String traineeId;

    public TraineeAssignment() {

    }

    public TraineeAssignment(String registrationCode, String traineeId) {
        this.registrationCode = registrationCode;
        this.traineeId = traineeId;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }
}
