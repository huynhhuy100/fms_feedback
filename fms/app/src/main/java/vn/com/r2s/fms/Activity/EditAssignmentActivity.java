package vn.com.r2s.fms.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.LoginService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Module;
import vn.com.r2s.fms.model.Trainer;

public class EditAssignmentActivity extends AppCompatActivity {

    TextView edt_asmClassName;
    TextView edt_asmModuleName;
    TextView edt_asmClassId;
    TextView edt_asmModuleId;
    Spinner spn_asmTrainerName;
    Button btnBack;
    Button btnSave;
    Button btnOk;
    TextView tvNotification;
    TextView tvFailed;
    TextView tvTitle;
    Button btnOkFail;
    Dialog dialog;

    Intent intent;

    String className;
    String moduleName;
    String registrationCode;

    private LoginService trainerService;
    private ModuleService moduleService;
    private ClassicService classicService;
    private AssignmentService asmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        init();

        getData();

    }

    private void init() {
        edt_asmClassId = findViewById(R.id.edt_asmUpdateClassId);
        edt_asmClassName = findViewById(R.id.edt_asmUpdateClassName);
        edt_asmModuleId = findViewById(R.id.edt_asmUpdateModuleId);
        edt_asmModuleName = findViewById(R.id.edt_asmUpdateModuleName);
        spn_asmTrainerName = findViewById(R.id.spn_asmUpdateTrainerName);
        btnBack = findViewById(R.id.btn_backEditASM);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave = findViewById(R.id.btn_saveEditASM);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButton();
            }
        });

    }

    private void getData() {
        intent = getIntent();
        className = intent.getStringExtra("className");
        moduleName = intent.getStringExtra("moduleName");

        edt_asmModuleName.setText("  "+moduleName);
        edt_asmClassName.setText("  "+className);

        classicService = APIUtility.getClassicService();
        moduleService = APIUtility.getModuleService();

        Call<Classic> callClassic = classicService.getClassByClassName(className);
        callClassic.enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                String classId = String.valueOf(response.body().getClassID());
                edt_asmClassId.setText("  "+classId);
            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {

            }
        });

        Call<Module> callModule = moduleService.getModuleByName(moduleName);
        callModule.enqueue(new Callback<Module>() {
            @Override
            public void onResponse(Call<Module> call, Response<Module> response) {
                String moduleId = String.valueOf(response.body().getModuleId());
                edt_asmModuleId.setText("  "+moduleId);
            }

            @Override
            public void onFailure(Call<Module> call, Throwable t) {

            }
        });

        trainerService = APIUtility.getLoginService();

        Call<ArrayList<Trainer>> callTrainer = trainerService.getAllTrainer();
        callTrainer.enqueue(new Callback<ArrayList<Trainer>>() {
            @Override
            public void onResponse(Call<ArrayList<Trainer>> call, Response<ArrayList<Trainer>> response) {
                ArrayList<Trainer> trainers = response.body();
                ArrayList<String> trainerNameList = new ArrayList<>();
                trainerNameList.clear();

                for (int i = 0; i < trainers.size(); i++) {
                    trainerNameList.add(trainers.get(i).getUserName());
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, trainerNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_asmTrainerName.setAdapter(adapter);
                }
            }


            @Override
            public void onFailure(Call<ArrayList<Trainer>> call, Throwable t) {

            }
        });
    }

    private void onSaveButton() {
        String classs = edt_asmClassId.getText().toString().replaceAll(" ", "");
        String module = edt_asmModuleId.getText().toString().replaceAll(" ", "");

        intent = getIntent();
        String trainer = intent.getStringExtra("trainerId");

        int classId = Integer.parseInt(classs);
        int moduleId = Integer.parseInt(module);

        String trainerId = (String) spn_asmTrainerName.getSelectedItem().toString();
        Assignment asm = new Assignment(classId, moduleId, trainerId);
        asmService = APIUtility.getAssignmentService();

        asmService.getAssignmentById(classId, moduleId, trainerId).enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                try {
                    if (response.body().getTrainerId() != trainer && response.body().getClassId() != classId && response.body().getModuleId() != moduleId) {

                    } else {
                        showDialogFailed();
                    }
                } catch (NullPointerException ex) {
                    Call<Assignment> callAsm = asmService.updateAssignment(classId, moduleId, trainer, asm);
                    callAsm.enqueue(new Callback<Assignment>() {
                        @Override
                        public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                            showDialogSuccess(Language.Add);
                            getData();
                        }

                        @Override
                        public void onFailure(Call<Assignment> call, Throwable t) {
                            showDialogFailed();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable t) {

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

    public void showDialogSuccess(String notifi) {
        dialog = new Dialog(EditAssignmentActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.show();
        btnOk = dialog.findViewById(R.id.btnSuccess);
        tvNotification = dialog.findViewById(R.id.textview);
        tvNotification.setText(notifi);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();
            }
        });
    }

    public void showDialogFailed() {
        dialog = new Dialog(EditAssignmentActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_faild);
        dialog.show();
        btnOkFail = dialog.findViewById(R.id.btnOk);
        tvFailed = dialog.findViewById(R.id.tvFailed);
        tvFailed.setText(Language.AddAsmFail);
        btnOkFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}