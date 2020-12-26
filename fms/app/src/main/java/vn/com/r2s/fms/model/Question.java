package vn.com.r2s.fms.model;

public class Question {
    private int questionID ;
    private int topicId ;
    private String questionContent ;
    private String topicName  ;
    private Boolean isdeleted ;


    public Question(int questionID, int topicId, String questionContent, String topicName, Boolean isdeleted) {
        this.questionID = questionID;
        this.topicId = topicId;
        this.questionContent = questionContent;
        this.topicName = topicName;
        this.isdeleted = isdeleted;
    }

    public Question() {
    }

    public Question(String questionContent, int topicId) {
        this.questionContent = questionContent;
        this.topicId = topicId;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getTopicID() {
        return topicId;
    }

    public void setTopicID(int topicID) {
        this.topicId = topicID;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}

