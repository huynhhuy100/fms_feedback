package vn.com.r2s.fms.ui.join;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.ClassAdaperTrainee;
import vn.com.r2s.fms.adapter.ClassAdapterTrainer;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Classic;

public class JoinFragment extends Fragment {
    private ArrayList<Assignment> assignmentArrayList;
    RecyclerView rec_class;
    private ArrayList<Classic> classList;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.activity_dialog, container, false);

        AssignmentService assignmentService = APIUtility.getAssignmentService();
        assignmentService.getAssignmentByClassId(1).enqueue(new Callback<ArrayList<Assignment>>() {
            @Override
            public void onResponse(Call<ArrayList<Assignment>> call, Response<ArrayList<Assignment>> response) {
                Log.e("aaaaaaaaaa", response.body().size() + "");
                assignmentArrayList = new ArrayList<>();
                assignmentArrayList = response.body();
                Log.d("code",response.body().get(0).getRegistrationCode());

            }

            @Override
            public void onFailure(Call<ArrayList<Assignment>> call, Throwable t) {

            }
        });


        return root;
    }


}