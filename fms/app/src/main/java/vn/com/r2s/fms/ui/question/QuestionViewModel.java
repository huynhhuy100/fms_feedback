package vn.com.r2s.fms.ui.question;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.QuestionsService;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;

public class QuestionViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Question>> questionlist;
    private MutableLiveData<ArrayList<Topic>> topiclist;
    private ArrayList<Topic> topiclist_size ;
    private QuestionsService questionsService;

    public QuestionViewModel(Application application){
        super(application);
            questionsService =  APIUtility.questionsService();
    }

    public MutableLiveData<ArrayList<Question>> getQuestionlist() {
        if(questionlist == null){
            questionlist = new MutableLiveData<ArrayList<Question>>();
        }
        loadQuestion();
        return questionlist;
    }

    public MutableLiveData<ArrayList<Topic>> getTopiclist() {
        if(topiclist == null){
            topiclist = new MutableLiveData<ArrayList<Topic>>();
        }
        loadTopic();
        return topiclist;
    }

    private void loadQuestion() {
        Call<ArrayList<Question>> call = questionsService.getQuestions();
        call.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call, Response<ArrayList<Question>> response) {
                questionlist.setValue(response.body());
                Log.d("FFF",(questionlist).toString()) ;
            }
            @Override
            public void onFailure(Call<ArrayList<Question>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTopic() {
        final Call<ArrayList<Topic>> callTopic = questionsService.getTopics();
        callTopic.enqueue(new Callback<ArrayList<Topic>>() {
            @Override
            public void onResponse(Call<ArrayList<Topic>> call, Response<ArrayList<Topic>> response) {
                topiclist.setValue(response.body());
                Log.d("TTT",(response.body()).toString()) ;
            }
            @Override
            public void onFailure(Call<ArrayList<Topic>> call, Throwable t) {
                Log.d("UserViewModel: " , t.getMessage()) ;
            }
        });
    }


}

