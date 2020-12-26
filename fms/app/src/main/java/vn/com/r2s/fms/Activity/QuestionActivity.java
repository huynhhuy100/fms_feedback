package vn.com.r2s.fms.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.TopicAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.QuestionsService;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;
import vn.com.r2s.fms.ui.question.QuestionViewModel;

public class QuestionActivity extends AppCompatActivity  {
    private Button btnOk, btnCancle, btnSuccess, btn_Error ;
    private EditText edt_QuestionContent ;
    private TextView tvTitle ;
    private TextView tvName ;
    private int mQuestionId;
    private Intent intent;
    private QuestionsService questionsService;
    private Dialog dialog;
    private Spinner spinner ;
    private TopicAdapter topicAdapter ;
    private ArrayList<Topic> dataTopic ;
    private QuestionViewModel questionViewModel ;
    private String topic_name ;
    private int mIDtopic_name ;
    private int topic_id = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initViews() {
        spinner = findViewById(R.id.spinnerTopic) ;
        customSpinner() ;
        tvTitle = findViewById(R.id.tvTitle) ;
        tvName = findViewById(R.id.tvName) ;
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class) ;
        btnOk = findViewById(R.id.btnOk) ;
        btnCancle = findViewById(R.id.btn_Cancel) ;
        edt_QuestionContent = findViewById(R.id.edt_QuestionContent) ;
        getData();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // test editText
                constraint();

            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    // getData
    private void getData() {
        questionsService = APIUtility.questionsService();
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_QUESTION_ID) && intent.hasExtra(Constants.UPDATE_TOPIC_ID)) {
            btnOk.setText("Update");
            tvTitle.setText("Update Question");
            mQuestionId = intent.getIntExtra(Constants.UPDATE_QUESTION_ID, 1);
            mIDtopic_name = intent.getIntExtra(Constants.UPDATE_TOPIC_ID,1);
        }
        // Get Question
        Call<Question> call = questionsService.getQuestionById(mQuestionId);
        call.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                Question question = response.body();
                populateUI(question);
            }
            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // Get Topic Name
        Call<Topic> callTopic = questionsService.getTopicsById(mIDtopic_name);
        callTopic.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(Call<Topic> callTopic, Response<Topic> response) {
                Topic topic = response.body();
                populateUITopic(topic) ;
            }
            @Override
            public void onFailure(Call<Topic> callTopic, Throwable t) {
//                Toast.makeText(this, "UserActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // load UI && TOPIC
    private void populateUI(Question question) {
        if (question == null) {
            return;
        }
        spinner.setVisibility(View.GONE);
        edt_QuestionContent.setText(question.getQuestionContent());
    }

    private void populateUITopic(Topic topic) {
        if (topic == null) {
            return;
        }
        Log.d("question",topic.gettopicName());
        tvName.setText(topic.gettopicName());
        topic_id = topic.getTopicID() ;
    }

    public void onSaveButtonClick() {
        final Question question ;
        question = new Question(edt_QuestionContent.getText().toString(), topic_id );
        AppExecutors.getInstance().netWorkIO().execute(new Runnable(){
            @Override
            public void run(){
                // postQuestion
                Log.d("question" , "postQuestion") ;
                if (!intent.hasExtra(Constants.UPDATE_QUESTION_ID) || !intent.hasExtra(Constants.UPDATE_TOPIC_ID)) {
                    Call<Question> call = questionsService.postQuestion(question);
                    call.enqueue(new Callback<Question>() {
                        @Override
                        public void onResponse(Call<Question> call, Response<Question> response) {
                            dialogSuccess();
                        }
                        @Override
                        public void onFailure(Call<Question> call, Throwable t) {
                            dialogError() ;
                        }
                    });
                //  updateQuestion
                }else {
                    Log.d("question" , "updateQuestion") ;
                    Call<Void> call = questionsService.updateQuestion(mQuestionId, question);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(QuestionActivity.this,"Update question successful", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(QuestionActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void dialogSuccess() {
        dialog = new Dialog(QuestionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        dialog.show();
        btnSuccess = dialog.findViewById(R.id.btnSuccess) ;
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionActivity.this, "ok", Toast.LENGTH_SHORT).show();
                // do it
                dialog.dismiss();
                finish();
            }
        });
    }

    private void dialogError() {
        dialog = new Dialog(QuestionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);
        dialog.show();
        btn_Error = dialog.findViewById(R.id.btn_Error) ;
        btn_Error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionActivity.this, "ok", Toast.LENGTH_SHORT).show();
                // do it
                dialog.dismiss();
                finish();
            }
        });
    }

    private void customSpinner() {
        dataTopic = new ArrayList<>();
        topicAdapter = new TopicAdapter(getApplicationContext(),R.layout.topic_item,dataTopic );
        spinner.setAdapter(topicAdapter);
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), dataTopic.get(position).getTopicID().toString(), Toast.LENGTH_SHORT).show();
                topic_id = dataTopic.get(position).getTopicID() ;
                topic_name = dataTopic.get(position).gettopicName() ;
                Log.d("dataTopic", topic_name) ;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("dataTopic","error") ;
            }
        } );


    }

    // load data
    public void retrieveTasks(){
        // TOPIC -
        // ViewModel là một lớp được sử dụng để lưu trữ và quản lý dữ liệu liên quan đến UI
        questionViewModel.getTopiclist().observe(this, new Observer<ArrayList<Topic>>() {
            @Override
            public void onChanged(ArrayList<Topic> topics) {
                dataTopic.clear();
                dataTopic.addAll(topics);
                topicAdapter.notifyDataSetChanged();
            }
        });
    }

    // rang buoc
    private void constraint() {
        if (edt_QuestionContent.length() == 0) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            onSaveButtonClick();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        retrieveTasks();
    }
}
