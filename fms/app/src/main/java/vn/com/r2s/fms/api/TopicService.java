package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;

public interface TopicService {
    @GET("api/topic/getAllTopics")
    Call<ArrayList<Topic>> getTopics();

    @GET("api/topic/gettopics/{topicId}")
    Call<Topic> getTopicsById(@Path("topicID") int id);

    @POST("api/topic/addtopics")
    Call<Question> postTopic(@Body Topic topic);
}
