package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;


public interface QuestionsService {
    //======================================= Questions ==================================//
    @GET("api/Question/getAllQuestion")
    Call<ArrayList<Question>> getQuestions();

    @GET("api/Question/getQuestionById/{questionID}")
    Call<Question> getQuestionById(@Path("questionID") int id);

    @POST("api/Question/Addquestion")
    Call<Question> postQuestion(@Body Question question);

    @PUT("api/Question/updatequestion/{questionID}")
    Call<Void> updateQuestion(@Path("questionID") int id, @Body Question question);

    @DELETE("api/Question/deletequestion/{questionID}")
    Call<Void> deleteQuestion(@Path("questionID") int id);

    //======================================= Topic ==================================//
    @GET("api/topic/getAllTopics")
    Call<ArrayList<Topic>> getTopics();

    @GET("api/topic/gettopicID/{topicId}")
    Call<Topic> getTopicsById(@Path("topicId") int id);
}
