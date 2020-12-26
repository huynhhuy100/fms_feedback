package vn.com.r2s.fms.model;

public class Feedback {
    private int feedbackID;
    private int typeFeedbackID;
    private String adminID;
    private Boolean IsDeleted;
    private String title;
    public Feedback(){

    }


    public Feedback(int FeedbackId, int TypeFeedbackId, String adminId, Boolean isDeleted, String titLe) {
        feedbackID = FeedbackId;
        typeFeedbackID = TypeFeedbackId;
        adminID = adminId;
        IsDeleted = isDeleted;
        title = titLe;
    }

    public Feedback(int TypeFeedbackId, String adminId, Boolean isDeleted, String titLe) {
        typeFeedbackID = TypeFeedbackId;
        adminID = adminId;
        IsDeleted = isDeleted;
        title = titLe;
    }

    public int getFeedbackId() {
        return feedbackID;
    }

    public void setFeedbackId(int feedbackId) {
        feedbackID = feedbackId;
    }

    public int getTypeFeedbackId() {
        return typeFeedbackID;
    }

    public void setTypeFeedbackId(int typeFeedbackId) {
        typeFeedbackID = typeFeedbackId;
    }

    public String getAdminId() {
        return adminID;
    }

    public void setAdminId(String adminId) {
        adminID = adminId;
    }

    public Boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titLe) {
        title = titLe;
    }
}
