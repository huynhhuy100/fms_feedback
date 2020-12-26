package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Classic;

public interface ClassicService {
    @GET("api/class/getAllClass")
    Call<ArrayList<Classic>> getAllClass();

    @GET("api/class/getClassById/{classID}")
    Call<Classic> getClassById(@Path("classID") int classID);
    
    @GET("api/class/getClassByClassName/{className}")
    Call<Classic> getClassByClassName(@Path("className") String className);

    @POST("api/class/addClass")
    Call<Classic> postClass(@Body Classic classic);

    @PUT("api/class/updateClass/{classID}")
    Call<Void> updateClass(@Path("classID") int classID, @Body Classic classic);

    @DELETE("api/class/deleteClass/{classID}")
    Call<Void> deleteClass(@Path("classID") int classID);
}
