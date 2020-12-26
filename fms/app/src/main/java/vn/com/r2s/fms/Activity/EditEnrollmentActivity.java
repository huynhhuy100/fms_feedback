package vn.com.r2s.fms.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.api.TraineeService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Enrollment;
import vn.com.r2s.fms.model.EnrollmentID;
import vn.com.r2s.fms.model.Trainee;

public class EditEnrollmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText edtTraineeName,edtTraineeId;
    private Button btnSave, btnCancel, btnSuccess, btnOk;
    private Integer classID, classID1, tempID;
    private String traineeID,className, classNameSpn,traineeName;
    TextView tvNotification1,tvFailed,tvEnrol;
    private Dialog dialog;
    private Intent intent;
    private EnrollmentService enrollmentService;
    private ClassicService classicService;
    private TraineeService traineeService;
    private Spinner spnClassName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_enrollment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        enrollmentService = APIUtility.getEnService();
        classicService = APIUtility.getClassicService();
        traineeService = APIUtility.getTraineeService();

        intent = getIntent();
        edtTraineeId.setText(intent.getExtras().getString("traineeId"));
        traineeID=intent.getExtras().getString("traineeId");
        classID = intent.getExtras().getInt("classId");

        classicService = APIUtility.getClassicService();
        Call<Classic> calls = classicService.getClassById(classID);
        calls.enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                className=response.body().getClassName();

            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {
                Toast.makeText(EditEnrollmentActivity.this, "EditEnrollmentActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        Call<Trainee> callss = traineeService.getTraineeById(traineeID);
        callss.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                 traineeName=response.body().getName();
                edtTraineeName.setText(traineeName);
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(EditEnrollmentActivity.this, "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        getSpinnerClass();

    }
    private void init(){

        edtTraineeId = findViewById(R.id.edtTraineeId);
        edtTraineeName= findViewById(R.id.edtTraineeName);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnBack);
        spnClassName= findViewById(R.id.spnClassName);

        btnSave.setOnClickListener(view -> {
            try {
                onSaveButtonClick();
            } catch (Exception e) {
                Log.e("EnrollmentActivity", "Error" + e.getMessage());
            }
        });
        btnCancel.setOnClickListener(view -> onBackPressed());

    }

    public void onSaveButtonClick() {

        if (classID==classID1){

            showDialogSuccess();

        }else {
            loadEnrollmentByTraineeID();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getSpinnerClass() {
        Call<ArrayList<Classic>> calls = classicService.getAllClass();
        calls.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> call, Response<ArrayList<Classic>> response) {
                ArrayList<Classic> classics = response.body();
                ArrayList<String> classNamelList = new ArrayList<>();
                classNamelList.clear();

                for (int i = 0; i < classics.size(); i++) {
                    classNamelList.add(classics.get(i).getClassName());

                    ArrayAdapter adapter = new ArrayAdapter(getApplication(), android.R.layout.simple_spinner_item, classNamelList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnClassName.setAdapter(adapter);
                    spnClassName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplication(), "Bạn đã chọn: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                            classNameSpn=adapterView.getItemAtPosition(i).toString();
                            findClassByClassName(classNameSpn, className, classID);

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

    private void findClassByClassName(String className2, String className, int classID4) {

        if(className2==className){

            classID1 =   classID4;

        }else {
            Call<Classic> calls = classicService.getClassByClassName(className2);
            calls.enqueue(new Callback<Classic>() {
                @Override
                public void onResponse(Call<Classic> call, Response<Classic> response) {

                    classID1 = response.body().getClassID();

                }

                @Override
                public void onFailure(Call<Classic> call, Throwable t) {
                    Toast.makeText(EditEnrollmentActivity.this, "EnrollmentViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void showDialogSuccess() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success1);
        dialog.show();
        btnSuccess = dialog.findViewById(R.id.btnSuccess);
        tvNotification1 = dialog.findViewById(R.id.textview);
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();
            }
        });
    }

    public void showDialogFail() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error_enrollment);
        dialog.show();
        btnSuccess = dialog.findViewById(R.id.btnOKK);
        tvNotification1 = dialog.findViewById(R.id.tvError);
        tvNotification1.setText(Language.UpdateEnFail);
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();
            }
        });
    }

    public void loadEnrollmentByTraineeID() {

        final Enrollment enrollment = new Enrollment(
                new EnrollmentID(classID1, edtTraineeId.getText().toString()));


        Call<ArrayList<Enrollment>> callssssssss = enrollmentService.getEnrollmentByTraineeID(traineeID);
        callssssssss.enqueue(new Callback<ArrayList<Enrollment>>() {
            @Override
            public void onResponse(Call<ArrayList<Enrollment>> call, Response<ArrayList<Enrollment>> response) {

                ArrayList<Enrollment> enrollments = response.body();

                populateUIEnrollment(enrollments);
                ArrayList<Integer> classIDList = new ArrayList<>();
                classIDList.clear();


                for (int i = 0; i < enrollments.size(); i++) {
                    int classID2=enrollments.get(i).getEnrollmentID().getClassId();

                    if (classID2 == classID1){
                        tempID=classID2;

                    } else {
                        Log.e("else", classID2+"");

                    }
                }
                if (tempID==classID1){
                    showDialogFail();
                }else {


                    Call<Void> calls = enrollmentService.updateEnrollment( traineeID, classID, enrollment);

                        calls.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                showDialogSuccess();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                showDialogFail();
                                tvNotification1.setText(Language.UpdateFail);

                                Toast.makeText(EditEnrollmentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Enrollment>> call, Throwable t) {
                showDialogFail();
                tvNotification1.setText(Language.UpdateFail);

                Toast.makeText(EditEnrollmentActivity.this, "EditEnrollmentActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

//        Log.e("eror", "tieppppp");
    }

    private void populateUIEnrollment(ArrayList<Enrollment> enrollments) {
        if (enrollments == null) {
            return;
        }
    }

}