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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.DetailEnrollmentActivity;
import vn.com.r2s.fms.Activity.EditEnrollmentActivity;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.api.TraineeService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Enrollment;
import vn.com.r2s.fms.model.Trainee;

public class EnrollmentAdapter extends RecyclerView.Adapter<EnrollmentAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Enrollment> enrollmentList;
    private ClassicService classicService;
    private TraineeService traineeService;
    String endTime;

    private EnrollmentService enrollmentService;
    private Dialog dialog;
    Button btnOk, btnSuccess,btnFail, btnCancel;
    TextView tvNotification1,tvFailed,tvEnrol;


    public EnrollmentAdapter(Context context,ArrayList<Enrollment> classicList) {
        this.enrollmentList = classicList;
    }

    @NonNull
    @Override
    public EnrollmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        enrollmentService = APIUtility.getEnService();
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_enrollment, parent, false);
        EnrollmentAdapter.ViewHolder viewHolder = new EnrollmentAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EnrollmentAdapter.ViewHolder holder, int i) {

        holder.tvClassId.setText(""+enrollmentList.get(i).getEnrollmentID().getClassId());
        //holder.tvClassName.setText(enrollmentList.get(i).getClassName());
        holder.tvTraineeId.setText(enrollmentList.get(i).getEnrollmentID().getTraineeId());
        int classID=enrollmentList.get(i).getEnrollmentID().getClassId();
        String traineeID=enrollmentList.get(i).getEnrollmentID().getTraineeId();

        classicService = APIUtility.getClassicService();
        Call<Classic> calls = classicService.getClassById(classID);
        calls.enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                String className=response.body().getClassName();
                holder.tvClassName.setText(className);

            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {
                Toast.makeText(context, "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        traineeService = APIUtility.getTraineeService();
        Call<Trainee> callss = traineeService.getTraineeById(traineeID);
        callss.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                String traineeName=response.body().getName();
                holder.tvTraineeName.setText(traineeName);

            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(context, "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        holder.imbtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("0001", enrollmentList.get(i).getEnrollmentID().getTraineeId()+"" );
                Intent intent = new Intent(context, DetailEnrollmentActivity.class);
                intent.putExtra("traineeId",enrollmentList.get(i).getEnrollmentID().getTraineeId());
                intent.putExtra("classId",enrollmentList.get(i).getEnrollmentID().getClassId());

                context.startActivity(intent);
            }
        });

        holder.btnEditEnrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, EditEnrollmentActivity.class);
                in.putExtra("traineeId",enrollmentList.get(i).getEnrollmentID().getTraineeId());
                Log.e("000222", enrollmentList.get(i).getEnrollmentID().getTraineeId()+"" );
                in.putExtra("classId",enrollmentList.get(i).getEnrollmentID().getClassId());
                Log.e("0003333", enrollmentList.get(i).getEnrollmentID().getClassId()+"" );

                context.startActivity(in);
            }
        });



    }
    @Override
    public int getItemCount() {
        if (enrollmentList == null) {
            return 0;
        }

        return enrollmentList.size();
    }

    public void setTasks(ArrayList<Enrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
        notifyDataSetChanged();
    }

    public ArrayList<Enrollment> getTasks() {
        return enrollmentList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvClassId;
        TextView tvClassName;
        TextView tvTraineeId;
        TextView tvTraineeName;

        CardView imgEditClass;
        CardView imgDeleteClass;
        CardView imgViewClass;
        Button imbtnDel,imbtnDetail,btnEditEnrollment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClassId = itemView.findViewById(R.id.tvClassId);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvTraineeId = itemView.findViewById(R.id.tvTraineeId);
            tvTraineeName = itemView.findViewById(R.id.tvTraineeName);
//
            imgEditClass = itemView.findViewById(R.id.imgEditClass);
            imgDeleteClass = itemView.findViewById(R.id.imgDeleteClass);
            imgViewClass= itemView.findViewById(R.id.imgViewDetail1);
            imbtnDel= itemView.findViewById(R.id.imgDeleteClass1);
            imbtnDetail=itemView.findViewById(R.id.imgViewDetail);

            btnEditEnrollment= itemView.findViewById(R.id.imgEditClass1);


            imbtnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getAdapterPosition();
                    dialogDelete(id);
                }
            });


        }
    }
    public void setData(){
        Call<ArrayList<Enrollment>> calls = enrollmentService.getAllEnrollment();
        calls.enqueue(new Callback<ArrayList<Enrollment>>() {
            @Override
            public void onResponse(Call<ArrayList<Enrollment>> calls, Response<ArrayList<Enrollment>> response) {
                enrollmentList.clear();
                enrollmentList.addAll(response.body());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Enrollment>> calls, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "FeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        notifyDataSetChanged();
    }

    private void dialogDelete(final int idd) {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        int classId = enrollmentList.get(idd).getEnrollmentID().getClassId();
        classicService = APIUtility.getClassicService();
        classicService.getClassById(classId).enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {

                try {
                    endTime=response.body().getEndTime();
                    Date now = formatter.parse(String.valueOf(LocalDate.now()));
                    Date endDate = formatter.parse(endTime);
//
                    Log.e("0002", now+"");

                    Log.e("00022",endDate+"");

                    if (endDate.compareTo(now) <0){

                        showDialogSuccess2();

                        tvEnrol.setText(Language.confirmDel);
                        btnCancel = dialog.findViewById(R.id.btn_Cancel);
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int classId = enrollmentList.get(idd).getEnrollmentID().getClassId();
                                Log.e("0001-> datatest", enrollmentList.get(idd).getEnrollmentID().getClassId()+"");
                                String traineeId = enrollmentList.get(idd).getEnrollmentID().getTraineeId();
                                Log.e("0001-> datatest", enrollmentList.get(idd).getEnrollmentID().getTraineeId()+"");

                                Call<Void> call = enrollmentService.deleteEnrollment(classId,traineeId);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        try {
                                            showDialogSuccess();
                                        }catch (Exception e){
                                            showDialogFailed();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                    } else {
                        showDialogSuccess2();
                        tvEnrol.setText(Language.notOverTime);
                        btnCancel = dialog.findViewById(R.id.btn_Cancel);
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int classId = enrollmentList.get(idd).getEnrollmentID().getClassId();
                                Log.e("0001-> datatest", enrollmentList.get(idd).getEnrollmentID().getClassId()+"");
                                String traineeId = enrollmentList.get(idd).getEnrollmentID().getTraineeId();
                                Log.e("0001-> datatest", enrollmentList.get(idd).getEnrollmentID().getTraineeId()+"");

                                Call<Void> call = enrollmentService.deleteEnrollment(classId,traineeId);
                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        try {
                                            showDialogSuccess();
                                        }catch (Exception e){
                                            showDialogFailed();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {

            }
        });







    }

    public void showDialogSuccess2() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_enrollment);
        dialog.show();
        btnOk = dialog.findViewById(R.id.btnOk);
        tvEnrol = dialog.findViewById(R.id.tvDelEnrollment);


    }

    public void showDialogSuccess() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success1);
        dialog.show();
        btnSuccess = dialog.findViewById(R.id.btnSuccess);
        tvNotification1 = dialog.findViewById(R.id.textview);
        tvNotification1.setText(Language.delete);
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ClassicActivity.this, "ok", Toast.LENGTH_SHORT).show();
                // do it
                setData();
                setTasks(enrollmentList);
                dialog.dismiss();
            }
        });
    }
    public void showDialogFailed() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_faild);
        dialog.show();
        btnFail = dialog.findViewById(R.id.btnOk);
        tvFailed = dialog.findViewById(R.id.tvFailed);
        tvFailed.setText(Language.deleteFailed);
        btnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ClassicActivity.this, "ok", Toast.LENGTH_SHORT).show();
                // do it
                dialog.dismiss();
            }
        });
    }

    private void findClassByClassID(int classId,@NonNull EnrollmentAdapter.ViewHolder holder, int i,ArrayList<Enrollment> enrollmentList) {
        classicService = APIUtility.getClassicService();
        Call<Classic> calls = classicService.getClassById(classId);
        calls.enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                String className=response.body().getClassName();
                holder.tvClassName.setText(className);
                enrollmentList.get(i).setClassName(className);

            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {
                Toast.makeText(context, "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
