package vn.com.r2s.fms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.ClassAdaperTraineeDetails;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Enrollment;

public class ClassicTrainerActivity extends AppCompatActivity {
    TextView tvClassID,
            tvClassName;

    RecyclerView rcv_class;

    Button btnBack;

    int classID;

    private ArrayList<Enrollment> enrollmentArrayList;
    ClassAdaperTraineeDetails classAdaperTraineeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_trainer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        getDataByAdapter();
    }
    public void getDataByAdapter() {
        Intent intent = getIntent();
        classID =intent.getExtras().getInt("classID");


        tvClassID.setText(classID+"");
        ClassicService classicService = APIUtility.getClassicService();
        classicService.getClassById(classID).enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                tvClassName.setText(response.body().getClassName());
            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {

            }
        });

        retrieveTasksTrainer(classID);
    }
    private void retrieveTasksTrainer(int classID) {
        EnrollmentService enrollmentService = APIUtility.getEnService();
        enrollmentService.getClassById(classID).enqueue(new Callback<ArrayList<Enrollment>>() {
            @Override
            public void onResponse(Call<ArrayList<Enrollment>> call, Response<ArrayList<Enrollment>> response) {
                enrollmentArrayList = new ArrayList<>();
                enrollmentArrayList = response.body();
                setAdapterForClass(enrollmentArrayList);
                Log.e("tag", enrollmentArrayList.size()+"" );
            }

            @Override
            public void onFailure(Call<ArrayList<Enrollment>> call, Throwable t) {

            }
        });
    }
    public void setAdapterForClass(ArrayList<Enrollment> enrollmentArrayList1){
        rcv_class.setHasFixedSize(true);
        LinearLayoutManager manager1 = new LinearLayoutManager(ClassicTrainerActivity.this, LinearLayoutManager.VERTICAL, false);

        rcv_class.setLayoutManager(manager1);

        classAdaperTraineeDetails = new ClassAdaperTraineeDetails(ClassicTrainerActivity.this, enrollmentArrayList1);
        rcv_class.setAdapter(classAdaperTraineeDetails);
    }
    public void init() {
        tvClassID = findViewById(R.id.tvClassId);
        tvClassName = findViewById(R.id.tvClassName);
        btnBack = findViewById(R.id.btnBack);
        rcv_class = findViewById(R.id.rcv_class);
    }

    public void back() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}