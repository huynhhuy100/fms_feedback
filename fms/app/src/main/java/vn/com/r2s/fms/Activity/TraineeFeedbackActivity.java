package vn.com.r2s.fms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AnswerService;
import vn.com.r2s.fms.api.CommentService;
import vn.com.r2s.fms.model.Answer;
import vn.com.r2s.fms.model.Comment;
import vn.com.r2s.fms.model.Feedback;

public class TraineeFeedbackActivity extends AppCompatActivity {
    TextView tvtraineemodulename, tvtraineeclassname, tvtraineeusername, tvtraineecomment;
    Button tvtraineesubmit;
    RadioButton strdis1, strdis2, strdis3, strdis4, strdis5, strdis6, dis1,
            dis2, dis3, dis4, dis5, dis6, undec1, undec2, undec3, undec4, undec5, undec6,
            agr1, agr2, agr3, agr4, agr5, agr6, stragr1, stragr2, stragr3, stragr4, stragr5, stragr6;
    RadioGroup radiogr1, radiogr2, radiogr3, radiogr4, radiogr5, radiogr6;
    int count1, count2, count3, count4, count5;
    AnswerService answerService;
    CommentService commentService;
    String comment1;
    String traineeid;
    Dialog dialog;
    Button  btnOKK, btn_Error;
    TextView texTview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_feedback);
        init();

        answerService = APIUtility.getAnswerService();
        commentService =APIUtility.getCommentService();
        Intent intent = getIntent();
        String classname = intent.getStringExtra("classname");
        tvtraineeclassname.setText("Class: " + classname);
        String modulename = intent.getStringExtra("modulename");
        tvtraineemodulename.setText("Module: " + modulename);
        int classisId = intent.getIntExtra("classisId", 0);
        int moduleId = intent.getIntExtra("moduleId", 0);
        traineeid = MainActivity.username;
        tvtraineeusername.setText(traineeid);
        String abc = tvtraineecomment.getText().toString();
        radiogr1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.strongdisagree1:
                        count1 = count1 + 1;
                        break;
                    case R.id.disagree1:
                        count2 = count2 + 1;
                        break;
                    case R.id.undecided1:
                        count3 = count3 + 1;
                        break;
                    case R.id.agree1:
                        count4 = count4 + 1;
                        break;
                    case R.id.strongagree1:
                        count5 = count5 + 1;
                        break;
                }
            }
        });
        radiogr2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.strongdisagree2:
                        count1 = count1 + 1;
                        break;
                    case R.id.disagree2:
                        count2 = count2 + 1;
                        break;
                    case R.id.undecided2:
                        count3 = count3 + 1;
                        break;
                    case R.id.agree2:
                        count4 = count4 + 1;
                        break;
                    case R.id.strongagree2:
                        count5 = count5 + 1;
                        break;
                }
            }
        });
        radiogr3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.strongdisagree3:
                        count1 = count1 + 1;
                        break;
                    case R.id.disagree3:
                        count2 = count2 + 1;
                        break;
                    case R.id.undecided3:
                        count3 = count3 + 1;
                        break;
                    case R.id.agree3:
                        count4 = count4 + 1;
                        break;
                    case R.id.strongagree3:
                        count5 = count5 + 1;
                        break;
                }
            }
        });
        radiogr4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.strongdisagree4:
                        count1 = count1 + 1;
                        break;
                    case R.id.disagree4:
                        count2 = count2 + 1;
                        break;
                    case R.id.undecided4:
                        count3 = count3 + 1;
                        break;
                    case R.id.agree4:
                        count4 = count4 + 1;
                        break;
                    case R.id.strongagree4:
                        count5 = count5 + 1;
                        break;
                }
            }
        });
        radiogr5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.strongdisagree5:
                        count1 = count1 + 1;
                        break;
                    case R.id.disagree5:
                        count2 = count2 + 1;
                        break;
                    case R.id.undecided5:
                        count3 = count3 + 1;
                        break;
                    case R.id.agree5:
                        count4 = count4 + 1;
                        break;
                    case R.id.strongagree5:
                        count5 = count5 + 1;
                        break;
                }
            }
        });
        radiogr6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.strongdisagree6:
                        count1 = count1 + 1;
                        break;
                    case R.id.disagree6:
                        count2 = count2 + 1;
                        break;
                    case R.id.undecided6:
                        count3 = count3 + 1;
                        break;
                    case R.id.agree6:
                        count4 = count4 + 1;
                        break;
                    case R.id.strongagree6:
                        count5 = count5 + 1;
                        break;
                }
            }
        });

        tvtraineesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (abc.isEmpty()) {
                    showDialogError("Check again your comment!");
                }else if(!strdis1.isChecked()&&!dis1.isChecked()&&!undec1.isChecked()&&!agr1.isChecked()&&!stragr1.isChecked()){
                    showDialogError("Check again your answer!");
                }else if(!strdis2.isChecked()&&!dis2.isChecked()&&!undec2.isChecked()&&!agr2.isChecked()&&!stragr2.isChecked()){
                    showDialogError("Check again your answer!");
                }else if(!strdis3.isChecked()&&!dis3.isChecked()&&!undec3.isChecked()&&!agr3.isChecked()&&!stragr3.isChecked()){
                    showDialogError("Check again your answer!");
                }else if(!strdis4.isChecked()&&!dis4.isChecked()&&!undec4.isChecked()&&!agr4.isChecked()&&!stragr4.isChecked()){
                    showDialogError("Check again your answer!");
                }else if(!strdis5.isChecked()&&!dis5.isChecked()&&!undec5.isChecked()&&!agr5.isChecked()&&!stragr5.isChecked()){
                    showDialogError("Check again your answer!");
                }else if(!strdis6.isChecked()&&!dis6.isChecked()&&!undec6.isChecked()&&!agr6.isChecked()&&!stragr6.isChecked()){
                    showDialogError("Check again your answer!");
                }else {
                    AppExecutors.getInstance().netWorkIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            comment1 = tvtraineecomment.getText().toString();
                            final Comment comment = new Comment(classisId, traineeid, moduleId,comment1);
                            Call<Comment> call = commentService.postComment(comment);
                            call.enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {
                                }
                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                }
                            });

                        }
                    });
                    if(count1>0){
                        AppExecutors.getInstance().netWorkIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                final Answer answer = new Answer(classisId, moduleId, 1, count1, traineeid);
                                Call<Answer> call = answerService.postAnswer(answer);
                                call.enqueue(new Callback<Answer>() {
                                    @Override
                                    public void onResponse(Call<Answer> call, Response<Answer> response) {
                                    }
                                    @Override
                                    public void onFailure(Call<Answer> call, Throwable t) {
                                    }
                                });

                            }
                        });

                    }
                    if(count2>0){
                        AppExecutors.getInstance().netWorkIO().execute(new Runnable() {
                            @Override
                            public void run() {

                                Answer answer = new Answer(classisId, moduleId, 3, count2, traineeid);
                                Call<Answer> calls = answerService.postAnswer(answer);
                                calls.enqueue(new Callback<Answer>() {
                                    @Override
                                    public void onResponse(Call<Answer> call, Response<Answer> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Answer> call, Throwable t) {
                                    }
                                });

                            }
                        });
                    }
                    if(count3>0){
                        AppExecutors.getInstance().netWorkIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                Answer answer = new Answer(classisId, moduleId, 4, count3, traineeid);
                                Call<Answer> callss = answerService.postAnswer(answer);
                                callss.enqueue(new Callback<Answer>() {
                                    @Override
                                    public void onResponse(Call<Answer> call, Response<Answer> response) {
                                    }

                                    @Override
                                    public void onFailure(Call<Answer> call, Throwable t) {
                                    }
                                });

                            }
                        });
                    }
                    if(count4>0){
                        AppExecutors.getInstance().netWorkIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                Answer answer = new Answer(classisId, moduleId, 7, count4, traineeid);
                                Call<Answer> calll = answerService.postAnswer(answer);
                                calll.enqueue(new Callback<Answer>() {
                                    @Override
                                    public void onResponse(Call<Answer> call, Response<Answer> response) {
                                    }

                                    @Override
                                    public void onFailure(Call<Answer> call, Throwable t) {
                                        Toast.makeText(TraineeFeedbackActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                    if(count5>0){
                        AppExecutors.getInstance().netWorkIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                Answer answer = new Answer(classisId, moduleId, 12, count5, traineeid);
                                Call<Answer> calll = answerService.postAnswer(answer);
                                calll.enqueue(new Callback<Answer>() {
                                    @Override
                                    public void onResponse(Call<Answer> call, Response<Answer> response) {
                                        showDialogSuccess("Feedback Success");
                                    }

                                    @Override
                                    public void onFailure(Call<Answer> call, Throwable t) {
                                        Toast.makeText(TraineeFeedbackActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                }

            }


        });
    }
    public void init() {
        tvtraineemodulename = findViewById(R.id.tvtraineemodulename);
        tvtraineeclassname = findViewById(R.id.tvtraineeclassname);
        tvtraineeusername = findViewById(R.id.tvtraineeusername);
        tvtraineecomment = findViewById(R.id.tvtraineecomment);
        tvtraineesubmit = findViewById(R.id.tvtraineesubmit);
//        recycle_traineefb = findViewById(R.id.recycle_traineefb);
        radiogr1 = findViewById(R.id.radiogr1);
        radiogr2 = findViewById(R.id.radiogr2);
        radiogr3 = findViewById(R.id.radiogr3);
        radiogr4 = findViewById(R.id.radiogr4);
        radiogr5 = findViewById(R.id.radiogr5);
        radiogr6 = findViewById(R.id.radiogr6);
        strdis1 = findViewById(R.id.strongdisagree1);
        strdis2 = findViewById(R.id.strongdisagree2);
        strdis3 = findViewById(R.id.strongdisagree3);
        strdis4 = findViewById(R.id.strongdisagree4);
        strdis5 = findViewById(R.id.strongdisagree5);
        strdis6 = findViewById(R.id.strongdisagree6);
        dis1 = findViewById(R.id.disagree1);
        dis2 = findViewById(R.id.disagree2);
        dis3 = findViewById(R.id.disagree3);
        dis4 = findViewById(R.id.disagree4);
        dis5 = findViewById(R.id.disagree5);
        dis6 = findViewById(R.id.disagree6);
        undec1 = findViewById(R.id.undecided1);
        undec2 = findViewById(R.id.undecided2);
        undec3 = findViewById(R.id.undecided3);
        undec4 = findViewById(R.id.undecided4);
        undec5 = findViewById(R.id.undecided5);
        undec6 = findViewById(R.id.undecided6);
        agr1 = findViewById(R.id.agree1);
        agr2 = findViewById(R.id.agree2);
        agr3 = findViewById(R.id.agree3);
        agr4 = findViewById(R.id.agree4);
        agr5 = findViewById(R.id.agree5);
        agr6 = findViewById(R.id.agree6);
        stragr1 = findViewById(R.id.strongagree1);
        stragr2 = findViewById(R.id.strongagree2);
        stragr3 = findViewById(R.id.strongagree3);
        stragr4 = findViewById(R.id.strongagree4);
        stragr5 = findViewById(R.id.strongagree5);
        stragr6 = findViewById(R.id.strongagree6);
    }
    public void showDialogSuccess(String str) {
        dialog = new Dialog(TraineeFeedbackActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        btnOKK = dialog.findViewById(R.id.btnSuccess) ;
        texTview = dialog.findViewById(R.id.textview);
        texTview.setText(str);
        dialog.show();
        btnOKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                dialog.dismiss();
            }
        });

    }
    public void showDialogError(String str) {
        dialog = new Dialog(TraineeFeedbackActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);
        btn_Error = dialog.findViewById(R.id.btn_Error) ;
        texTview = dialog.findViewById(R.id.tverror);
        texTview.setText(str);
        dialog.show();
        btn_Error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


}
