package vn.com.r2s.fms.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.TraineeService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Trainee;

public class DetailEnrollmentActivity extends AppCompatActivity {
    TextView tvClassID, tvClassName, tvCapacity, tvStartTime, tvEndTime, tvTraineeId, tvTraineeName, tvPhone, tvAdress, tvEmail;
    Button btnBack;

    int classID,Capacity;
    String className,startTime,endTime,traineeId,traineeName,Phone,Address,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollment_detail);
        btnBack=findViewById(R.id.btnBack);
        tvClassID=findViewById(R.id.tvClassId);
        tvClassName=findViewById(R.id.tvClassName);
        tvCapacity=findViewById(R.id.tvCapacity);
        tvStartTime=findViewById(R.id.tvStartTime);
        tvEndTime=findViewById(R.id.tvEndTime);

        tvTraineeId=findViewById(R.id.tvTraineeId);
        tvTraineeName=findViewById(R.id.tvTraineeName);
        tvPhone=findViewById(R.id.tvPhone);
        tvAdress=findViewById(R.id.tvAddress);
        tvEmail=findViewById(R.id.tvEmail);

        Intent intent = getIntent();

        classID =intent.getExtras().getInt("classId");
        traineeId = intent.getExtras().getString("traineeId");
        tvClassID.setText(classID+"");
        tvTraineeId.setText(traineeId);
        retrieveTasksClass(classID);

        retrieveTasksTrainee(traineeId);

        back();

    }
    private void retrieveTasksClass(int classID) {
        ClassicService classicService = APIUtility.getClassicService();
        classicService.getClassById(classID).enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                className=response.body().getClassName();
                Capacity=response.body().getCapadity();
                startTime=response.body().getStartTime();
                endTime=response.body().getEndTime();

                tvClassName.setText(className);
                tvCapacity.setText(Capacity+"");
                tvStartTime.setText(startTime);
                tvEndTime.setText(endTime);


            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {

            }
        });
    }

    private void retrieveTasksTrainee(String traineeId) {
        TraineeService classicService = APIUtility.getTraineeService();
        classicService.getTraineeById(traineeId).enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                traineeName=response.body().getName();
                Phone=response.body().getPhone();
                Address=response.body().getAddress();
                Email=response.body().getEmail();

                tvTraineeName.setText(traineeName);
                tvPhone.setText(Phone);
                tvAdress.setText(Address);
                tvEmail.setText(Email);

            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {

            }
        });
    }
    public void back() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}