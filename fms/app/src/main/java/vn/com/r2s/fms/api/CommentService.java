package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Comment;

public interface CommentService {
    @GET("api/traineeComment/getAll")
    Call<ArrayList<Comment>> getAllComment();

    @GET("api/traineeComment/getTraineeCommentByTraineeId/{traineeId}")
    Call<ArrayList<Comment>> getcommenttraineeId(@Path("traineeId") String traineeId);

    @POST("api/traineeComment/addTraineeComment")
    Call<Comment> postComment(@Body Comment comment);
}
