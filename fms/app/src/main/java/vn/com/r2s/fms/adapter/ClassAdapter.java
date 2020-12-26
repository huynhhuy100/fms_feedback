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
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.ClassicActivity;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.ui.classic.ClassViewModel;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>  {
    ClassViewModel classViewModel;
    private Context context;
    private ArrayList<Classic> classicList;
    private ClassicService classicService;
    private Dialog dialog;
    Button btnOk, btnSuccess,btnFail, btnCancel;
    TextView tvNotification1,tvFailed;
    public ClassAdapter(Context context,ArrayList<Classic> classicList) {
        this.classicList = classicList;
    }

    @NonNull
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        classicService = APIUtility.getClassicService();
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.class_item, parent, false);
        ClassAdapter.ViewHolder viewHolder = new ClassAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ViewHolder holder, int i) {

//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
//
//        String strStartDate = format.format(classicList.get(i).getStartTime());
//        String strEndDate = format.format(classicList.get(i).getEndTime());
        String month = classicList.get(i).getStartTime().substring(5,7);
        String day = classicList.get(i).getStartTime().substring(8,10);
        String year = classicList.get(i).getStartTime().substring(0,4);

        String month1 = classicList.get(i).getEndTime().substring(5,7);
        String day1 = classicList.get(i).getEndTime().substring(8,10);
        String year1 = classicList.get(i).getEndTime().substring(0,4);
        Log.d("====>>>", day+ " , " + month + " , " + year);
        String stratDate = month+"/"+day+"/"+year;
        String endDate = month1+"/"+day1+"/"+year1;

        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        holder.tvClassId.setText(""+classicList.get(i).getClassID());
        holder.tvClassName.setText(classicList.get(i).getClassName());
        holder.tvCapadity.setText(classicList.get(i).getCapadity()+"");
       // holder.tvStartTime.setText(new SimpleDateFormat("MM/dd/YYYY").format(classicList.get(i).getStartTime()));
       // holder.tvEndTime.setText(new SimpleDateFormat("MM/dd/YYYY").format(classicList.get(i).getEndTime()));
        holder.tvStartTime.setText(stratDate);
        holder.tvEndTime.setText(endDate);


    }

    @Override
    public int getItemCount() {
        if (classicList == null) {
            return 0;
        }

        return classicList.size();
    }

    public void setTasks(ArrayList<Classic> classicList) {
        this.classicList = classicList;
        notifyDataSetChanged();
    }

    public ArrayList<Classic> getTasks() {
        return classicList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvClassId;
        TextView tvClassName;
        TextView tvCapadity;
        TextView tvStartTime;
        TextView tvEndTime;

        CardView imgEditClass;
        CardView imgDeleteClass;

        Button btnEditClass1, btnDeleteClass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClassId = itemView.findViewById(R.id.tvClassId);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvCapadity = itemView.findViewById(R.id.tvCapadity);
            tvStartTime = itemView.findViewById(R.id.tvStartTime);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            imgEditClass = itemView.findViewById(R.id.imgEditClass);
            imgDeleteClass = itemView.findViewById(R.id.imgDeleteClass);
            btnEditClass1 = itemView.findViewById(R.id.imgEditClass1);
            btnDeleteClass = itemView.findViewById(R.id.btnDeleteClass);
            btnEditClass1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = classicList.get(getAdapterPosition()).getClassID();
                    Intent i = new Intent(context, ClassicActivity.class);
                    i.putExtra(Constants.UPDATE_USER_ID, elementId);
                    context.startActivity(i);
                }
            });
            btnDeleteClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getAdapterPosition();
                    dialogDelete(id);
                }
            });
        }
    }
    public void setData(){
        Call<ArrayList<Classic>> calls = classicService.getAllClass();
        calls.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> calls, Response<ArrayList<Classic>> response) {
                classicList.clear();
                classicList.addAll(response.body());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Classic>> calls, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "FeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        notifyDataSetChanged();
    }
    private void dialogDelete(final int idd) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.show();
        btnOk = dialog.findViewById(R.id.btnOk);
        btnCancel = dialog.findViewById(R.id.btn_Cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int classId = classicList.get(idd).getClassID();
                Call<Void> call = classicService.deleteClass(classId);
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

    public void showDialogSuccess() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
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
                setTasks(classicList);
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
}
