package vn.com.r2s.fms.ui.enrollment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.EnrollmentAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Enrollment;


public class EnrollmentFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    RecyclerView rec_enrollment;
    Button btnAddEnrollment;
    Spinner spnClassName;
    ClassicService classicService;
    EnrollmentAdapter enrollmentAdapter;
    private ArrayList<Enrollment> enrollmentArrayList;
    private EnrollmentViewModel enrollmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        enrollmentViewModel = new ViewModelProvider(this).get(EnrollmentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_enrollment, container, false);

        btnAddEnrollment = root.findViewById(R.id.btnAddEnrollment);
        rec_enrollment = root.findViewById(R.id.rec_enrollments);
        spnClassName=root.findViewById(R.id.spnClassName);

        rec_enrollment.setHasFixedSize(true);
        rec_enrollment.setLayoutManager(new LinearLayoutManager(getContext()));
        enrollmentArrayList = new ArrayList<>();
        enrollmentAdapter = new EnrollmentAdapter(getContext(), enrollmentArrayList);
        rec_enrollment.setAdapter(enrollmentAdapter);

        classicService = APIUtility.getClassicService();
        getSpinnerClass();
        return root;
    }

    private void retrieveTasks(){
        enrollmentViewModel.getEnrollmentlist().observe(getViewLifecycleOwner(), enrollments -> {
            enrollmentArrayList.clear();
            enrollmentArrayList.addAll(enrollments);
            idASCTapped();
            enrollmentAdapter.notifyDataSetChanged();
        });
    }

    public void getSpinnerClass() {
        Call<ArrayList<Classic>> calls = classicService.getAllClass();
        calls.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> call, Response<ArrayList<Classic>> response) {
                ArrayList<Classic> classics = response.body();
                ArrayList<String> className = new ArrayList<>();
                className.clear();
                className.add("All");
                populateUIClass(classics);
                for (int i = 0; i < classics.size(); i++) {

                    className.add(classics.get(i).getClassName());

                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, className);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClassName.setAdapter(adapter);
                    spnClassName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getContext(), "Bạn đã chọn: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                            String className=adapterView.getItemAtPosition(i).toString();
                            findClassByClassName(className);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Classic>> call, Throwable t) {

            }
        });
    }

    private void populateUIClass(ArrayList<Classic> classics) {
        if (classics == null) {
            return;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        retrieveTasks();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void findClassByClassName(String className) {
        if(className=="All"){
            retrieveTasks();

        }else {
            Call<Classic> calls = classicService.getClassByClassName(className);
            calls.enqueue(new Callback<Classic>() {
                @Override
                public void onResponse(Call<Classic> call, Response<Classic> response) {
                    int classID=response.body().getClassID();
                    enrollmentViewModel.loadEnrollmentByClassID(classID);
                    enrollmentArrayList.clear();
                    enrollmentAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Classic> call, Throwable t) {
                    Toast.makeText(getContext(), "EnrollmentViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    public void idASCTapped()
    {
        Collections.sort(enrollmentArrayList, Enrollment.idAscending);
    }
}

