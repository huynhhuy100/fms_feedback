package vn.com.r2s.fms.ui.assignment;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.model.Assignment;

public class AssignmentViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Assignment>> asmList;
    private MutableLiveData<ArrayList<Assignment>> asmListByClassId;
    private AssignmentService asmService;

    public AssignmentViewModel(@NonNull Application application) {
        super(application);
        asmService = APIUtility.getAssignmentService();

    }

    public MutableLiveData<ArrayList<Assignment>> getAsmList() {
        if(asmList == null) {
            asmList = new MutableLiveData<ArrayList<Assignment>>();
        }
        loadAsm();
        return asmList;
    }

    private void loadAsm() {
        Call<ArrayList<Assignment>> call = asmService.getAllAssignment();
        call.enqueue(new Callback<ArrayList<Assignment>>() {
            @Override
            public void onResponse(Call<ArrayList<Assignment>> call, Response<ArrayList<Assignment>> response) {
                asmList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Assignment>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "AssignmentViewModel " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
