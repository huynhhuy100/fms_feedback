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
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.api.TraineeService;
import vn.com.r2s.fms.model.Enrollment;
import vn.com.r2s.fms.model.Trainee;

public class ClassAdaperTraineeDetails extends RecyclerView.Adapter<ClassAdaperTraineeDetails.ViewHolder> {

    private Context context;
    private ArrayList<Enrollment> classicList;
    private EnrollmentService classicService;

    public ClassAdaperTraineeDetails(Context context, ArrayList<Enrollment> classicList) {
        this.classicList = classicList;
    }

    @NonNull
    @Override
    public ClassAdaperTraineeDetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        classicService = APIUtility.getEnService();
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_class_trainee_details, parent, false);
        ClassAdaperTraineeDetails.ViewHolder viewHolder = new ClassAdaperTraineeDetails.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdaperTraineeDetails.ViewHolder holder, int i) {

//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
//        try {
//            Date date = format.parse(String.valueOf(classicList.get(i).getStartTime()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        holder.tvNumber.setText(i+1 + "");
        holder.tvTraineeId.setText(classicList.get(i).getEnrollmentID().getTraineeId() + "");

        TraineeService traineeService = APIUtility.getTraineeService();
        traineeService.getTraineeById(classicList.get(i).getEnrollmentID().getTraineeId()).enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                holder.tvTraineeName.setText(response.body().getName());
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {

            }
        });


//        EnrollmentService enrollmentService = APIUtility.getEnService();
//        enrollmentService.getClassById(classicList.get(i).getEnrollmentID().getClassId()).enqueue(new Callback<ArrayList<Enrollment>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Enrollment>> call, Response<ArrayList<Enrollment>> response) {
//                holder.tvNumberTrainee.setText(response.body().size()+"");
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Enrollment>> call, Throwable t) {
//
//            }
//        });
//
//        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ClassicTraineeActivity.class);
//                intent.putExtra("classID", classicList.get(i).getEnrollmentID().getClassId());
//                intent.putExtra("className", classicList.get(i).getClassName());
//                context.startActivity(intent);
//            }
//        });


//        holder.tvCapadity.setText(classicList.get(i).getCapadity()+"");
//        holder.tvStartTime.setText(new SimpleDateFormat("MM/dd/YYYY").format(classicList.get(i).getStartTime()));
//        holder.tvEndTime.setText(new SimpleDateFormat("MM/dd/YYYY").format(classicList.get(i).getEndTime()));
    }

    @Override
    public int getItemCount() {
        if (classicList == null) {
            return 0;
        }

        return classicList.size();
    }

    public void setTasks(ArrayList<Enrollment> classicList) {
        this.classicList = classicList;
        notifyDataSetChanged();
    }

    public ArrayList<Enrollment> getTasks() {
        return classicList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber;
        TextView tvTraineeId;
        TextView tvTraineeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvTraineeId = itemView.findViewById(R.id.tvTraineeId);
            tvTraineeName = itemView.findViewById(R.id.tvTraineeName);

        }
    }
}
