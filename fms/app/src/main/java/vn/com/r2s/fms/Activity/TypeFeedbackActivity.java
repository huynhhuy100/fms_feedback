package vn.com.r2s.fms.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.TypeFeedbackService;

import vn.com.r2s.fms.model.TypeFeedback;

public class TypeFeedbackActivity extends AppCompatActivity {
    private EditText edtTypename;
    private boolean isDeleted;
    private Button btnSave, btnCancel, btnOKK;
    TextView textview;
    private long mTypeFeedbackId;
    private Intent intent;
    private TypeFeedbackService typeFeedbackService;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_feedback);
        typeFeedbackService = APIUtility.getTypeFeedbackService();
        initViews();
        intent = getIntent();

        if (intent != null && intent.hasExtra(Constants.UPDATE_TYPEFEEDBACK_ID)) {
            btnSave.setText("Update");
            mTypeFeedbackId = intent.getLongExtra(Constants.UPDATE_TYPEFEEDBACK_ID, -1);
        }

        Call<TypeFeedback> call = typeFeedbackService.getTypeFeedbackById(mTypeFeedbackId);
        call.enqueue(new Callback<TypeFeedback>() {
            @Override
            public void onResponse(Call<TypeFeedback> call, Response<TypeFeedback> response) {
                TypeFeedback typeFeedback = response.body();
                populateUI(typeFeedback);
            }

            @Override
            public void onFailure(Call<TypeFeedback> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "TypeFeedbackActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void populateUI(TypeFeedback typeFeedback) {
        if (typeFeedback == null) {
            return;
        }
        edtTypename.setText(typeFeedback.getTypeName());
    }
    private void initViews() {

        edtTypename = findViewById(R.id.edttypename);
        isDeleted = true;
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onSaveButtonClick();
                } catch (Exception e){
                    Log.e("loi init view typefeedback", "loi init view" + e.getMessage());
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
        final TypeFeedback typeFeedback = new TypeFeedback(edtTypename.getText().toString(),isDeleted);
        AppExecutors.getInstance().netWorkIO().execute(new Runnable(){
            @Override
            public void run(){
                if (!intent.hasExtra(Constants.UPDATE_TYPEFEEDBACK_ID)) {
                    Call<TypeFeedback> call = typeFeedbackService.postTypeFeedback(typeFeedback);
                    call.enqueue(new Callback<TypeFeedback>() {
                        @Override
                        public void onResponse(Call<TypeFeedback> call, Response<TypeFeedback> response) {
                           // Toast.makeText(TypeFeedbackActivity.this,"Add TypeFeedback successful", Toast.LENGTH_SHORT).show();
                            showDialogSuccess("Add Success");
                        }
                        @Override
                        public void onFailure(Call<TypeFeedback> call, Throwable t) {
                            Toast.makeText(TypeFeedbackActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Call<Void> call = typeFeedbackService.updateTypeFeedback(mTypeFeedbackId, typeFeedback);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                           // Toast.makeText(TypeFeedbackActivity.this,"Update typefeedback successful", Toast.LENGTH_SHORT).show();
                            showDialogSuccess("Updated Success");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(TypeFeedbackActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
    public void showDialogSuccess(String str) {
        dialog = new Dialog(TypeFeedbackActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        btnOKK = dialog.findViewById(R.id.btnOKK) ;
        textview = dialog.findViewById(R.id.textview);
        textview.setText(str);
        dialog.show();
            btnOKK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onBackPressed();

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
