package vn.com.r2s.fms.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.model.Enrollment;
import vn.com.r2s.fms.model.EnrollmentID;

public class EnrollmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText edtTraineeName, edtClassId,edtTraineeId, etClassName;
    private Button btnSave, btnCancel;
    private Integer mPersonId;
    private Intent intent;
    private EnrollmentService enrollmentService;
    private Spinner spnClassName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        enrollmentService = APIUtility.getEnService();
        intent = getIntent();
    }
    private void init(){
        etClassName = findViewById(R.id.edtClassName);
        edtClassId = findViewById(R.id.edtClassId);
        edtTraineeId = findViewById(R.id.edtTraineeId);
        edtTraineeName= findViewById(R.id.edtTraineeName);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnBack);

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
}