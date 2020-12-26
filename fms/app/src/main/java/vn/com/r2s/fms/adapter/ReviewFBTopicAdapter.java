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

public class ReviewFBTopicAdapter extends RecyclerView.Adapter<ReviewFBTopicAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private Context context;
    private ArrayList<Topic> topiclist;
    private ArrayList<Question> questionList;
    private ArrayList<Question> questionlistQuestionContent;
    private ArrayList<Question> getQuestionlistQuestionContent ;
    private TopicService topicService;
    private QuestionsService questionsService;
    private ReviewFBQuestionAdapter reviewFBQuestionAdapter;
    private ReviewFBTopicAdapter reviewFBTopicAdapter;



    public ReviewFBTopicAdapter(Context context, ArrayList<Topic> topiclist) {
        this.context = context;
        this.topiclist = topiclist;
    }

    // anh xa va gan adapter
    @NonNull
    @Override
    public ReviewFBTopicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        topicService = APIUtility.getTopicService();
        questionsService = APIUtility.questionsService();
        context = parent.getContext();
        questionList = new ArrayList<>();
        questionlistQuestionContent = new ArrayList<>();
        getQuestionlistQuestionContent = new ArrayList<>() ;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.reviewfbtopic_item, parent, false);
        ReviewFBTopicAdapter.ViewHolder viewHolder = new ReviewFBTopicAdapter.ViewHolder(view);
        return viewHolder;
    }

    // getData - onBind: mỗi cái item thì nó sẽ gọi một lần (positon + 1 )
    @Override
    public void onBindViewHolder(@NonNull ReviewFBTopicAdapter.ViewHolder holder, int position) {
        // get Question
        holder.tvtopicname.setText(topiclist.get(position).gettopicName());

        Call<ArrayList<Question>> call1 = questionsService.getQuestions();
        call1.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> call1, Response<ArrayList<Question>> response) {
                getQuestionlistQuestionContent.clear();
                questionList.clear();
                questionList.addAll(response.body());
                for (Question question : questionList) {
                    if (question.getTopicID() == (position + 1)) {
                        getQuestionlistQuestionContent.add(question);
                    }
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recycle_reviewquestion.getContext(), LinearLayoutManager.VERTICAL, false);
                layoutManager.setInitialPrefetchItemCount(getQuestionlistQuestionContent.size());
                reviewFBQuestionAdapter = new ReviewFBQuestionAdapter(context,getQuestionlistQuestionContent);
                holder.recycle_reviewquestion.setLayoutManager(layoutManager);
                holder.recycle_reviewquestion.setAdapter(reviewFBQuestionAdapter);
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
