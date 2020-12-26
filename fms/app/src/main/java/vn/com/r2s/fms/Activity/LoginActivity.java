package vn.com.r2s.fms.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.MainActivity2;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.LoginService;
import vn.com.r2s.fms.model.Admin;
import vn.com.r2s.fms.model.Trainee;
import vn.com.r2s.fms.model.Trainer;

public class LoginActivity extends AppCompatActivity {

    Button btn_login, btnCancel;
    Spinner spinner;
    CheckBox saveLoginCheckBox;
    LoginService loginService;
    String typeUser;
    private TextInputEditText edt_userName, edt_password;
    private TextInputLayout tlUsername, tlPass;
    private String TAG = LoginActivity.class.getSimpleName();
    private List<String> list;
    private Dialog dialog;
    String saveLogin = "saveLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        init();

//khai báo List
        list = new ArrayList<>();
        list.add(Language.admin);
        list.add(Language.trainee);
        list.add(Language.trainer);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                typeUser = list.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(LoginActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });

//CheckValidate Login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validatePass() | !validateUsername()){
                    return;
                }
                else {
                    if (typeUser.equals(Language.trainee)) {
                        setTrainee();
                    } else if (typeUser.equals(Language.admin)) {
                        setAdmin();
                    } else if (typeUser.equals(Language.trainer)) {
                        setTrainer();
                    }
                }
            }
        });
    }

//Set Validate Login
    public boolean validateUsername() {
        String userName = edt_userName.getText().toString().trim();
        if (userName.isEmpty()) {
            tlUsername.setError(Language.ValPassLogin2);
            return false;
        } else if(edt_userName.getText().toString() != userName){
           tlUsername.setError(Language.Vallogin2);
            return false;
        }else {
            tlUsername.setError(null);
            return true;
        }
    }
    public boolean validatePass() {
        String pass = edt_password.getText().toString().trim();
                if (pass.isEmpty()) {
            tlPass.setError(Language.ValPassLogin2);
            return false;
        } else if (pass.length() < 2){
          tlPass.setError(Language.ValPassLogin2);
          return false;
        } else if(edt_password.getText().toString() != pass) {
            tlPass.setError(Language.Vallogin2);
            return false;
        } else {
            tlPass.setError(null);
            return true;
        }
    }

// Remember Password
    @Override
    protected void onPause() {
        super.onPause();
//lưu thông tin
        SharedPreferences preferences = getSharedPreferences(saveLogin, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String email = edt_userName.getText().toString().trim();
        String passw = edt_password.getText().toString().trim();
        //set Null TextinputLayout
        tlUsername.setError(null);
        tlPass.setError(null);
        tlPass.clearFocus();
        tlUsername.clearFocus();
        editor.putString("email", email);
        editor.putString("password", passw);
        editor.putBoolean("save", saveLoginCheckBox.isChecked());
        editor.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
//phục hồi thông tin
        SharedPreferences preferences = getSharedPreferences(saveLogin, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String pass = preferences.getString("password", "");
        boolean save = preferences.getBoolean("save", false);
        if (save) {
            edt_userName.setText(email);
            edt_password.setText(pass);
        }
        saveLoginCheckBox.setChecked(save);
    }

    //Ánh xạ
    private void init() {
        btn_login = findViewById(R.id.btn_login);
        btnCancel = findViewById(R.id.btn_logBack);
        edt_userName = findViewById(R.id.edt_userName);
        edt_password = findViewById(R.id.edt_password);
        spinner = findViewById(R.id.id_spinnerLogin);
        saveLoginCheckBox = findViewById(R.id.checkbox);
        tlUsername = findViewById(R.id.userName_til);
        tlPass = findViewById(R.id.till_pass);
    }

    public void showdialog(String notifi) {
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);
        dialog.show();
        btnCancel = dialog.findViewById(R.id.btn_logBack);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // Set access Login Admin
    public void setAdmin() {

        loginService = APIUtility.getLoginService();

        String userName = edt_userName.getText().toString();
        String password = edt_password.getText().toString();

        Call<Admin> call = loginService.getAdminByUserName(userName);
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                Admin admin = response.body();
                try {
                    if (admin.getPassword().equals(password)) {
                        Log.e("userName", typeUser);
                        doLogin(Language.admin, userName);
                    } else {
                        showdialog(Language.admin);
                    }
                } catch (Exception e) {
                    if (userName.length() < 2){
                        tlUsername.setError(Language.ValUserLogin);
                    } else {
                        showdialog(Language.admin);
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(),
                        "LoginAcitivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Set access Login Trainee
    public void setTrainee() {
        loginService = APIUtility.getLoginService();

        String userName = edt_userName.getText().toString();
        String password = edt_password.getText().toString();

        Call<Trainee> call = loginService.getTraieeByUserName(userName);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                Trainee trainee = response.body();
                try {
                    if (trainee.getPassword().equals(password)) {
                        doLogin2(Language.trainee, userName);
                    } else {
                        showdialog(Language.trainee);
                    }
                } catch (Exception e) {
                    if (userName.length() < 2){
                        tlUsername.setError(Language.ValUserLogin);
                    } else {
                        showdialog(Language.trainee);
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(),
                        "LoginAcitivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Set access Login Trainer
    public void setTrainer() {

        loginService = APIUtility.getLoginService();

        String userName = edt_userName.getText().toString();
        String password = edt_password.getText().toString();

        Call<Trainer> call = loginService.getTrainerByUserName(userName);
        call.enqueue(new Callback<Trainer>() {
            @Override
            public void onResponse(Call<Trainer> call, Response<Trainer> response) {
                Trainer trainer = response.body();
                try {
                    if (trainer.getPassword().equals(password)) {
                        doLogin(Language.trainer,userName);
                    } else {
                        showdialog(Language.trainer);
                    }
                } catch (Exception e) {
//                    tlUsername.setError("Please check your account information and password");
                    if (userName.length() < 2){
                        tlUsername.setError(Language.ValUserLogin);
                    } else {
                        showdialog(Language.trainer);
                    }
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<Trainer> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(),
                        "LoginAcitivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void doLogin(String typeUser, String userName) {
        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
        intent1.putExtra("typeUser", typeUser);
        intent1.putExtra("userName",userName);
        startActivity(intent1);
    }

    public void doLogin2(String typeUser, String userName) {
        Intent intent1 = new Intent(LoginActivity.this, MainActivity2.class);
        intent1.putExtra("typeUser", typeUser);
        intent1.putExtra("userName",userName);
        startActivity(intent1);
    }
}