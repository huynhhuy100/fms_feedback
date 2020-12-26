package vn.com.r2s.fms.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.ReviewFBQuestionAdapter;
import vn.com.r2s.fms.adapter.ReviewFBTopicAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.FeedbackService;
import vn.com.r2s.fms.api.QuestionsService;
import vn.com.r2s.fms.api.TopicService;
import vn.com.r2s.fms.api.TypeFeedbackService;
import vn.com.r2s.fms.model.Feedback;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;
import vn.com.r2s.fms.model.TypeFeedback;

public class ReviewFeedbackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner rfbspntype;
    EditText rfbedttitle;
    Button rfbbtnreview, rfbbtnreviewback;
    private TypeFeedbackService typeFeedbackService;
    TextView tvTitle;
    String TypeName;
    private FeedbackService feedbackService;
    private long mFeedbackId;
    Intent intent;
    private QuestionsService questionsService;
    private TopicService topicService;
    Context context;
    RecyclerView rv_topic;
    private boolean IsDeleted;
    ReviewFBTopicAdapter reviewFBTopicAdapter;
    ReviewFBQuestionAdapter reviewFBQuestionAdapter;
    private ArrayList<Topic> topiclist;
    private ArrayList<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewfeedback);
        init();
        typeFeedbackService = APIUtility.getTypeFeedbackService();
        feedbackService = APIUtility.getFeedbackService();
        topicService = APIUtility.getTopicService();
        questionsService = APIUtility.questionsService();
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReviewFeedbackActivity.this);
        rv_topic.setLayoutManager(layoutManager);
        topiclist = new ArrayList<>();
        questionList = new ArrayList<Question>();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_FEEDBACK_ID)) {
            mFeedbackId = intent.getLongExtra(Constants.UPDATE_FEEDBACK_ID, -1);
            tvTitle.setText("Edit New Feedback");
        }
        Call<Feedback> calls = feedbackService.getFeedbackById(mFeedbackId);
        calls.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                populateUI(feedback);
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
            }
        });

        Call<ArrayList<TypeFeedback>> call = typeFeedbackService.getTFB();
        call.enqueue(new Callback<ArrayList<TypeFeedback>>() {
            @Override
            public void onResponse(Call<ArrayList<TypeFeedback>> call, Response<ArrayList<TypeFeedback>> response) {
                ArrayList<TypeFeedback> typeFeedback = response.body();
                ArrayList<Integer> tentllist = new ArrayList<>();
                tentllist.clear();
                populateUIs(typeFeedback);
                for (int i = 0; i < typeFeedback.size(); i++) {
                    tentllist.add(typeFeedback.get(i).getTypeId());
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, tentllist);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    rfbspntype.setAdapter(adapter);
                    rfbspntype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            TypeName = typeFeedback.get(i).getTypeName();
                            Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + TypeName, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TypeFeedback>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "FeedbackActivity-type: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<ArrayList<Topic>> callss = topicService.getTopics();
        callss.enqueue(new Callback<ArrayList<Topic>>() {
            @Override
            public void onResponse(Call<ArrayList<Topic>> call, Response<ArrayList<Topic>> response) {
                topiclist.clear();
                topiclist.addAll(response.body());
                reviewFBTopicAdapter = new ReviewFBTopicAdapter(context, topiclist);
                rv_topic.setAdapter(reviewFBTopicAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Topic>> call, Throwable t) {
            }
        });


        buton();
    }

    public void init() {
        rfbspntype = findViewById(R.id.rfbspntype);
        rfbedttitle = findViewById(R.id.rfbedttitle);
        rfbbtnreview = findViewById(R.id.rfbbtnSave);
        rfbbtnreviewback = findViewById(R.id.rfbbtnCancel);
        rv_topic = findViewById(R.id.rv_topic);
        rfbspntype.setOnItemSelectedListener(this);
        tvTitle = findViewById(R.id.tvTitle);


    }
    private void populateUIs(ArrayList<TypeFeedback> typeFeedback) {
        if (typeFeedback == null) {
            return;
        }
    }
    public void buton() {
        rfbbtnreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long elementId = mFeedbackId;
                Intent intents = getIntent();
                String Title = String.valueOf(rfbedttitle.getText());
                Integer typeid = (Integer) rfbspntype.getSelectedItem();
                if (Title.equalsIgnoreCase("")) {
                    Toast.makeText(ReviewFeedbackActivity.this, "Please check again your title", Toast.LENGTH_SHORT).show();
                } else if (intents != null && intents.hasExtra(Constants.UPDATE_FEEDBACK_ID)) {

                    Intent intent = new Intent(ReviewFeedbackActivity.this, CreateFeedbackActivity.class);
                    intent.putExtra("title", Title);
                    intent.putExtra("type", typeid);
                    intent.putExtra(Constants.UPDATE_FEEDBACK_ID, elementId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ReviewFeedbackActivity.this, CreateFeedbackActivity.class);
                    intent.putExtra("title", Title);
                    intent.putExtra("type", typeid);
                    startActivity(intent);
                }


            }
        });
        rfbbtnreviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void populateUI(Feedback feedback) {
        if (feedback == null) {
            return;
        }
        rfbedttitle.setText(feedback.getTitle());
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}