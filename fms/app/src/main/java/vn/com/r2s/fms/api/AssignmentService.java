package vn.com.r2s.fms.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Classic;

public interface AssignmentService {
    @GET("/api/assignment/getAll")
    Call<ArrayList<Assignment>> getAllAssignment();

    @GET("/api/assignment/getAssignmentByTrainerId/{trainerId}")
    Call<ArrayList<Assignment>> getAssignmentNyTrainerId(@Path("trainerId") String trainerId);

    @GET("/api/assignment/getAssignmentByClassId/{classId}")
    Call<ArrayList<Assignment>> getAssignmentByClassId(@Path("classId") int classId);

    @GET("/api/assignment/getAssignmentByModuleId/{moduleId}")
    Call<ArrayList<Assignment>> getAssignmentByModuleId(@Path("moduleId") int moduleId);

    @GET("/api/assignment/getAssignmentByRegistrationCode/{registrationCode}")
    Call<Assignment> getAssignmentByRegistrationCode(@Path("registrationCode") String registrationCode);

    @GET("/api/assignment/getAssignmentById/{classId}/{moduleId}/{trainerId}")
    Call<Assignment> getAssignmentById(@Path("classId") int classId
            , @Path("moduleId") int moduleId, @Path("trainerId") String trainerId);

    @POST("api/assignment/addAssignment")
    Call<Assignment> postAssignment(@Body Assignment assignment);

    @PUT("api/assignment/updateAssignment/{classId}/{moduleId}/{trainerId}")
    Call<Assignment> updateAssignment(@Path("classId") int classId
            , @Path("moduleId") int moduleId, @Path("trainerId") String trainerId, @Body Assignment assignment);

    @DELETE("api/assignment/deleteAssignment/{classId}/{moduleId}/{trainerId}")
    Call<Void> deleteAssignment(@Path("classId") int classId
            , @Path("moduleId") int moduleId, @Path("trainerId") String trainerId);

}
