package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Module;

public interface ModuleService {
    @GET("api/module/getAllModule")
    Call<ArrayList<Module>> getAllModule();

    @GET("api/module/getModuleById/{moduleId}")
    Call<Module> getModuleById(@Path("moduleId") int moduleID);

    @GET("api/module/getModuleByName/{moduleName}")
    Call<Module> getModuleByName(@Path("moduleName") String moduleName);

    @POST("api/module/addModule")
    Call<Module> postModule(@Body Module module);

    @PUT("api/module/updateModule/{moduleId}")
    Call<Void> updateModule(@Path("moduleId") int idModule, @Body Module module);

    @DELETE("api/module/deleteModue/{moduleId}")
    Call<Void> deleteModule(@Path("moduleId") int moduleID);

}
