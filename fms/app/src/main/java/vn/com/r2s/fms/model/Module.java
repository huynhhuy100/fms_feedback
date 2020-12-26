package vn.com.r2s.fms.model;

import java.util.Date;

public class Module {
    private int moduleId;
    private String idAdmin;
    private String moduleName;
    private String startTime;
    private String endTime;
    private boolean isDeleted;
    private String feedbackStartTime;
    private String feedbackEndTime;
    private int idFeedback;

    public Module() {

    }

    public Module(int moduleId, String idAdmin, String moduleName, String startTime, String endTime, boolean isDeleted, String feedbackStartTime, String feedbackEndTime, int idFeedback) {
        this.moduleId = moduleId;
        this.idAdmin = idAdmin;
        this.moduleName = moduleName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDeleted = isDeleted;
        this.feedbackStartTime = feedbackStartTime;
        this.feedbackEndTime = feedbackEndTime;
        this.idFeedback = idFeedback;
    }

    public Module(String idAdmin, String moduleName, String startTime, String endTime, String feedbackStartTime, String feedbackEndTime, int idFeedback) {
        this.idAdmin = idAdmin;
        this.moduleName = moduleName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.feedbackStartTime = feedbackStartTime;
        this.feedbackEndTime = feedbackEndTime;
        this.idFeedback = idFeedback;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getFeedbackStartTime() {
        return feedbackStartTime;
    }

    public void setFeedbackStartTime(String feedbackStartTime) {
        this.feedbackStartTime = feedbackStartTime;
    }

    public String getFeedbackEndTime() {
        return feedbackEndTime;
    }

    public void setFeedbackEndTime(String feedbackEndTime) {
        this.feedbackEndTime = feedbackEndTime;
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }
}
