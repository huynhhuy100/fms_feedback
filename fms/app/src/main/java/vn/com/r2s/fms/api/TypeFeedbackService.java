package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.TypeFeedback;

public interface TypeFeedbackService {
    @GET("api/typeFeedback/getAllTypeFeedback")
    Call<ArrayList<TypeFeedback>> getTFB();

    @GET("api/typeFeedback/getTypeFeedbackById/{id}")
    Call<TypeFeedback> getTypeFeedbackById(@Path("id") long id);

    @POST("api/typeFeedback/addTypeFeedback")
    Call<TypeFeedback> postTypeFeedback(@Body TypeFeedback typeFeedback);

    @PUT("api/typeFeedback/updatetypeFeedback/{id}")
    Call<Void> updateTypeFeedback(@Path("id") long id, @Body TypeFeedback typeFeedback);

    @DELETE("api/typeFeedback/deletetypeFeedback/{id}")
    Call<Void> deletetfb(@Path("id") long id);
}
