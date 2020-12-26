package vn.com.r2s.fms.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Module;


public class ByPercentResultActivity extends AppCompatActivity {
    Button btnShowOver, btnDetail, btnViewComment;
    Spinner spinnerClass, spinnerModule;
    private ClassicService classicService;
    private ModuleService moduleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_percent_result);
        classicService = APIUtility.getClassicService();
        moduleService = APIUtility.getModuleService();
        init();
        ShowComment();
        ShowOverView();
        getSpinnerClass();

    }

    public void init() {
        btnShowOver = findViewById(R.id.btnOverview);
        btnViewComment = findViewById(R.id.btnComment);
        spinnerClass = findViewById(R.id.spClass);
        spinnerModule = findViewById(R.id.spModule);
    }

    //getSpinner

    //get Spinner Class
    public void getSpinnerClass() {
        Call<ArrayList<Classic>> calls = classicService.getAllClass();
        calls.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> call, Response<ArrayList<Classic>> response) {
                ArrayList<Classic> classics = response.body();
                ArrayList<String> className = new ArrayList<>();
                className.clear();
                populateUIClass(classics);
                for (int i = 0; i < classics.size(); i++) {
                    className.add(classics.get(i).getClassName());
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, className);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClass.setAdapter(adapter);
                    spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

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
    //get Spinner Module
    //get Spinner Module
    public void getSpinnerModule() {
        Call<ArrayList<Module>> calls = moduleService.getAllModule();
        calls.enqueue(new Callback<ArrayList<Module>>() {
            @Override
            public void onResponse(Call<ArrayList<Module>> call, Response<ArrayList<Module>> response) {
                ArrayList<Module> modules = response.body();
                ArrayList<String> moduleName = new ArrayList<>();
                moduleName.clear();
                populateUIModule(modules);
                for (int i = 0; i < modules.size(); i++) {
                    moduleName.add(modules.get(i).getModuleName());
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, moduleName);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerModule.setAdapter(adapter);
                    spinnerModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Module>> call, Throwable t) {

            }
        });
    }

    private void populateUIModule(ArrayList<Module> modules) {
        if (modules == null) {
            return;
        }
    }
    //ShowOverView
    public void ShowOverView() {
        btnShowOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //ShowComment

    public void ShowComment() {

        btnViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ByPercentResultActivity.this, ShowCommentActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

}