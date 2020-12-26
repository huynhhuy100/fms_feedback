package vn.com.r2s.fms.model;

public class Topic {
    private int topicId;
    private String topicName;

    public Topic(int topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
    }

   /*    public Topic(String topic_name) {
        this.topicName = topic_name;
    }*/

    public Topic() {
    }

    public int getTopicID() {
        return topicId;
    }

    public void setTopicID(int topicId) {
        topicId = topicId;
    }

    public String gettopicName() {
        return topicName;
    }

    public void setTopic_name(String topicName) {
        this.topicName = topicName;
    }
}

/*    public Topic(int topicID, String topic_name) {
        TopicID = topicID;
        topic_name = topic_name;
    }
    public Topic(String topic_name) {
        topic_name = topic_name;
    }
    public Topic() {}

    public int getTopicID() {
        return TopicID;
    }

    public void setTopicID(int topicID) {
        TopicID = topicID;
    }

    public String getNameTopic() {
        return topic_name;
    }

    public void setNameTopic(String topic_name) {
        topic_name = nameTopic;
    }
}*/
