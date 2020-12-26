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
import vn.com.r2s.fms.Activity.ClassicTraineeActivity;
import vn.com.r2s.fms.Activity.ClassicTrainerActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Enrollment;

public class ClassAdapterTrainer extends RecyclerView.Adapter<ClassAdapterTrainer.ViewHolder> {
    private Context context;
    private ArrayList<Assignment> assignmentList;
    private AssignmentService assignmentService;

    public ClassAdapterTrainer(Context context, ArrayList<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }
    @NonNull
    @Override
    public ClassAdapterTrainer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        assignmentService = APIUtility.getAssignmentService();
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_class_trainee, parent, false);
        ClassAdapterTrainer.ViewHolder viewHolder = new ClassAdapterTrainer.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapterTrainer.ViewHolder holder, int i) {

//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
//        try {
//            Date date = format.parse(String.valueOf(classicList.get(i).getStartTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }



        holder.tvClassId.setText(assignmentList.get(i).getClassId()+"");

        ClassicService classicService = APIUtility.getClassicService();
        classicService.getClassById(assignmentList.get(i).getClassId()).enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                holder.tvClassName.setText(response.body().getClassName());
            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {

            }
        });

        EnrollmentService enrollmentService = APIUtility.getEnService();
        enrollmentService.getClassById(assignmentList.get(i).getClassId()).enqueue(new Callback<ArrayList<Enrollment>>() {
            @Override
            public void onResponse(Call<ArrayList<Enrollment>> call, Response<ArrayList<Enrollment>> response) {
                holder.tvNumberTrainee.setText(response.body().size()+"");
            }

            @Override
            public void onFailure(Call<ArrayList<Enrollment>> call, Throwable t) {

            }
        });
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClassicTrainerActivity.class);
                intent.putExtra("classID", assignmentList.get(i).getClassId());
                context.startActivity(intent);
            }
        });


//        holder.tvCapadity.setText(classicList.get(i).getCapadity()+"");
//        holder.tvStartTime.setText(new SimpleDateFormat("MM/dd/YYYY").format(classicList.get(i).getStartTime()));
//        holder.tvEndTime.setText(new SimpleDateFormat("MM/dd/YYYY").format(classicList.get(i).getEndTime()));
    }

    @Override
    public int getItemCount() {
        if (assignmentList == null) {
            return 0;
        }

        return assignmentList.size();
    }
    public void setTasks(ArrayList<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
        notifyDataSetChanged();
    }
    public ArrayList<Assignment> getTasks() {
        return assignmentList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvClassId;
        TextView tvClassName;
        TextView tvNumberTrainee;
        Button btnDetails;
        CardView imgViewClass;
//
//        Button btnEditClass1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClassId = itemView.findViewById(R.id.tvClassId);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvNumberTrainee = itemView.findViewById(R.id.tvNumberTrainee);
            btnDetails = itemView.findViewById(R.id.btnDetails);
            imgViewClass= itemView.findViewById(R.id.imgViewDetail1);
//            btnEditClass1 = itemView.findViewById(R.id.imgEditClass1);
//            btnEditClass1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int elementId = classicList.get(getAdapterPosition()).getClassId();
//                    Intent i = new Intent(context, ClassisActivity.class);
//                    i.putExtra(Constants.UPDATE_USER_ID, elementId);
//                    context.startActivity(i);
//                }
//            });
        }
    }
}
