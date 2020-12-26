package vn.com.r2s.fms;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.LoginActivity;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.JoinService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.TraineeAssignment;

public class MainActivity extends AppCompatActivity {
     Button btnCancel, btnJoin;
    EditText edtTyping;
    Dialog dialog;
    TextView tvSuccess,tvinvalid,tvtry;
    Button
            btnOKK,
            btnOkFail;

    private AppBarConfiguration mAppBarConfiguration;
    private Button btn_cencel1, btn_logout;
    public static String typeUser = " ";
    public static String username = " ";
    private ArrayList<Assignment> assignmentArrayList;

    private JoinService joinService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_assignment,R.id.nav_class,R.id.nav_module, R.id.nav_enrollment,
                R.id.nav_feedback,R.id.nav_traineefeedback, R.id.nav_result, R.id.nav_result, R.id.nav_question, R.id.nav_contact, R.id.nav_logout,R.id.nav_join)
                .setDrawerLayout(drawer)
                .build();
                
     //join
        navigationView.getMenu().findItem(R.id.nav_join).setOnMenuItemClickListener(menuItem -> {
            dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.fragment_join);
            dialog.show();
            btnJoin = dialog.findViewById(R.id.btn_Sumbit);
            edtTyping = dialog.findViewById(R.id.edt_Typing);
            btnCancel = dialog.findViewById(R.id.btn_Cancel);
            edtTyping.setText("");
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    dialog.dismiss();

                }
            });

            btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JoinService joinService = APIUtility.gettraineeAllAssignment();
                    AssignmentService asmService = APIUtility.getAssignmentService();

                    String edTyping = edtTyping.getText().toString();

                    TraineeAssignment traineeAsm = new TraineeAssignment();
                    traineeAsm.setTraineeId(MainActivity.username);
                    traineeAsm.setRegistrationCode(edTyping);

                    Call<TraineeAssignment> calls = joinService.postaddTraineeAssignment(traineeAsm);
                    calls.enqueue(new Callback<TraineeAssignment>() {
                        @Override
                        public void onResponse(Call<TraineeAssignment> call, Response<TraineeAssignment> response) {
                            showDialogJoinSuccess(Language.Add);
                        }

                        @Override
                        public void onFailure(Call<TraineeAssignment> call, Throwable t) {
                            showDialogjoinFailed();
                        }
                    });
                }
            });



            return true;
        });

//Logout
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
             dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_logout);
            dialog.show();
            btn_logout = dialog.findViewById(R.id.btn_yes);
            btn_cencel1 = dialog.findViewById(R.id.btn_cencel1);
            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    Toast.makeText(MainActivity.this, Language.logout + "  " + typeUser, Toast.LENGTH_SHORT).show();
                }
            });
            btn_cencel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            return true;    
        });
//Set Drawer LoginService
        Menu nav_menu = navigationView.getMenu();

        Intent intent = getIntent();
        typeUser = intent.getExtras().getString("typeUser");
        username = intent.getExtras().getString("userName");
// Log.d("typeUser: ", typeUser);
       if (typeUser.equals(Language.trainer)) {
            nav_menu.findItem(R.id.nav_enrollment).setVisible(false);
            nav_menu.findItem(R.id.nav_feedback).setVisible(false);
            nav_menu.findItem(R.id.nav_question).setVisible(false);
            nav_menu.findItem(R.id.nav_traineefeedback).setVisible(false);
        } else if(typeUser.equals(Language.admin)) {
            nav_menu.findItem(R.id.nav_traineefeedback).setVisible(false);
        }
        Toast.makeText(this, typeUser + " Logged in successfully", Toast.LENGTH_SHORT).show();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
     //join
     public void showDialogJoinSuccess(String notifi) {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_joinsuccess);
        dialog.show();
        btnOKK = dialog.findViewById(R.id.btnOKK);
        tvSuccess = dialog.findViewById(R.id.tvSuccess);
        tvSuccess.setText(notifi);
        btnOKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });
    }

    public void showDialogjoinFailed() {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_joininvalid);
        dialog.show();
        btnOkFail = dialog.findViewById(R.id.btnOKK);
        tvinvalid = dialog.findViewById(R.id.tvinvalid);
        btnOkFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
    }

    public void showDialogjoinExist() {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tryanother);
        dialog.show();
        btnOkFail = dialog.findViewById(R.id.btnOKK);
        tvtry = dialog.findViewById(R.id.tvtry);
        tvtry.setText("You already join  this module, please try another!!!");
        btnOkFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });
    }
}