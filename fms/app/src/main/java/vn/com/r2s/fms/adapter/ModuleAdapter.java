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
import vn.com.r2s.fms.Activity.ModuleActivity;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Module;
import vn.com.r2s.fms.ui.module.ModuleViewModel;

public class ModuleAdapter extends  RecyclerView.Adapter<ModuleAdapter.ViewHolder>  {
    ModuleViewModel moduleViewModel;
    private Context context;
    CardView imgEditModule1, imgDeleteModule1;
    private ArrayList<Module> moduleList;
    private ModuleService moduleService;
    private Dialog dialog;
    Button btnOk2, btnSuccess,btnFail, btnCancel;
    TextView tvNotification1,tvFailed;
    public ModuleAdapter(Context context,ArrayList<Module> moduleList) {
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ModuleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        moduleService = APIUtility.getModuleService();
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.module_item, parent, false);
        ModuleAdapter.ViewHolder viewHolder = new ModuleAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleAdapter.ViewHolder holder, int i) {

        String month = moduleList.get(i).getStartTime().substring(5, 7);
        String day = moduleList.get(i).getStartTime().substring(8, 10);
        String year = moduleList.get(i).getStartTime().substring(0, 4);

        String month1 = moduleList.get(i).getEndTime().substring(5, 7);
        String day1 = moduleList.get(i).getEndTime().substring(8, 10);
        String year1 = moduleList.get(i).getEndTime().substring(0, 4);

        String month2 = moduleList.get(i).getFeedbackStartTime().substring(5,7);
        String day2 = moduleList.get(i).getFeedbackStartTime().substring(8,10);
        String year2 = moduleList.get(i).getFeedbackStartTime().substring(0,4);
        String hours = moduleList.get(i).getFeedbackStartTime().substring(11,14);
        String minute = moduleList.get(i).getFeedbackStartTime().substring(14,16);





        Log.d("====>>>", year2+"/"+month2+"/"+day2+" "+hours+""+minute+"");
        String stratDate = month+"/"+day+"/"+year;
        String endDate = month1+"/"+day1+"/"+year1;
        String fbSdate = year2+"/"+month2+"/"+day2+" "+hours+""+minute+"";
        String fbEdate = year2+"/"+month2+"/"+day2+" "+hours+""+minute+"";

//



        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        holder.tvModuleID.setText(""+moduleList.get(i).getModuleId());
        holder.tvModuleName.setText(moduleList.get(i).getModuleName());
        holder.tvStartTime.setText(stratDate);
        holder.tvEndTime.setText(endDate);
        holder.tvFBStartDate.setText(fbSdate);
        holder.tvFBEndDate.setText(fbEdate);
        holder.tvAdminID.setText(moduleList.get(i).getIdAdmin());
//        holder.tvFBTitle.setText(moduleList.get(i).getIdFeedback());
        // holder.tvStartTime.setText(new SimpleDateFormat("MM/dd/YYYY").format(classicList.get(i).getStartTime()));
        // holder.tvEndTime.setText(new SimpleDateFormat("MM/dd/YYYY").format(classicList.get(i).getEndTime()));


    }

    @Override
    public int getItemCount() {
        if (moduleList == null) {
            return 0;
        }

        return moduleList.size();
    }

    public void setTasks(ArrayList<Module> moduleList) {
        this.moduleList = moduleList;
        notifyDataSetChanged();
    }

    public ArrayList<Module> getTasks() {
        return moduleList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvModuleID;
        TextView tvModuleName;
        TextView tvStartTime;
        TextView tvEndTime;
        TextView tvAdminID;
        TextView tvFBTitle;
        TextView tvFBStartDate;
        TextView tvFBEndDate;

        CardView imgEditModule;
        CardView imgDeleteModule;

        Button btnEditmodule1, btnDeleteModule;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDeleteModule1 = itemView.findViewById(R.id.imgDeleteModule1);
            imgEditModule1 = itemView.findViewById(R.id.imgEditModule1);
            tvModuleID = itemView.findViewById(R.id.tvModuleID);
            tvModuleName = itemView.findViewById(R.id.tvModuleName);
            tvStartTime = itemView.findViewById(R.id.tvStartDate);
            tvEndTime = itemView.findViewById(R.id.tvEndDate);
            tvFBTitle=itemView.findViewById(R.id.tvFBTitle);
            tvAdminID = itemView.findViewById(R.id.tvAdminID);
            tvFBStartDate = itemView.findViewById(R.id.tvFBStartTime);
            tvFBEndDate = itemView.findViewById(R.id.tvFBEndTime);
            imgEditModule = itemView.findViewById(R.id.imgEditModule1);
            imgDeleteModule = itemView.findViewById(R.id.imgDeleteModule1);
            btnEditmodule1 = itemView.findViewById(R.id.btnEditModule);
            btnDeleteModule = itemView.findViewById(R.id.btnDeleteModule);
            if(MainActivity.typeUser.equals("Admin")){
                imgDeleteModule1.setVisibility(View.VISIBLE);
                imgEditModule1.setVisibility(View.VISIBLE);
                btnEditmodule1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int elementId = moduleList.get(getAdapterPosition()).getModuleId();
                        Intent i = new Intent(context, ModuleActivity.class);
                        i.putExtra(Constants.UPDATE_USER_ID, elementId);
                        context.startActivity(i);
                    }
                });
                btnDeleteModule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = getAdapterPosition();
                        dialogDelete(id);
                    }
                });
            }
            else {
                imgDeleteModule1.setVisibility(View.INVISIBLE);
                imgEditModule1.setVisibility(View.INVISIBLE);
            }

        }
    }
    public void setData(){
        Call<ArrayList<Module>> calls = moduleService.getAllModule();
        calls.enqueue(new Callback<ArrayList<Module>>() {
            @Override
            public void onResponse(Call<ArrayList<Module>> calls, Response<ArrayList<Module>> response) {
                moduleList.clear();
                moduleList.addAll(response.body());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Module>> calls, Throwable t) {
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
        btnOk2 = dialog.findViewById(R.id.btnOk);
        btnCancel = dialog.findViewById(R.id.btn_Cancel);
        btnOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int moduleId = moduleList.get(idd).getModuleId();
                Call<Void> call = moduleService.deleteModule(moduleId);
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
                setTasks(moduleList);
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
