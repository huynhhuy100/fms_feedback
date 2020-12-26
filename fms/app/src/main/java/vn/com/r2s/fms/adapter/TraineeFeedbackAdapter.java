package vn.com.r2s.fms.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.TraineeFeedbackActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AnswerService;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.FeedbackService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.api.TopicService;
import vn.com.r2s.fms.api.TraineeService;
import vn.com.r2s.fms.model.Answer;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Enrollment;
import vn.com.r2s.fms.model.Feedback;
import vn.com.r2s.fms.model.Module;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;
import vn.com.r2s.fms.model.Trainee;

public class TraineeFeedbackAdapter extends RecyclerView.Adapter<TraineeFeedbackAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Feedback> Feedbacklist;
    private ArrayList<Module> modulelist;
    private ArrayList<Classic> classiclist;
    private ArrayList<Answer> answerlist;
    private ArrayList<Enrollment> enrollmentList;
    private FeedbackService feedbackService;
    private ModuleService moduleService;
    private ClassicService classicService;
    private AnswerService answerService;
    private TraineeFeedbackAdapter adapter;
    String classname, modulename, traineename;

    public TraineeFeedbackAdapter(Context context, ArrayList<Feedback> Feedbacklist) {
        this.context = context;
        this.Feedbacklist = Feedbacklist;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtraineefbtt, tvtraineeclassid, tvtraineeclassname,
                tvtraineemoduleiid, tvtraineemodulenname, tvtraineeend, tvtraineestatus;
        Button btncheckfb;
        CardView cardcheckfb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtraineefbtt = itemView.findViewById(R.id.tvtraineefbtt);
            tvtraineeclassid = itemView.findViewById(R.id.tvtraineeclassid);
            tvtraineeclassname = itemView.findViewById(R.id.tvtraineeclassname);
            tvtraineemoduleiid = itemView.findViewById(R.id.tvtraineemoduleiid);
            tvtraineemodulenname = itemView.findViewById(R.id.tvtraineemodulenname);
            tvtraineeend = itemView.findViewById(R.id.tvtraineeend);
            tvtraineestatus = itemView.findViewById(R.id.tvtraineestatus);
            btncheckfb = itemView.findViewById(R.id.btncheckfb);
            cardcheckfb = itemView.findViewById(R.id.cardcheckfb);
            modulelist = new ArrayList<>();
            classiclist = new ArrayList<>();
            enrollmentList = new ArrayList<>();
            btncheckfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int moduleId = modulelist.get(getAdapterPosition()).getModuleId();
                    int classisId = classiclist.get(getAdapterPosition()).getClassID();
                    classname = classiclist.get(getAdapterPosition()).getClassName();
                    modulename = modulelist.get(getAdapterPosition()).getModuleName();
                    Intent i = new Intent(context, TraineeFeedbackActivity.class);
                    i.putExtra("modulename", modulename);
                    i.putExtra("classname", classname);
                    i.putExtra("moduleId", moduleId);
                    i.putExtra("classisId", classisId);
                    cardcheckfb.setVisibility(View.INVISIBLE);

                    context.startActivity(i);
                }
            });
        }

    }
    @NonNull
    @Override
    public TraineeFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        feedbackService = APIUtility.getFeedbackService();
        classicService = APIUtility.getClassicService();
        moduleService = APIUtility.getModuleService();
        answerService = APIUtility.getAnswerService();
        adapter = new TraineeFeedbackAdapter(context, Feedbacklist);
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.traineefeedback_item, parent, false);
        TraineeFeedbackAdapter.ViewHolder viewHolder = new TraineeFeedbackAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TraineeFeedbackAdapter.ViewHolder holder, int position) {
        holder.tvtraineefbtt.setText(Feedbacklist.get(position).getTitle());
        holder.tvtraineestatus.setText("InComplete");
//        modulename = "Thông tin";
        Call<ArrayList<Module>> call = moduleService.getAllModule();
        call.enqueue(new Callback<ArrayList<Module>>() {
            @Override
            public void onResponse(Call<ArrayList<Module>> callss, Response<ArrayList<Module>> response) {
                ArrayList<Module> modules = response.body();
                modulelist.addAll(modules);
                holder.tvtraineemoduleiid.setText(String.valueOf(modulelist.get(position).getModuleId()));

                holder.tvtraineemodulenname.setText(response.body().get(position).getModuleName());
                holder.tvtraineeend.setText(response.body().get(position).getEndTime());
            }

            @Override
            public void onFailure(Call<ArrayList<Module>> callss, Throwable t) {
            }
        });
        classicService = APIUtility.getClassicService();
        Call<ArrayList<Classic>> calls = classicService.getAllClass();
        calls.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> call, Response<ArrayList<Classic>> response) {
                classiclist.addAll(response.body());
                holder.tvtraineeclassname.setText(response.body().get(position).getClassName());
                holder.tvtraineeclassid.setText(String.valueOf(response.body().get(position).getClassID()));

                moduleService = APIUtility.getModuleService();

            }

            @Override
            public void onFailure(Call<ArrayList<Classic>> call, Throwable t) {
            }
        });
//        answerService = APIUtility.getAnswerService();
//        Call<ArrayList<Answer>> callss = answerService.getAllAnswers();
//        callss.enqueue(new Callback<ArrayList<Answer>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Answer>> call, Response<ArrayList<Answer>> response) {
//                answerlist.addAll(response.body());
//                for (Answer answer : answerlist) {
//                    if (answer.getModuleId() == (position + 1)) {
//                        cardcheckfb.setVisibility(View.INVISIBLE);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Answer>> call, Throwable t) {
//            }
//        });


    }

    @Override
    public int getItemCount() {
        if (Feedbacklist == null) {
            return 0;
        }
        return Feedbacklist.size();

    }

    public void setTasks(ArrayList<Feedback> Feedbacklist) {
        this.Feedbacklist = Feedbacklist;
        notifyDataSetChanged();
    }

    public ArrayList<Feedback> getTasks() {
        return Feedbacklist;
    }

}
