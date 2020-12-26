package vn.com.r2s.fms.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.QuestionsService;
import vn.com.r2s.fms.api.TopicService;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;

public class ReviewFeedbackAdapter extends RecyclerView.Adapter<ReviewFeedbackAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private Context context;
    private ArrayList<Topic> topiclist;
    private ArrayList<Question> questionList;
    private ArrayList<Question> questionlistQuestionContent;
    private ArrayList<Question> getQuestionlistQuestionContent ;
    private TopicService topicService;
    private QuestionsService questionsService;
    private ReviewFeedbackQuestionAdapter reviewFeedbackQuestionAdapter;
    private ReviewFeedbackAdapter reviewFBTopicAdapter;
    private ArrayList<String> listchecked ;


    // 3

    public ReviewFeedbackAdapter(Context context, ArrayList<Topic> topiclist) {
        this.context = context;
        this.topiclist = topiclist;
    }


    // anh xa va gan adapter
    @NonNull
    @Override
    public ReviewFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        topicService = APIUtility.getTopicService();
        questionsService = APIUtility.questionsService();
        context = parent.getContext();
        questionList = new ArrayList<>();
        questionlistQuestionContent = new ArrayList<>();
        getQuestionlistQuestionContent = new ArrayList<>() ;



        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.reviewfbtopic_item_1, parent, false);
        ReviewFeedbackAdapter.ViewHolder viewHolder = new ReviewFeedbackAdapter.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ReviewFeedbackAdapter.ViewHolder holder, int position) {
        // get Question

        Log.d("hello", String.valueOf(position));
        Call<ArrayList<Question>> call1 = questionsService.getQuestions();
        call1.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call1, Response<ArrayList<Question>> response) {
                getQuestionlistQuestionContent.clear();
                questionList.clear();
                questionList.addAll(response.body());
                holder.tvtopicname.setText(topiclist.get(position).gettopicName());

                for (Question question : questionList) {
                    if (question.getTopicID() == (position + 1) && question.getIsdeleted() == true) {
                        getQuestionlistQuestionContent.add(question);
                    }
                }  Log.d("questionList", getQuestionlistQuestionContent.toString());

                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recycle_reviewquestion.getContext(), LinearLayoutManager.VERTICAL, false);
                layoutManager.setInitialPrefetchItemCount(getQuestionlistQuestionContent.size());
                reviewFeedbackQuestionAdapter = new ReviewFeedbackQuestionAdapter(context,getQuestionlistQuestionContent);
                holder.recycle_reviewquestion.setLayoutManager(layoutManager);
                holder.recycle_reviewquestion.setAdapter(reviewFeedbackQuestionAdapter);
                holder.recycle_reviewquestion.setRecycledViewPool(viewPool);
            }

            @Override
            public void onFailure(Call<ArrayList<Question>> call1, Throwable t) {
                Log.d("Error","UserViewModel: " + t.getMessage());
            }
        });
    }

    // tra ve topiclist.size
    @Override
    public int getItemCount() {
        if (topiclist == null) {
            return 0;
        } else {
            Log.d("AAA", String.valueOf(topiclist.size())) ;
            return topiclist.size(); }
    }

    // khai bao va anh xa
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvtopicname;
        private RecyclerView recycle_reviewquestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtopicname = itemView.findViewById(R.id.tvtopicname);
            recycle_reviewquestion = itemView.findViewById(R.id.recycle_reviewquestion);

        }

    }
}
