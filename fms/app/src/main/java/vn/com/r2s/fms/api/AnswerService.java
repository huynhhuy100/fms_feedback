package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Answer;
import vn.com.r2s.fms.model.Feedback;

public interface AnswerService {
    @GET("api/answer/getAll")
    Call<ArrayList<Answer>> getAllAnswers();

    @GET("api/answer/getAnswerByTraineeId/{traineeId}")
    Call<Answer> getAnswerByTraineeId(@Path("id") long id);

    @POST("api/answer/addAnswer")
    Call<Answer> postAnswer(@Body Answer answer);

}
