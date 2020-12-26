package vn.com.r2s.fms.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.QuestionActivity;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.QuestionsService;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;
import vn.com.r2s.fms.ui.question.QuestionFragment;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Dialog dialog ;
    private Button btnOk, btnCancel;
    private Context context;
    private ArrayList<Question> questionList;
    private QuestionsService questionsService;
    static String mtopicName ;
    private Fragment fragment ;

    public QuestionAdapter(Context context, ArrayList<Question> questionList) {
        this.questionList = questionList;
        this.context = context;
    }

    public QuestionAdapter(Context context, ArrayList<Question> questionList, QuestionFragment questionFragment) {
        this.questionList = questionList;
        this.fragment=questionFragment;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        questionsService = APIUtility.questionsService();
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =  layoutInflater.inflate(R.layout.question_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.tvTopicID.setText(String.valueOf(questionList.get(i).getTopicID()));
        Call<Topic> call = questionsService.getTopicsById(questionList.get(i).getTopicID());
        call.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(Call<Topic> call, Response<Topic> response) {
                Topic topic = response.body();
                mtopicName = topic.gettopicName();
                Log.d("gettopicname",mtopicName) ;
                holder.tvTopicName.setText(mtopicName);
            }

            @Override
            public void onFailure(Call<Topic> call, Throwable t) {
                Toast.makeText(context, "UserActivity: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        Log.d("gettopicname", mtopicName) ;

        holder.tvQuestionID.setText(String.valueOf(questionList.get(i).getQuestionID()));
        holder.tvQuestionContent.setText(questionList.get(i).getQuestionContent());
    }

    @Override
    public int getItemCount() {
        if (questionList==null){
            return 0;
        }
        return questionList.size();
    }

    public ArrayList<Question> getTasks(){return questionList;}

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvQuestionID;
        TextView tvTopicName;
        TextView tvTopicID;
        TextView tvQuestionContent;
        Button imgEditQuestion, imgDeleteQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQuestionID = itemView.findViewById(R.id.tvQuestionID);
            tvTopicID = itemView.findViewById(R.id.tvTopicID);
            tvQuestionContent = itemView.findViewById(R.id.tvQuestionContent);
            tvTopicName = itemView.findViewById(R.id.tvTopicName);
            imgEditQuestion = itemView.findViewById(R.id.imgEditQuestion);
            imgDeleteQuestion = itemView.findViewById(R.id.imgDeleteQuestion);

           imgEditQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = questionList.get(getAdapterPosition()).getQuestionID();
                    int elementIDTopic = questionList.get(getAdapterPosition()).getTopicID();
                    Log.d("ABC", String.valueOf(elementIDTopic)) ;
                    Intent i = new Intent(context, QuestionActivity.class);
                    i.putExtra(Constants.UPDATE_QUESTION_ID,elementId);
                    i.putExtra(Constants.UPDATE_TOPIC_ID,elementIDTopic);
                    context.startActivity(i);
                }
            });

            imgDeleteQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getAdapterPosition() ;
                    dialogDelete(id);
                }
            });

        }
    }

    public void dialogDelete(final int idd) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.show();
        btnOk = dialog.findViewById(R.id.btnOk) ;
        btnCancel = dialog.findViewById(R.id.btn_Cancel) ;
        btnOk.setOnClickListener(   new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mQuestionId = questionList.get(idd).getQuestionID();
                Call<Void> call = questionsService.deleteQuestion(mQuestionId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(context,"Delete Question successful", Toast.LENGTH_SHORT).show();
                        setTasks(questionList);
                        loadData() ;
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void loadData() {
        Call<ArrayList<Question>> calls = questionsService.getQuestions();
        calls.enqueue(new Callback<ArrayList<Question>>() {
            @Override
            public void onResponse(Call<ArrayList<Question>> calls, Response<ArrayList<Question>> response) {
                questionList.clear();
                questionList.addAll(response.body());
                notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ArrayList<Question>> calls, Throwable t) {
//                Toast.makeText(().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setTasks(ArrayList<Question> questionList){
        this.questionList = questionList;
        notifyDataSetChanged();
    }

}