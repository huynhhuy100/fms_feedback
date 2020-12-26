package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Trainee;
import vn.com.r2s.fms.model.TraineeAssignment;


public interface JoinService {
    @GET("/api/traineeAssignment/getTraineeAssignmentById/{getAll}")
    Call<TraineeAssignment> getAll();

    @GET("/api/traineeAssignment/getTraineeAssignmentById/{registrationCode}")
    Call<TraineeAssignment> getregistrationCode(@Path("registrationCode") String registrationCode);

    @GET("/api/getTraineeAssignmentById/{registrationCode}/{traineeId}")
    Call<TraineeAssignment> getTraineeAssignmentById(@Path("registrationCode") String registrationCode, @Path("traineeId") String trainerId);

   @POST("/api/traineeAssignment/addTraineeAssignment")
    Call<TraineeAssignment> postaddTraineeAssignment (@Body TraineeAssignment addTraineeAssignment);

}
