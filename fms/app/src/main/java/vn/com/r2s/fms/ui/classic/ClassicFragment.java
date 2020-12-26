package vn.com.r2s.fms.ui.classic;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.ClassicActivity;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.MainActivity2;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.ClassAdaperTrainee;
import vn.com.r2s.fms.adapter.ClassAdapter;
import vn.com.r2s.fms.adapter.ClassAdapterTrainer;
import vn.com.r2s.fms.adapter.EnrollmentAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Enrollment;
import vn.com.r2s.fms.model.Topic;

import vn.com.r2s.fms.ui.enrollment.EnrollmentViewModel;

public class ClassicFragment extends Fragment {
    RecyclerView rec_class;
    Button btnAddClass;
    ClassViewModel classViewModel;
    ClassAdapter classAdapter;

    private ArrayList<Enrollment> enrollmentArrayList;
    private ArrayList<Assignment> assignmentArrayList;
    EnrollmentViewModel enrollmentViewModel;
    ClassAdaperTrainee classAdaperTrainee;
    ClassAdapterTrainer classAdapterTrainer;
    private ArrayList<Classic> classList;
    EnrollmentService enrollmentService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_classic, container, false);

        init(root);

//        if(MainActivity.typeUser.equals(Language.trainee)){
//            btnAddClass.setVisibility(View.GONE);
//            dataTrainee();
//            retrieveTasksTrainee();
//        }else
        if (MainActivity.typeUser.equals(Language.admin) && MainActivity.typeUser != null) {
            event();
            getData();
            retrieveTasks();
        } else if (MainActivity.typeUser.equals(Language.trainer) && MainActivity.typeUser != null) {
            btnAddClass.setVisibility(View.GONE);
            dataTrainer();
            retrieveTasksTrainer();
//        }
//        } else if (MainActivity2.typeUser.equals(Language.trainee)){
        } else {
            btnAddClass.setVisibility(View.GONE);
            dataTrainee();
            retrieveTasksTrainee();
        }
        return root;
    }

    // trainer
    public void dataTrainer() {
        getDataTrainer();
    }

    public void getDataTrainer() {
        rec_class.setHasFixedSize(true);
        rec_class.setLayoutManager(new LinearLayoutManager(getContext()));

        assignmentArrayList = new ArrayList<>();
        classAdapterTrainer = new ClassAdapterTrainer(getContext(), assignmentArrayList);
        rec_class.setAdapter(classAdapterTrainer);
    }

    private void retrieveTasksTrainer() {


        AssignmentService assignmentService = APIUtility.getAssignmentService();
        assignmentService.getAssignmentNyTrainerId(MainActivity.username).enqueue(new Callback<ArrayList<Assignment>>() {
            @Override
            public void onResponse(Call<ArrayList<Assignment>> call, Response<ArrayList<Assignment>> response) {
                Log.e("aaaaaaaaaa", response.body().size() + "");
                assignmentArrayList = new ArrayList<>();
                assignmentArrayList = response.body();
                getAssignmentByAssignment(assignmentArrayList);
            }

            @Override
            public void onFailure(Call<ArrayList<Assignment>> call, Throwable t) {

            }
        });
    }

    public void getAssignmentByAssignment(ArrayList<Assignment> assignmentArrayList1) {

        rec_class.setHasFixedSize(true);
        rec_class.setLayoutManager(new LinearLayoutManager(getContext()));

        classList = new ArrayList<>();

        classAdapterTrainer = new ClassAdapterTrainer(getContext(), assignmentArrayList1);

        rec_class.setAdapter(classAdapterTrainer);

    }

    // trainee
    public void dataTrainee() {
        getDataTrainee();
    }

    public void getDataTrainee() {
        rec_class.setHasFixedSize(true);
        rec_class.setLayoutManager(new LinearLayoutManager(getContext()));

        enrollmentArrayList = new ArrayList<>();
        classAdaperTrainee = new ClassAdaperTrainee(getContext(), enrollmentArrayList);
        rec_class.setAdapter(classAdapter);
    }

    private void retrieveTasksTrainee() {

        EnrollmentService enrollmentService = APIUtility.getEnService();
        enrollmentService.getEnrollmentByTraineeID(MainActivity2.username).enqueue(new Callback<ArrayList<Enrollment>>() {
            @Override
            public void onResponse(Call<ArrayList<Enrollment>> call, Response<ArrayList<Enrollment>> response) {
                Log.e("aaaaaaaaaa", response.body().size() + "");

                enrollmentArrayList = new ArrayList<>();
                enrollmentArrayList = response.body();
                getClassByClassId(enrollmentArrayList);

            }

            @Override
            public void onFailure(Call<ArrayList<Enrollment>> call, Throwable t) {
                Log.e("fail", t.getMessage() + "");
            }
        });
    }

    public void getClassByClassId(ArrayList<Enrollment> enrollmentArrayList) {

        rec_class.setHasFixedSize(true);
        rec_class.setLayoutManager(new LinearLayoutManager(getContext()));

        classList = new ArrayList<>();

        classAdaperTrainee = new ClassAdaperTrainee(getContext(), enrollmentArrayList);

        rec_class.setAdapter(classAdaperTrainee);

    }

    // admin
    public void getData() {

        rec_class.setHasFixedSize(true);
        rec_class.setLayoutManager(new LinearLayoutManager(getContext()));

        classList = new ArrayList<>();

        classAdapter = new ClassAdapter(getContext(), classList);

        rec_class.setAdapter(classAdapter);

    }

    public void event() {
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClassicActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(View root) {
        btnAddClass = root.findViewById(R.id.btnAddClass);
        rec_class = root.findViewById(R.id.rec_class);
    }

    public void retrieveTasks() {
        classViewModel.getClasslist().observe(getViewLifecycleOwner(), classics -> {
            classList.clear();
            classList.addAll(classics);
            classAdapter.notifyDataSetChanged();
        });
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (MainActivity2.typeUser.equals(Language.admin)) {
//            retrieveTasksTrainee();
//        } else
        if (MainActivity.typeUser.equals(Language.admin)) {
            retrieveTasks();
        }

    }

}