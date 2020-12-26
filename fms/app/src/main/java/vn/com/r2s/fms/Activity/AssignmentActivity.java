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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.AppExecutors;
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

public class AssignmentActivity extends AppCompatActivity {

    Button btnBack;
    Button btnSave;
    Button btnOk;
    Button btnOkFail;
    TextView tvFailed;
    TextView tvTitle;

    TextView tvTitleAsm;
    TextView tvNotification;

    Spinner spn_className;
    Spinner spn_moduleName;
    Spinner spn_trainerName;

    private Intent intent;
    private Dialog dialog;

    private AssignmentService asmService;
    private ModuleService moduleService;
    private ClassicService classicService;
    private LoginService trainerService;

    private int classId;
    private int moduleId;

    String moduleName,
            trainerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        init();

        classicService = APIUtility.getClassicService();
        moduleService = APIUtility.getModuleService();
        trainerService = APIUtility.getLoginService();

        intent = getIntent();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick();
            }
        });


        Call<ArrayList<Classic>> callClass = classicService.getAllClass();
        callClass.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> call, Response<ArrayList<Classic>> response) {
                ArrayList<Classic> classics = response.body();
                ArrayList<String> classicNameList = new ArrayList<>();
                classicNameList.clear();

                for (int i = 0; i < classics.size(); i++) {
                    classicNameList.add(classics.get(i).getClassName());
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, classicNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_className.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Classic>> call, Throwable t) {

            }
        });

        Call<ArrayList<Module>> callModule = moduleService.getAllModule();
        callModule.enqueue(new Callback<ArrayList<Module>>() {
            @Override
            public void onResponse(Call<ArrayList<Module>> call, Response<ArrayList<Module>> response) {
                ArrayList<Module> modules = response.body();
                ArrayList<String> moduleNameList = new ArrayList<>();
                moduleNameList.clear();

                for (int i = 0; i < response.body().size(); i++) {
                    moduleNameList.add(response.body().get(i).getModuleName());
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, moduleNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_moduleName.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Module>> call, Throwable t) {

            }
        });

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
                    spn_trainerName.setAdapter(adapter);
                }
            }


            @Override
            public void onFailure(Call<ArrayList<Trainer>> call, Throwable t) {

            }
        });
    }

    private void init() {
        spn_className = findViewById(R.id.spn_asmClassName);
        spn_moduleName = findViewById(R.id.spn_asmModuleName);
        spn_trainerName = findViewById(R.id.spn_asmTrainerName);

        btnBack = findViewById(R.id.btn_backASM);
        btnSave = findViewById(R.id.btn_saveASM);

        tvTitleAsm = findViewById(R.id.tvTitleAsm);
    }


    public void onSaveButtonClick() {
        trainerId = (String) spn_trainerName.getSelectedItem();

        moduleName = (String) spn_moduleName.getSelectedItem();

        String className = (String) spn_className.getSelectedItem();

        Call<Classic> callClassic2 = classicService.getClassByClassName(className);
        callClassic2.enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                classId = response.body().getClassID();
                getData(classId, moduleName,trainerId);
            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {

            }
        });
    }

    public void getData(int classID, String moduleName,String trainerId ) {
        Call<Module> callModule2 = moduleService.getModuleByName(moduleName);
        callModule2.enqueue(new Callback<Module>() {
            @Override
            public void onResponse(Call<Module> call, Response<Module> response) {
                moduleId = response.body().getModuleId();
                getFullData(classID,moduleId,trainerId);
            }

            @Override
            public void onFailure(Call<Module> call, Throwable t) {

            }
        });
    }

    public void getFullData(int clazzID, int moduleId, String trainerID) {

        Date now = new Date();
        Timestamp timestamp = new Timestamp(now.getTime());
        String timeString = timestamp.toString().replaceAll("\\s", "").replaceAll(":", "");
        String registrationCode = "CL" + clazzID + "M" + moduleId + "T" + timeString.replaceAll("-", "").replace(".", "");

        Assignment asm = new Assignment(clazzID, moduleId, trainerID, registrationCode);

        asmService = APIUtility.getAssignmentService();
        try {
            asmService.getAssignmentById(classId, moduleId, trainerID).enqueue(new Callback<Assignment>() {
                @Override
                public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                    try {
                        if (response.body().getTrainerId() != trainerID && response.body().getClassId() != clazzID && response.body().getModuleId() != moduleId) {

                        } else {
                            showDialogFailed();
                        }
                    } catch (NullPointerException ex) {
                        AppExecutors.getInstance().netWorkIO().execute(() -> {
                            asmService = APIUtility.getAssignmentService();
                            Call<Assignment> calls = asmService.postAssignment(asm);
                            calls.enqueue(new Callback<Assignment>() {
                                @Override
                                public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                                    showDialogSuccess(Language.Add);
                                }

                                @Override
                                public void onFailure(Call<Assignment> call, Throwable t) {
                                }
                            });
                        });
                    }
                }

                @Override
                public void onFailure(Call<Assignment> call, Throwable t) {

                }
            });
        } catch (NullPointerException ex) {

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

    public void showDialogSuccess(String notifi) {
        dialog = new Dialog(AssignmentActivity.this);
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

        dialog = new Dialog(AssignmentActivity.this);
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