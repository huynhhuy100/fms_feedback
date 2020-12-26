package vn.com.r2s.fms.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.ui.classic.ClassicFragment;


public class ClassicActivity extends AppCompatActivity {
    EditText edtClassName,
            edtCapacity;

    LinearLayout linEndDate,
            linStartDate;

    TextView tvStartTime,
            tvEndTime,
            tvTitle,
            tvNotification,
            tvCheckClassName,
            tvCheckCapacity,
            tvCheckStartTime,
            tvCheckEndTime,
            tvFailed;

    Button btnSave,
            btnBack,
            btnOKK,
            btnOkFail;

    String strStartDate,
            strEndDate;

    private ClassicService classicService;

    private Intent intent;

    private int mPersonId;

    Classic classic = null;

    private Dialog dialog;

    //    public static ClassicActivity classicActivity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classis_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        event();
        dieukien();
        saveData();


    }

    private void init() {
        edtClassName = findViewById(R.id.edtClassName);
        edtCapacity = findViewById(R.id.edtCapacity);
        linStartDate = findViewById(R.id.linStartDate);
        linEndDate = findViewById(R.id.linEndDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        tvTitle = findViewById(R.id.tvTitle);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        tvCheckClassName = findViewById(R.id.tvCheckClassName);
        tvCheckCapacity = findViewById(R.id.tvCheckCapacity);
        tvCheckStartTime = findViewById(R.id.tvCheckStartTime);
        tvCheckEndTime = findViewById(R.id.tvCheckEndTime);
    }

    private void event() {

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datefirst = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthofyear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth);

                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                tvStartTime.setText(sdf.format(myCalendar.getTime()));
//                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//                strStartDate = sdf2.format((myCalendar.getTime()));

            }
        };

        final DatePickerDialog.OnDateSetListener dateend = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthofyear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth);

                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                tvEndTime.setText(sdf.format(myCalendar.getTime()));
            }
        };


        linStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ClassicActivity.this, datefirst, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        linEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ClassicActivity.this, dateend, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void dieukien() {
        classicService = APIUtility.getClassicService();

        intent = getIntent();

        if (intent != null && intent.hasExtra(Constants.UPDATE_USER_ID)) {
            btnSave.setText("Update");
            tvTitle.setText("Edit Class");
            mPersonId = intent.getIntExtra(Constants.UPDATE_USER_ID, -1);
        }

        Call<Classic> call = classicService.getClassById(mPersonId);
        call.enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                Classic classic = response.body();
//                Log.d("yyyyy", classic.getStartTime() + "");
                populateUI(classic);
            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(),
                        "UserActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUI(Classic classic) {
        if (classic == null) {
            return;
        }

        String month = classic.getStartTime().substring(5, 7);
        String day = classic.getStartTime().substring(8, 10);
        String year = classic.getStartTime().substring(0, 4);

        String month1 = classic.getEndTime().substring(5, 7);
        String day1 = classic.getEndTime().substring(8, 10);
        String year1 = classic.getEndTime().substring(0, 4);

        String stratDate = month + "/" + day + "/" + year;
        String endDate = month1 + "/" + day1 + "/" + year1;

        edtClassName.setText(classic.getClassName());
        edtCapacity.setText(classic.getCapadity() + "");
        tvStartTime.setText(stratDate);
        tvEndTime.setText(endDate);
        Log.d("dataaaaaaaaaaaa  ", classic.getClassName());
    }

    public void saveData() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addAndEditClass();
                check();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        dialog = new Dialog(ClassicActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.show();
        btnOKK = dialog.findViewById(R.id.btnSuccess);
        tvNotification = dialog.findViewById(R.id.textview);
        tvNotification.setText(notifi);
        btnOKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ClassicActivity.this, "ok", Toast.LENGTH_SHORT).show();
                // do it
                dialog.dismiss();
                onBackPressed();
            }
        });
    }

    public void showDialogFailed() {
        String notifi = tvTitle.getText().toString();

        dialog = new Dialog(ClassicActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_faild);
        dialog.show();
        btnOkFail = dialog.findViewById(R.id.btnOk);
        tvFailed = dialog.findViewById(R.id.tvFailed);
        if (notifi.equals(Language.addClass)) {
            tvFailed.setText(Language.AddFailed);
        } else {
            tvFailed.setText(Language.updateFailed);
        }

        btnOkFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ClassicActivity.this, "ok", Toast.LENGTH_SHORT).show();
                // do it
                dialog.dismiss();
            }
        });
    }

//    public static ClassicActivity classicActivity() {
//        // hàm này tạo ra để gọi thẳng đến fragmentDrinkManagement để lấy những hàm khác trong fragmentDrinkManagement
//        // đây là cách static nguyên class
//        return classicActivity;
//    }

    public void addAndEditClass() {
        String startTime = tvStartTime.getText().toString();
        String endTime = tvEndTime.getText().toString();

        String month = startTime.substring(0, 2);
        String day = startTime.substring(3, 5);
        String year = startTime.substring(6);

        String month1 = endTime.substring(0, 2);
        String day1 = endTime.substring(3, 5);
        String year1 = endTime.substring(6);

        String stratDate = year + "-" + month + "-" + day;
        String endDate = year1 + "-" + month1 + "-" + day1;

        Log.d("->>>>>>>>", day + " , " + month + " , " + year + "-----" + stratDate);

        // Log.d("class -> class", "-----------"  + "++++++++" + "///////////" + ">>>>>>>>>"+   sdf.parse(dateInString));

        classic = new Classic(edtClassName.getText().toString(), Integer.parseInt(edtCapacity.getText().toString()), stratDate, endDate);


        AppExecutors.getInstance().netWorkIO().execute(() -> {
            if (!intent.hasExtra(Constants.UPDATE_USER_ID)) {
                Call<Classic> call = classicService.postClass(classic);
                call.enqueue(new Callback<Classic>() {
                    @Override
                    public void onResponse(Call<Classic> call, Response<Classic> response) {
                        showDialogSuccess(Language.Add);
                    }

                    @Override
                    public void onFailure(Call<Classic> call, Throwable t) {
                        Toast.makeText(ClassicActivity.this, t.getMessage()
                                , Toast.LENGTH_SHORT).show();
                        Log.d("yyyyy", t.getMessage());
                    }
                });
            } else {
                //Log.i("Huong", classic.getClassName() + ", " + classic.getStartTime());
                // Class2 class2 = new Class2("abc", 1, "2000-11-11", "2000-12-12");
                Call<Void> call = classicService.updateClass(mPersonId, classic);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
//                                    Toast.makeText(ClassicActivity.this, "Update user successful"
//                                            , Toast.LENGTH_SHORT).show();
                        showDialogSuccess(Language.update);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ClassicActivity.this, t.getMessage()
                                , Toast.LENGTH_SHORT).show();
                        Log.d("yyyyy", t.getMessage());
                    }
                });
            }
        });

    }

    public void check() {
        String startTime = tvStartTime.getText().toString();
        String endTime = tvEndTime.getText().toString();

        SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
//            Date startDate = formatter1.parse(startTime);
//            Date endDate = formatter1.parse(endTime);

            Date nowDate = formatter.parse(String.valueOf(LocalDate.now()));
//            Log.d("00003 -> date", startTime + " " + endTime + " 00002 -> Date: " + formatter1.parse(startTime) + " " + formatter1.parse(endTime) + "day now: " + nowDate);
            if (edtClassName.getText().toString().isEmpty() || edtClassName.getText().toString().length() > 255) {
                showDialogFailed();
                tvCheckClassName.setText(Language.className);
                tvCheckCapacity.setText("");
                tvCheckStartTime.setText("");
                tvCheckEndTime.setText("");
            } else if (edtCapacity.getText().toString().isEmpty() || Integer.parseInt(edtCapacity.getText().toString()) == 0) {
                showDialogFailed();
                tvCheckCapacity.setText(Language.capacity);
                tvCheckStartTime.setText("");
                tvCheckEndTime.setText("");
                tvCheckClassName.setText("");
            } else if (tvStartTime.getText().toString().isEmpty()) {
                showDialogFailed();
                tvCheckStartTime.setText(Language.date);
                tvCheckEndTime.setText("");
                tvCheckClassName.setText("");
                tvCheckCapacity.setText("");
            } else if (tvEndTime.getText().toString().isEmpty()) {
                showDialogFailed();
                tvCheckEndTime.setText(Language.date);
                tvCheckClassName.setText("");
                tvCheckCapacity.setText("");
                tvCheckStartTime.setText("");
            } else if (formatter1.parse(startTime).compareTo(nowDate) < 0) {
                showDialogFailed();
                tvCheckStartTime.setText(Language.date);
                tvCheckEndTime.setText("");
                tvCheckClassName.setText("");
                tvCheckCapacity.setText("");
            } else if (formatter1.parse(startTime).compareTo(formatter1.parse(endTime)) == 0) {
                showDialogFailed();
                tvCheckStartTime.setText(Language.date);
                tvCheckEndTime.setText(Language.date);
                tvCheckClassName.setText("");
                tvCheckCapacity.setText("");
            } else if (formatter1.parse(startTime).compareTo(formatter1.parse(endTime)) > 0) {
                showDialogFailed();
                tvCheckEndTime.setText(Language.date);
                tvCheckClassName.setText("");
                tvCheckCapacity.setText("");
                tvCheckStartTime.setText("");
            } else {
                addAndEditClass();
                tvCheckClassName.setText("");
                tvCheckCapacity.setText("");
                tvCheckStartTime.setText("");
                tvCheckEndTime.setText("");
//                Toast.makeText(ClassicActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
//            else if (startDate.compareTo(endDate) > 0) {
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
