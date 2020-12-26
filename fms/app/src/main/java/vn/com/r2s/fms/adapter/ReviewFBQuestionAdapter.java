package vn.com.r2s.fms.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.CreateFeedbackActivity;
import vn.com.r2s.fms.Activity.ReviewFeedbackActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.QuestionsService;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;

public class ReviewFBQuestionAdapter extends RecyclerView.Adapter<ReviewFBQuestionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Question> questionlist;
    private QuestionsService questionsService;
    ReviewFBQuestionAdapter reviewFBQuestionAdapter;
    private Question question ;

    public ReviewFBQuestionAdapter(Context context, ArrayList<Question> questionlist) {
        this.context = context;
        this.questionlist = questionlist;
    }

    @NonNull
    @Override
    public ReviewFBQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        questionsService = APIUtility.questionsService();
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.reviewfbquestion_item, parent, false);
        ReviewFBQuestionAdapter.ViewHolder viewHolder = new ReviewFBQuestionAdapter.ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ReviewFBQuestionAdapter.ViewHolder holder, int position) {
        holder.tvcheckbox.setText(questionlist.get(position).getQuestionContent());
        if (questionlist.get(position).getIsdeleted() == true) {
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        if (questionlist == null) {
            return 0;
        }
        return questionlist.size();
    }
    public ArrayList<Question> getTasks() {
        return questionlist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox checkBox;
        TextView tvcheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            tvcheckbox = itemView.findViewById(R.id.tvcheckbox);
            checkBox.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (checkBox.isChecked()){
                question = new Question(questionlist.get(getLayoutPosition()).getQuestionID(),questionlist.get(getLayoutPosition()).getTopicID(),questionlist.get(getLayoutPosition())
                        .getQuestionContent(),questionlist.get(getLayoutPosition()).getTopicName(),true);
                updateQuestion(questionlist.get(getLayoutPosition()).getQuestionID(),question);
//                Toast.makeText(context, listcheck.toString(), Toast.LENGTH_SHORT).show();
            }else if(!checkBox.isChecked()){
//                listcheck.remove(questionlist.get(getLayoutPosition()).getQuestionContent());
                question = new Question(questionlist.get(getLayoutPosition()).getQuestionID(),questionlist.get(getLayoutPosition()).getTopicID(),questionlist.get(getLayoutPosition())
                        .getQuestionContent(),questionlist.get(getLayoutPosition()).getTopicName(),false);
                updateQuestion(questionlist.get(getLayoutPosition()).getQuestionID(),question);

            }

        }
    }
    private void updateQuestion(int mQuestionId, Question question) {
        Call<Void> call = questionsService.updateQuestion( mQuestionId, question);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

