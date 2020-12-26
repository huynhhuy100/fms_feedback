package vn.com.r2s.fms.api;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Feedback;

public interface FeedbackService {
    @GET("api/feedback/getallfeedbacks")
    Call<ArrayList<Feedback>> getFB();

    @GET("api/feedback/getFeedbacksById/{id}")
    Call<Feedback> getFeedbackById(@Path("id") long id);

    @POST("api/feedback/addFeedbacks")
    Call<Feedback> postFeedback(@Body Feedback feedback);

    @PUT("api/feedback/updateFeedbacks/{id}")
    Call<Void> updateFeedback(@Path("id") long id, @Body Feedback feedback);

    @DELETE("api/feedback/deleteFeedback/{id}")
    Call<Void> deletefb(@Path("id") long id);
}
