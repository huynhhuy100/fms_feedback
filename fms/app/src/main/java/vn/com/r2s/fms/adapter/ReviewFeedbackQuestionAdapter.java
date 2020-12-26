package vn.com.r2s.fms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.QuestionsService;
import vn.com.r2s.fms.model.Question;

public class ReviewFeedbackQuestionAdapter extends RecyclerView.Adapter<ReviewFeedbackQuestionAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Question> questionlist;
    private QuestionsService questionsService;
    private ReviewFBQuestionAdapter reviewFBQuestionAdapter;


    // 4
    public ReviewFeedbackQuestionAdapter(Context context, ArrayList<Question> questionlist){
        this.context = context;
        this.questionlist = questionlist;
    }
    @NonNull
    @Override
    public ReviewFeedbackQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        questionsService = APIUtility.questionsService();
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.reviewfbquestion_item_1, parent, false);
        ReviewFeedbackQuestionAdapter.ViewHolder viewHolder = new ReviewFeedbackQuestionAdapter.ViewHolder(view);
        return viewHolder;
    }

    public ArrayList<Question> getTasks() {
        return (ArrayList<Question>) questionlist;
    }


    @Override
    public void onBindViewHolder(@NonNull ReviewFeedbackQuestionAdapter.ViewHolder holder, int position) {
        holder.tvcheckbox.setText(questionlist.get(position).getQuestionContent());
    }

    @Override
    public int getItemCount() {
        if (questionlist==null){
            return 0;
        }
        return questionlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView tvcheckbox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvcheckbox = itemView.findViewById(R.id.tvcheckbox);
        }
    }
}
