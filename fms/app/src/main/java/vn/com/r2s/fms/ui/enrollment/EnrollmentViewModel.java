package vn.com.r2s.fms.ui.enrollment;

import android.app.Application;
import android.widget.Toast;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.model.Enrollment;


public class EnrollmentViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Enrollment>> enrollmentlist;
    private EnrollmentService enrollmentService;

    public EnrollmentViewModel( Application application) {
        super(application);
        enrollmentService = APIUtility.getEnService();
    }

    public MutableLiveData<ArrayList<Enrollment>> getEnrollmentlist() {
        if(enrollmentlist == null){
            enrollmentlist = new MutableLiveData<ArrayList<Enrollment>>();

        }
        loadEnrollment();
        return enrollmentlist;
    }


    private void loadEnrollment() {
        Call<ArrayList<Enrollment>> call = enrollmentService.getAllEnrollment();

        call.enqueue(new Callback<ArrayList<Enrollment>>() {
            @Override
            public void onResponse(Call<ArrayList<Enrollment>> call, Response<ArrayList<Enrollment>> response) {
                enrollmentlist.setValue(response.body());

                for (int i =0; i <response.body().size(); i++){
//                    Log.e("0001-> datatest", response.body().get(i).getEnrollmentID().getClassId()+"");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Enrollment>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "EnrollmentViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void loadEnrollmentByClassID(int key) {
        Call<ArrayList<Enrollment>> calls = enrollmentService.getClassById(key);
        calls.enqueue(new Callback<ArrayList<Enrollment>>() {
            @Override
            public void onResponse(Call<ArrayList<Enrollment>> call, Response<ArrayList<Enrollment>> response) {
                enrollmentlist.setValue(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<Enrollment>> call, Throwable t) {
                Toast.makeText(getApplication(), "EnrollmentViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
