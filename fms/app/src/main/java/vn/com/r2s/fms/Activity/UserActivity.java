package vn.com.r2s.fms.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.UserService;
import vn.com.r2s.fms.model.User;

public class UserActivity extends AppCompatActivity {
    private EditText edtFName, edtLName, edtEmail;
    private Button btnSave, btnCancel;
    private long mPersonId;
    private Intent intent;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        userService = APIUtility.getUserService();
        intent = getIntent();

        if (intent != null && intent.hasExtra(Constants.UPDATE_USER_ID)) {
            btnSave.setText("Update");
            mPersonId = intent.getLongExtra(Constants.UPDATE_USER_ID, -1);
        }

        Call<User> call = userService.getUserById(mPersonId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                populateUI(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUI(User user) {
        if (user == null) {
            return;
        }
        edtFName.setText(user.getFirstName());
        edtLName.setText(user.getLastName());
        edtEmail.setText(user.getEmail());
    }

    private void initViews() {
        edtFName = findViewById(R.id.etFirstName);
        edtLName = findViewById(R.id.etLastName);
        edtEmail = findViewById(R.id.etEmail);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onSaveButtonClick();
                } catch (Exception e){
                    Log.e("kkkkkkkkk", "eeeeeee" + e.getMessage());
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void onSaveButtonClick() {
        final User user = new User(edtFName.getText().toString(),edtLName.getText().toString(), edtEmail.getText().toString());

        AppExecutors.getInstance().netWorkIO().execute(new Runnable(){
            @Override
            public void run(){
                if (!intent.hasExtra(Constants.UPDATE_USER_ID)) {
                    Call<User> call = userService.postUser(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Toast.makeText(UserActivity.this,"Add user successful", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(UserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Call<Void> call = userService.updateUser(mPersonId, user);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(UserActivity.this,"Update user successful", Toast.LENGTH_SHORT).show();
                            onBackPressed();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(UserActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
