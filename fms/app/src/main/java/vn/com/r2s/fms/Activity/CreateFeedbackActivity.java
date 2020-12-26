package vn.com.r2s.fms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.ReviewFeedbackAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.FeedbackService;
import vn.com.r2s.fms.api.TopicService;
import vn.com.r2s.fms.model.Feedback;
import vn.com.r2s.fms.model.Topic;
import vn.com.r2s.fms.model.TypeFeedback;

public class CreateFeedbackActivity extends AppCompatActivity {
    private TopicService topicService;
    private RecyclerView rcvReviewFeedback;
    private ArrayList<Topic> topiclist;
    private ReviewFeedbackAdapter reviewFeedbackAdapter;
    private Context context;
    private Button btnOKK, btn_Error;
    String name;
    private Dialog dialog;
    TextView texTview, showadminid, showtitle, reviewtitle;
    Button cfbbtnsave, cfbback;
    private long mFeedbackId;
    FeedbackService feedbackService;
    Boolean IsDeleted = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feedback);

        init();
        feedbackService = APIUtility.getFeedbackService();
        topicService = APIUtility.getTopicService();
        LinearLayoutManager layoutManager = new LinearLayoutManager(CreateFeedbackActivity.this);
        rcvReviewFeedback.setLayoutManager(layoutManager);
        topiclist = new ArrayList<>();
        name = MainActivity.username;
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        Integer type = intent.getIntExtra("type", 0);
        showtitle.setText(title);
        showadminid.setText(name);
        // get Topic
        Call<ArrayList<Topic>> call = topicService.getTopics();
        call.enqueue(new Callback<ArrayList<Topic>>() {
            @Override
            public void onResponse(Call<ArrayList<Topic>> call, Response<ArrayList<Topic>> response) {
                topiclist.clear();
                topiclist.addAll(response.body());
                reviewFeedbackAdapter = new ReviewFeedbackAdapter(context, topiclist);
                rcvReviewFeedback.setAdapter(reviewFeedbackAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Topic>> call, Throwable t) {
            }
        });

        if (intent != null && intent.hasExtra(Constants.UPDATE_FEEDBACK_ID)) {
            cfbbtnsave.setVisibility(View.VISIBLE);
            mFeedbackId = intent.getLongExtra(Constants.UPDATE_FEEDBACK_ID, -1);
        } else if (intent != null && intent.hasExtra(Constants.VIEW)) {
            reviewtitle.setText("Review Feedback");
            cfbbtnsave.setVisibility(View.INVISIBLE);
            mFeedbackId = intent.getLongExtra(Constants.VIEW, -1);
            Call<Feedback> calls = feedbackService.getFeedbackById(mFeedbackId);
            calls.enqueue(new Callback<Feedback>() {
                @Override
                public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                    Feedback feedback = response.body();
                    populateUI(feedback);
                }

                @Override
                public void onFailure(Call<Feedback> call, Throwable t) {
                    Toast.makeText(getApplication().getBaseContext(), "FeedbackActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            cfbback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            cfbbtnsave.setVisibility(View.VISIBLE);
        }
        cfbback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cfbbtnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Feedback feedback = new Feedback(type, showadminid.getText().toString(), IsDeleted, title);
                AppExecutors.getInstance().netWorkIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!intent.hasExtra(Constants.UPDATE_FEEDBACK_ID)) {
                            Call<Feedback> call = feedbackService.postFeedback(feedback);
                            call.enqueue(new Callback<Feedback>() {
                                @Override
                                public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                                    showDialogSuccess("Add Success");
                                }

                                @Override
                                public void onFailure(Call<Feedback> call, Throwable t) {
                                    showDialogError("Add Fail");
                                    Toast.makeText(CreateFeedbackActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else{
                            Call<Void> call = feedbackService.updateFeedback(mFeedbackId, feedback);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
//                            Toast.makeText(FeedbackActivity.this, "Update feedback successful", Toast.LENGTH_SHORT).show();
//                            onBackPressed();
                                    showDialogSuccess("Updated Success");
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                    Toast.makeText(CreateFeedbackActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    showDialogError("Updated Fail");
                                }
                            });
                        }



                    }
                });

            }
        });
    }

    public void init() {
        rcvReviewFeedback = findViewById(R.id.rcvReviewFeedback);
        reviewtitle = findViewById(R.id.reviewtitle);
        showadminid = findViewById(R.id.showAdminID);
        showtitle = findViewById(R.id.showTitle);
        cfbbtnsave = findViewById(R.id.cfbbtnSave);
        cfbback = findViewById(R.id.cfbbtnBack);
    }

    private void populateUI(Feedback feedback) {
        if (feedback == null) {
            return;
        }
        showtitle.setText(feedback.getTitle());
    }

    public void showDialogSuccess(String str) {
        dialog = new Dialog(CreateFeedbackActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        btnOKK = dialog.findViewById(R.id.btnSuccess);
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
        dialog = new Dialog(CreateFeedbackActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);
        btn_Error = dialog.findViewById(R.id.btn_Error);
        texTview = dialog.findViewById(R.id.tverror);
        texTview.setText(str);
        dialog.show();
        btn_Error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                onBackPressed();

            }
        });
    }

}
//    TextView showadminid, showtitle, lista, listb, listc, listd;
//    private Button  btnOKK, btn_Error;
//    private long mFeedbackId;
//    private TextView texTview, reviewtitle;
//    Button cfbbtnsave, cfbback;
//    private FeedbackService feedbackService;
//    private QuestionsService questionsService;
//    private LoginService loginService;
//    private Dialog dialog;
//    Boolean IsDeleted;
//    String name =MainActivity.username;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_feedback);
//        reviewtitle = findViewById(R.id.reviewtitle);
//        showadminid = findViewById(R.id.showAdminID);
//        showtitle = findViewById(R.id.showTitle);
//        cfbbtnsave = findViewById(R.id.cfbbtnSave);
//        cfbback = findViewById(R.id.cfbbtnBack);
//        lista = findViewById(R.id.lista);
//        listb = findViewById(R.id.listb);
//        listc = findViewById(R.id.listc);
//        listd = findViewById(R.id.listd);
//        loginService =APIUtility.getLoginService();
//        feedbackService = APIUtility.getFeedbackService();
//        questionsService = APIUtility.questionsService();
//        Intent intent = getIntent();
//        String title =intent.getStringExtra("title");
//        Integer type = intent.getIntExtra("type", 0);
//        showtitle.setText(title);
//        IsDeleted = true;
//        showadminid.setText(name);
//        if (intent != null && intent.hasExtra(Constants.UPDATE_FEEDBACK_ID)) {
//            listchecka = intent.getStringArrayListExtra("lista");
//            listcheckb = intent.getStringArrayListExtra("listb");
//            listcheckc = intent.getStringArrayListExtra("listc");
//            listcheckd = intent.getStringArrayListExtra("listd");
////            listchecka.add("- I am satisfied with the topic/course’s content (Tôi hài lòng với nội dung môn học/khóa học)" + "\n" +"- The materiati distribuced were pertiment and helpful (Tài liệu học tập phù hợp và bổ ích)");
////            listcheckb.add("- The training content is fully transferred following the courseware ( nội dung được truyền đạt đầy đủ theo giáo trình)" + "\n" + "- The trainer’s instructions were clear and understandable (giảng viên hướng dẫn rõ ràng dễ hiểu)" + "\n");
////            listcheckc.add("- The course information was communicated clearly to the trainees before the sourse start, including training calendar (time, venue). Course disciplines. Training commitment, etc (Thông tin khóa học ...)"+ "\n" + "The Class Admin was supportive and helpful (providding accourate and timelu and professtional manners) (Quản lý lớp hỗ trợ hiệu quả và nhiệt tình)" +"\n");
////            listcheckd.add("- What did you like most about topic/course? (Những điểm bạn thích nhất ở môn học/khóa học này)" + "\n" +"What aspects of the topic/source cource could be improve? ( Những điểm có thể cải tiến ở môn học/khóa học này?)" +"\n");
//
//            cfbbtnsave.setVisibility(View.VISIBLE);
//            mFeedbackId = intent.getLongExtra(Constants.UPDATE_FEEDBACK_ID, -1);
//        }
//        else if(intent != null && intent.hasExtra(Constants.VIEW)){
//            reviewtitle.setText("Review Feedback");
//            cfbbtnsave.setVisibility(View.INVISIBLE);
//            mFeedbackId = intent.getLongExtra(Constants.VIEW, -1);
//            Call<Feedback> call = feedbackService.getFeedbackById(mFeedbackId);
//            call.enqueue(new Callback<Feedback>() {
//                @Override
//                public void onResponse(Call<Feedback> call, Response<Feedback> response) {
//                    Feedback feedback = response.body();
//                    populateUI(feedback);
//                }
//
//                @Override
//                public void onFailure(Call<Feedback> call, Throwable t) {
//                    Toast.makeText(getApplication().getBaseContext(), "FeedbackActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            listchecka.add("- I am satisfied with the topic/course’s content (Tôi hài lòng với nội dung môn học/khóa học)" + "\n" +"- The materiati distribuced were pertiment and helpful (Tài liệu học tập phù hợp và bổ ích)");
//            listcheckb.add("- The training content is fully transferred following the courseware ( nội dung được truyền đạt đầy đủ theo giáo trình)" + "\n" + "- The trainer’s instructions were clear and understandable (giảng viên hướng dẫn rõ ràng dễ hiểu)" + "\n");
//            listcheckc.add("- The course information was communicated clearly to the trainees before the sourse start, including training calendar (time, venue). Course disciplines. Training commitment, etc (Thông tin khóa học ...)"+ "\n" + "The Class Admin was supportive and helpful (providding accourate and timelu and professtional manners) (Quản lý lớp hỗ trợ hiệu quả và nhiệt tình)" +"\n");
//            listcheckd.add("- What did you like most about topic/course? (Những điểm bạn thích nhất ở môn học/khóa học này)" + "\n" +"What aspects of the topic/source cource could be improve? ( Những điểm có thể cải tiến ở môn học/khóa học này?)" +"\n");
//            cfbback.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onBackPressed();
//                }
//            });
//        }
//        else {
//            listchecka = intent.getStringArrayListExtra("lista");
//            listcheckb = intent.getStringArrayListExtra("listb");
//            listcheckc = intent.getStringArrayListExtra("listc");
//            listcheckd = intent.getStringArrayListExtra("listd");
////            listchecka.add("- I am satisfied with the topic/course’s content (Tôi hài lòng với nội dung môn học/khóa học)" + "\n" +"- The material distribuced were pertiment and helpful (Tài liệu học tập phù hợp và bổ ích)");
////            listcheckb.add("- The training content is fully transferred following the courseware ( nội dung được truyền đạt đầy đủ theo giáo trình)" + "\n" + "- The trainer’s instructions were clear and understandable (giảng viên hướng dẫn rõ ràng dễ hiểu)" + "\n");
////            listcheckc.add("- The course information was communicated clearly to the trainees before the sourse start, including training calendar (time, venue). Course disciplines. Training commitment, etc (Thông tin khóa học ...)"+ "\n" + "The Class Admin was supportive and helpful (providding accourate and timelu and professtional manners) (Quản lý lớp hỗ trợ hiệu quả và nhiệt tình)" +"\n");
////            listcheckd.add("- What did you like most about topic/course? (Những điểm bạn thích nhất ở môn học/khóa học này)" + "\n" +"What aspects of the topic/source cource could be improve? ( Những điểm có thể cải tiến ở môn học/khóa học này?)" +"\n");
//
//            cfbbtnsave.setVisibility(View.VISIBLE);
//        }
//        file();
//        cfbback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//        cfbbtnsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Feedback feedback = new Feedback(type, showadminid.getText().toString(), IsDeleted, title);
//                AppExecutors.getInstance().netWorkIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (!intent.hasExtra(Constants.UPDATE_FEEDBACK_ID)) {
//                            Call<Feedback> call = feedbackService.postFeedback(feedback);
//                            call.enqueue(new Callback<Feedback>() {
//                                @Override
//                                public void onResponse(Call<Feedback> call, Response<Feedback> response) {
////                            Toast.makeText(FeedbackActivity.this, "Add Feedback successful", Toast.LENGTH_SHORT).show();
////                            onBackPressed();
//                                    showDialogSuccess("Add Success");
//                                }
//
//                                @Override
//                                public void onFailure(Call<Feedback> call, Throwable t) {
//                                    showDialogError("Add Fail");
//                                    Toast.makeText(CreateFeedbackActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//                        else{
//                            Call<Void> call = feedbackService.updateFeedback(mFeedbackId, feedback);
//                            call.enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(Call<Void> call, Response<Void> response) {
////                            Toast.makeText(FeedbackActivity.this, "Update feedback successful", Toast.LENGTH_SHORT).show();
////                            onBackPressed();
//                                    showDialogSuccess("Updated Success");
//                                }
//
//                                @Override
//                                public void onFailure(Call<Void> call, Throwable t) {
//
//                                    Toast.makeText(CreateFeedbackActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                    showDialogError("Updated Fail");
//                                }
//                            });
//                        }
//
//
//
//                    }
//                });
//
//            }
//        });
//
//
//    }
//    public void showDialogSuccess(String str) {
//        dialog = new Dialog(CreateFeedbackActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_success);
//        btnOKK = dialog.findViewById(R.id.btnSuccess) ;
//        texTview = dialog.findViewById(R.id.textview);
//        texTview.setText(str);
//        dialog.show();
//        btnOKK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//                dialog.dismiss();
//            }
//        });
//
//    }
//    public void showDialogError(String str) {
//        dialog = new Dialog(CreateFeedbackActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_error);
//        btn_Error = dialog.findViewById(R.id.btn_Error) ;
//        texTview = dialog.findViewById(R.id.tverror);
//        texTview.setText(str);
//        dialog.show();
//        btn_Error.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                onBackPressed();
//
//            }
//        });
//    }