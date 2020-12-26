package vn.com.r2s.fms.ui.traineefeedback;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.api.FeedbackService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Enrollment;
import vn.com.r2s.fms.model.Feedback;
import vn.com.r2s.fms.model.Module;

public class TraineeFeedbackViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Feedback>> Feedbacklist;
    private MutableLiveData<ArrayList<Classic>> classlist;
    private MutableLiveData<ArrayList<Enrollment>> enrollmentlist;
    private MutableLiveData<ArrayList<Module>> modulelist;
    private MutableLiveData<ArrayList<Assignment>> assignmentlist;
    private AssignmentService assignmentService;
    private ModuleService moduleService;
    private EnrollmentService enrollmentService;
    private ClassicService classicService;
    private FeedbackService feedbackService;


    public TraineeFeedbackViewModel(Application application) {
        super(application);
        feedbackService = APIUtility.getFeedbackService();
        classicService = APIUtility.getClassicService();
        enrollmentService = APIUtility.getEnService();
        moduleService = APIUtility.getModuleService();
        assignmentService = APIUtility.getAssignmentService();
    }
    public MutableLiveData<ArrayList<Feedback>> getFeedbacklist() {
        if(Feedbacklist == null){
            Feedbacklist = new MutableLiveData<>();

        }
        loadFeedback();
        return Feedbacklist;
    }
    public MutableLiveData<ArrayList<Classic>> getClasslist() {
        if (classlist == null) {
            classlist = new MutableLiveData<ArrayList<Classic>>();

        }
        loadClass();
        return classlist;
    }
    public MutableLiveData<ArrayList<Module>> getModulelist() {
        if (modulelist == null) {
            modulelist = new MutableLiveData<ArrayList<Module>>();

        }
        loadModule();
        return modulelist;
    }
    private void loadModule() {
        Call<ArrayList<Module>> callsss = moduleService.getAllModule();
        callsss.enqueue(new Callback<ArrayList<Module>>() {
            @Override
            public void onResponse(Call<ArrayList<Module>> call, Response<ArrayList<Module>> response) {
                modulelist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Module>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void loadClass() {
        Call<ArrayList<Classic>> calls = classicService.getAllClass();
        calls.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> call, Response<ArrayList<Classic>> response) {
                classlist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Classic>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void loadFeedback() {
        Call<ArrayList<Feedback>> call = feedbackService.getFB();
        call.enqueue(new Callback<ArrayList<Feedback>>() {
            @Override
            public void onResponse(Call<ArrayList<Feedback>> call, Response<ArrayList<Feedback>> response) {
                Feedbacklist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Feedback>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "FeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void deleteFeedback(long key){
        Call<Void> call = feedbackService.deletefb(key);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadFeedback();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "FeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}