package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Admin;
import vn.com.r2s.fms.model.Trainee;
import vn.com.r2s.fms.model.Trainer;

public interface LoginService {

    @GET("/api/admin/getAdminById/{userName}")
    Call<Admin> getAdminByUserName(@Path("userName") String userName);

    @GET("/api/trainer/getTrainerById/{userName}")
    Call<Trainer> getTrainerByUserName(@Path("userName") String userName);

    @GET("/api/trainee/getTraineeById/{userName}")
    Call<Trainee> getTraieeByUserName(@Path("userName") String userName);

    @GET("/api/trainer/getAllTrainer")
    Call<ArrayList<Trainer>> getAllTrainer();

}
