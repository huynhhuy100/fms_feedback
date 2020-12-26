package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Trainee;

public interface TraineeService {
    @GET("api/trainee/getAllTrainee")
    Call<ArrayList<Trainee>> getAll();

    @GET("api/trainee/getTraineeById/{Trainee}")
    Call<Trainee> getTraineeById(@Path("Trainee") String userName);
}
