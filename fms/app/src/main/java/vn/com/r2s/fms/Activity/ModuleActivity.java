package vn.com.r2s.fms.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.FeedbackService;
import vn.com.r2s.fms.api.LoginService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.api.UserService;
import vn.com.r2s.fms.model.Admin;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Feedback;
import vn.com.r2s.fms.model.Module;
import vn.com.r2s.fms.model.TypeFeedback;
import vn.com.r2s.fms.model.User;

public class ModuleActivity extends AppCompatActivity {
    EditText etModuleName;
    LinearLayout linStartDate, linEndDate, linFBStartDate, linFBEndDate;
    Button btnSaveModule, btnBackModule, btnOKK1, btnOKFAIL;
    Spinner spnAdmin, spnFBtitle;
    private long mFeadbackid;
    public static String username;

    FeedbackService feedbackService;
    private ArrayList<Admin> adminList;
    UserService userService;
    LoginService loginService;

    TextView
            tvStartDate,
            tvEndDate,
            tvModuleID,

    tvModuleName,
            tvStartTime,
            tvEndTime,
            tvAdminID,
            tvFBStartDate,
            tvFBEndDate,
            tvTitle1,
            tvNotification1,
            tvFailed1, tvCheckModuleName, tvCheckStartdate, tvCheckenddate, tvCheckAdmindID, tvCheckFBtitle, tvCheckFBstartdate, tvCheckFBenddate;
    private ModuleService moduleService;
    private Intent intent;
    private int mPersonId;
    Module module = null;
    String typeUser;
    private Dialog dialog;
    String countryList[] = {"Test", "Test1", "Test2"};
    String countryLists[] = {"Feedback1", "Feedback2", "Feedback3"};
    ArrayList<Admin> Adminlist;
    private void populateUIs(ArrayList<Feedback> feadback) {
        if (feadback == null) {
            return;
        }
    }
    private void populateUIs1(ArrayList<User> user) {
        if (user == null) {
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_module);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        event();
        saveData();
        dieukien();

    }



    private void init() {
        tvCheckModuleName = findViewById(R.id.tvcheckModuleName);
        etModuleName = findViewById(R.id.etModuleName);
        linStartDate = findViewById(R.id.linStartDate1);
        linEndDate = findViewById(R.id.linEndDate1);
        linFBStartDate = findViewById(R.id.linFBStartDate);
        tvStartDate = findViewById(R.id.tvStartDate2);
        tvEndDate = findViewById(R.id.tvEndDate2);
        tvFBStartDate = findViewById(R.id.tvFBStartDate1);
        tvFBEndDate = findViewById(R.id.tvFBSEndDate1);
        linFBEndDate = findViewById(R.id.linFBEndDate);
        tvTitle1 = findViewById(R.id.tvTitle1);
        spnFBtitle = findViewById(R.id.spnFBtitle);
        spnAdmin = findViewById(R.id.spnAdmin);
        btnSaveModule = findViewById(R.id.btnSaveModule);
        btnBackModule = findViewById(R.id.btnBackModule);
        tvCheckStartdate = findViewById(R.id.tvCheckStartdate);
        tvCheckenddate = findViewById(R.id.tvCheckEnddate);
        tvCheckFBstartdate = findViewById(R.id.tvCheckFBstartdate);
        tvCheckFBenddate = findViewById(R.id.tvCheckFBenddate);
        tvModuleID = findViewById(R.id.tvModuleID);
        Adminlist = new ArrayList<>();
        feedbackService = APIUtility.getFeedbackService();
        userService = APIUtility.getUserService();

        Call<ArrayList<Feedback>> callss = feedbackService.getFB();
        callss.enqueue(new Callback<ArrayList<Feedback>>() {
            @Override
            public void onResponse(Call<ArrayList<Feedback>> call, Response<ArrayList<Feedback>> response) {
                ArrayList<Feedback> feedback = response.body();
//                ArrayList<String> tentllist = new ArrayList<>();
                ArrayList<Integer> tentllistid = new ArrayList<>();
                tentllistid.clear();
                for (int i=0;i<feedback.size();i++){
//                    tentllist.add(typeFeedback.get(i).getTypeName());
                    populateUIs(feedback);
                    tentllistid.add(feedback.get(i).getFeedbackId());
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,  tentllistid);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnFBtitle.setAdapter(adapter);
                    spnFBtitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
//                             Integer abc = idname.getInputType();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Feedback>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "FeedbackActivity-type: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        Call<ArrayList<User>> callsss = userService.getUsers();
        callsss.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> callsss, Response<ArrayList<User>> response) {
                ArrayList<User> user = response.body();
//                ArrayList<String> tentllist = new ArrayList<>();
                ArrayList<Long> tentllistid = new ArrayList<>();
                tentllistid.clear();
                for (int i=0;i<user.size();i++){
//                    tentllist.add(typeFeedback.get(i).getTypeName());
                    populateUIs1(user);
                    tentllistid.add(user.get(i).getId());
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,  tentllistid);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnAdmin.setAdapter(adapter);
                    spnAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
//                             Integer abc = idname.getInputType();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "FeedbackActivity-type: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

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
                tvStartDate.setText(sdf.format(myCalendar.getTime()));

            }
        };
        final Calendar myCalendar1 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datefirst1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthofyear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayofmonth);

                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                tvEndDate.setText(sdf.format(myCalendar1.getTime()));

            }
        };


        linStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ModuleActivity.this, datefirst, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        linEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ModuleActivity.this, datefirst1, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        linFBStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(tvFBStartDate);
            }
        });
        linFBEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(tvFBEndDate);
            }
        });
    }

    private void showDateTimeDialog(final TextView tvFBStartDate1) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String month = simpleDateFormat.format(calendar.getTime()).substring(5, 7);
                        String day = simpleDateFormat.format(calendar.getTime()).substring(8, 10);
                        String year = simpleDateFormat.format(calendar.getTime()).substring(0, 4);
                        String time = simpleDateFormat.format(calendar.getTime()).substring(10);
                        Log.e("00002", "day: " + day + " month: " + month + " year: " + year);
                        tvFBStartDate1.setText(month + "/" + day + "/" + year + time);
//                            tvFBStartDate1.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(ModuleActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(ModuleActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    private void showDateTimeDialog1(final TextView tvFBEndDate1) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddTHH:mm");
                        String month = simpleDateFormat.format(calendar.getTime()).substring(5, 7);
                        String day = simpleDateFormat.format(calendar.getTime()).substring(8, 10);
                        String year = simpleDateFormat.format(calendar.getTime()).substring(0, 4);
                        String time = simpleDateFormat.format(calendar.getTime()).substring(10);
                        Log.e("00002", "day: " + day + " month: " + month + " year: " + year);
                        tvFBEndDate1.setText(month + "/" + day + "/" + year + time);
//                            tvFBStartDate1.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(ModuleActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(ModuleActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    public void dieukien() {
        moduleService = APIUtility.getModuleService();

        intent = getIntent();

        if (intent != null && intent.hasExtra(Constants.UPDATE_USER_ID)) {
            btnSaveModule.setText("Update");
            tvTitle1.setText("Edit Class");
            mPersonId = intent.getIntExtra(Constants.UPDATE_USER_ID, -1);
        }

        Call<Module> call = moduleService.getModuleById(mPersonId);
        call.enqueue(new Callback<Module>() {
            @Override
            public void onResponse(Call<Module> call, Response<Module> response) {
                Module module = response.body();
//                Log.d("yyyyy", classic.getStartTime() + "");
                populateUI(module);
            }

            @Override
            public void onFailure(Call<Module> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(),
                        "UserActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//
////
    }

    private void populateUI(Module module) {
        if (module == null) {
            return;
        }
        String month = module.getStartTime().substring(5, 7);
        String day = module.getStartTime().substring(8, 10);
        String year = module.getStartTime().substring(0, 4);
//
        String month1 = module.getEndTime().substring(5, 7);
        String day1 = module.getEndTime().substring(8, 10);
        String year1 = module.getEndTime().substring(0, 4);

        String month2 = module.getFeedbackStartTime().substring(5, 7);
        String day2 = module.getFeedbackStartTime().substring(8, 10);
        String year2 = module.getFeedbackStartTime().substring(0, 4);
        String hours = module.getFeedbackStartTime().substring(11, 14);
        String minute = module.getFeedbackStartTime().substring(14, 16);


        Log.d("====>>>", month + "/" + day + "/" + year);
        String stratDate = month + "/" + day + "/" + year;
        String endDate = month1 + "/" + day1 + "/" + year1;
        String fbSdate = year2 + "/" + month2 + "/" + day2 + " " + hours + "" + minute + "";
        String fbEdate = year2 + "/" + month2 + "/" + day2 + " " + hours + "" + minute + "";


        etModuleName.setText(module.getModuleName());
        tvStartDate.setText(stratDate);
        tvEndDate.setText(endDate);
        tvFBStartDate.setText(fbSdate);
        tvFBEndDate.setText(fbEdate);
//        tvAdminID.setText(module.getIdAdmin());
//
    }

    public void saveData() {

        btnSaveModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addAndEditModule();
//                Toast.makeText(ModuleActivity.this, "ggggggggggggggggggggggggggg", Toast.LENGTH_SHORT).show();
                check();
            }
        });
        btnBackModule.setOnClickListener(new View.OnClickListener() {
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
        dialog = new Dialog(ModuleActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.show();
        btnOKK1 = dialog.findViewById(R.id.btnSuccess);
        tvNotification1 = dialog.findViewById(R.id.textview);
        tvNotification1.setText(notifi);
        btnOKK1.setOnClickListener(new View.OnClickListener() {
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
        String notifi = tvTitle1.getText().toString();

        dialog = new Dialog(ModuleActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);
        dialog.show();
        btnOKFAIL = dialog.findViewById(R.id.btn_Error);
        tvFailed1 = dialog.findViewById(R.id.tverror);
        if (notifi.equals(Language.addClass)) {
            tvFailed1.setText(Language.AddFailed);
        } else {
            tvFailed1.setText(Language.updateFailed);
        }

        btnOKFAIL.setOnClickListener(new View.OnClickListener() {
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

    public void addAndEditModule() {
        String startTime = tvStartDate.getText().toString();
        String endTime = tvEndDate.getText().toString();
        String FBStartTime = tvFBStartDate.getText().toString();
        String FBEndTime = tvFBEndDate.getText().toString();

        String month = startTime.substring(0, 2);
        String day = startTime.substring(3, 5);
        String year = startTime.substring(6, 10);

        String month1 = endTime.substring(0, 2);
        String day1 = endTime.substring(3, 5);
        String year1 = endTime.substring(6, 10);
//
        String month2 = FBStartTime.substring(0, 2);
        String day2 = FBStartTime.substring(3, 5);
        String year2 = FBStartTime.substring(6, 10);
        String hours = FBStartTime.substring(11, 14);
        String minute = FBStartTime.substring(14, 16);
//
        String month3 = FBEndTime.substring(0, 2);
        String day3 = FBEndTime.substring(3, 5);
        String year3 = FBEndTime.substring(6, 10);
        String hours1 = FBEndTime.substring(11, 14);
        String minute1 = FBEndTime.substring(14, 16);

        String stratDate = year + "-" + month + "-" + day;
        String endDate = year1 + "-" + month1 + "-" + day1;
        String FBstartDate = year2 + "-" + month2 + "-" + day2 + "T" + hours + "" + minute + ":00";
        String FBEndDate = year3 + "-" + month3 + "-" + day3 + "T" + hours1 + "" + minute1 + ":00";

        Log.d("->>>>>>>>", stratDate);

//         Log.d("class -> class", "-----------"  + "++++++++" + "///////////" + ">>>>>>>>>"+   sdf.parse(dateInString));
//                t = y + t + d + "T" + h + m + ":00"
//        module = new Module("anh yeu cua huy","huy","2020-05-20","2020-05-20","2020-11-02T06:10:28","2020-11-02T06:10:28",2 );
        module = new Module("spnAdmin", etModuleName.getText().toString(), stratDate, endDate, FBstartDate, FBEndDate, 2);

        AppExecutors.getInstance().netWorkIO().execute(() -> {
            if (!intent.hasExtra(Constants.UPDATE_USER_ID)) {
                Call<Module> call = moduleService.postModule(module);
                call.enqueue(new Callback<Module>() {
                    @Override
                    public void onResponse(Call<Module> call, Response<Module> response) {
                        showDialogSuccess(Language.Add);
                    }

                    @Override
                    public void onFailure(Call<Module> call, Throwable t) {
                        Toast.makeText(ModuleActivity.this, t.getMessage()
                                , Toast.LENGTH_SHORT).show();
                        Log.d("yyyyy", t.getMessage());
                    }
                });
            } else {
                //Log.i("Huong", classic.getClassName() + ", " + classic.getStartTime());
                // Class2 class2 = new Class2("abc", 1, "2000-11-11", "2000-12-12");
                Call<Void> call = moduleService.updateModule(mPersonId, module);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
//                                    Toast.makeText(ClassicActivity.this, "Update user successful"
//                                            , Toast.LENGTH_SHORT).show();
                        showDialogSuccess(Language.update);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ModuleActivity.this, t.getMessage()
                                , Toast.LENGTH_SHORT).show();
                        Log.d("yyyyy", t.getMessage());
                    }
                });
            }
        });

    }

    public void check() {
        String startTime = tvStartDate.getText().toString();
        String endTime = tvEndDate.getText().toString();

        SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date nowDate = formatter.parse(String.valueOf(LocalDate.now()));
            if (etModuleName.getText().toString().isEmpty() || etModuleName.getText().toString().length() > 255) {
                showDialogFailed();
                tvCheckModuleName.setText(Language.ModuleName);
                tvCheckStartdate.setText("");
                tvCheckenddate.setText("");
                tvCheckAdmindID.setText("");
                tvCheckFBtitle.setText("");
                tvCheckFBstartdate.setText("");
                tvCheckFBenddate.setText("");
            }else if (tvStartDate.getText().toString().isEmpty()) {
                showDialogFailed();
                tvCheckStartdate.setText(Language.date);
                tvCheckenddate.setText("");
                tvCheckFBstartdate.setText("");
                tvCheckFBenddate.setText("");
            }else if (tvCheckFBstartdate.getText().toString().isEmpty()) {
                showDialogFailed();
                tvCheckFBstartdate.setText(Language.date);
                tvCheckenddate.setText("");
                tvCheckStartdate.setText("");
                tvCheckFBenddate.setText("");
            }else if (tvFBEndDate.getText().toString().isEmpty()) {
                showDialogFailed();
                tvCheckFBenddate.setText(Language.date);
                tvCheckenddate.setText("");
                tvCheckStartdate.setText("");
                tvCheckFBstartdate.setText("");
            } else if (tvEndDate.getText().toString().isEmpty()) {
                showDialogFailed();
                tvCheckenddate.setText(Language.date);
                tvCheckModuleName.setText("");
                tvCheckFBstartdate.setText("");
                tvCheckFBenddate.setText("");
                tvCheckStartdate.setText("");
            } else if (formatter1.parse(startTime).compareTo(nowDate) < 0) {
                showDialogFailed();
                tvCheckStartdate.setText(Language.date);
                tvCheckenddate.setText("");
                tvCheckModuleName.setText("");
                tvCheckFBstartdate.setText("");
                tvCheckFBenddate.setText("");
            } else if (formatter1.parse(startTime).compareTo(formatter1.parse(endTime)) == 0) {
                showDialogFailed();
                tvCheckStartdate.setText(Language.date);
                tvCheckenddate.setText(Language.date);
                tvCheckModuleName.setText("");
                tvCheckFBstartdate.setText("");
                tvCheckFBenddate.setText("");
            } else if (formatter1.parse(startTime).compareTo(formatter1.parse(endTime)) > 0) {
                showDialogFailed();
                tvCheckenddate.setText(Language.date);
                tvCheckModuleName.setText("");
                tvCheckFBstartdate.setText("");
                tvCheckStartdate.setText("");
                tvCheckFBenddate.setText("");
            } else {
                addAndEditModule();
                tvCheckModuleName.setText("");
                tvCheckStartdate.setText("");
                tvCheckenddate.setText("");
                tvCheckFBstartdate.setText("");
                tvCheckFBenddate.setText("");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void test() {
        String date = tvFBStartDate.getText().toString();
        String date1 = tvEndDate.getText().toString();
        Log.e("0001-> date", date);
    }
}
