package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.User;

public interface UserService {
    @GET("api/user/getAllUsers")
    Call<ArrayList<User>> getUsers();

    @GET("api/user/getUsersById/{id}")
    Call<User> getUserById(@Path("id") long id);

    @POST("api/user/addUsers")
    Call<User> postUser(@Body User user);

    @PUT("api/user/updateUsers/{id}")
    Call<Void> updateUser(@Path("id") long id, @Body User user);

    @DELETE("api/user/deleteUsers/{id}")
    Call<Void> delete(@Path("id") long id);
}
