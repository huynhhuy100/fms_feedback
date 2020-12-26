package vn.com.r2s.fms.api;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Enrollment;


public interface EnrollmentService {
    @GET("api/enrollment/getAll")
    Call<ArrayList<Enrollment>> getAllEnrollment();

    @GET("api/enrollment/getByClass/{classID}")
    Call<ArrayList<Enrollment>> getClassById(@Path("classID") int classID);

    @GET("api/enrollment/getByTrainee/{id}")
    Call<ArrayList<Enrollment>> getEnrollmentByTraineeID(@Path("id") String TraineeID);

    @POST("api/enrollment/save")
    Call<Enrollment> postEnrollment(@Body Enrollment enrollment);

    @PUT("api/enrollment/updateEnrollment/{traineeId}/{classID}")
    Call<Void> updateEnrollment(@Path("traineeId") String traineeId,@Path("classID") int classID, @Body Enrollment enrollment);

    @DELETE("api/enrollment/delete/{traineeId}/{classID}")
    Call<Void> deleteEnrollment(@Path("classID") int classID,@Path("traineeId") String traineeId);

}
