package vn.com.r2s.fms.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.EditAssignmentActivity;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Module;
import vn.com.r2s.fms.ui.assignment.AssignmentViewModel;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    AssignmentViewModel asmViewModel;
    private Context context;
    private ArrayList<Assignment> asmList;
    private ArrayList<Assignment> asmListByClassName;
    private AssignmentService asmService;
    private ClassicService classicService;
    private ModuleService moduleService;
    private Dialog dialog;
    TextView tvThisAssignment, tvNotification, tvFailed;
    Button btnOk, btnCancel, btnFail;



    public AssignmentAdapter(Context context, ArrayList<Assignment> asmList) {
        this.asmList = asmList;
    }

    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        asmService = APIUtility.getAssignmentService();
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.assignment_item, parent, false);
        AssignmentAdapter.ViewHolder viewHolder = new AssignmentAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        getClassName(holder, position);
        getModuleName(holder, position);

        holder.tvNoId.setText(position+1+"");
        holder.tvTrainerName.setText(""+asmList.get(position).getTrainerId());
        holder.tvRegistrationCode.setText(""+asmList.get(position).getRegistrationCode());;
    }

    private void getClassName(ViewHolder holder, int position) {
        int classId = asmList.get(position).getClassId();
        classicService = APIUtility.getClassicService();
        classicService.getClassById(classId).enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                holder.tvClassName.setText(response.body().getClassName());
            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {

            }
        });
    }

    private void getModuleName(ViewHolder holder, int position) {
        int moduleId = asmList.get(position).getModuleId();
        moduleService = APIUtility.getModuleService();
        moduleService.getModuleById(moduleId).enqueue(new Callback<Module>() {
            @Override
            public void onResponse(Call<Module> call, Response<Module> response) {
                holder.tvModuleName.setText(response.body().getModuleName());
            }

            @Override
            public void onFailure(Call<Module> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(asmList == null) {
            return 0;
        }
        return asmList.size();
    }

    public void setTasks(ArrayList<Assignment> asmList) {
        this.asmList = asmList;
        notifyDataSetChanged();
    }

    public ArrayList<Assignment> getTasks() {
        return asmList;
    }

    public void deleteAsm(final int id) {

        int classId = asmList.get(id).getClassId();
        classicService = APIUtility.getClassicService();
        classicService.getClassById(classId).enqueue(new Callback<Classic>() {
            @Override
            public void onResponse(Call<Classic> call, Response<Classic> response) {
                showDialogDelete();
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int classId = asmList.get(id).getClassId();
                        int moduleId = asmList.get(id).getModuleId();
                        String trainerId = asmList.get(id).getTrainerId();

                        Call<Void> calls = asmService.deleteAssignment(classId, moduleId, trainerId);
                        calls.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                showDialogSuccess();

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                showDialogFail();
                            }
                        });
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(Call<Classic> call, Throwable t) {

            }
        });
    }

    private void showDialogFail() {
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

    private void showDialogSuccess() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        TextView tv = dialog.findViewById(R.id.textview);
        tv.setText("Delete Success");
        dialog.show();
        btnOk = dialog.findViewById(R.id.btnSuccess);
        tvNotification = dialog.findViewById(R.id.textview);
        tvNotification.setText(Language.delete);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                setTasks(asmList);
                dialog.dismiss();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvClassName;
        TextView tvModuleName;
        TextView tvTrainerName;
        TextView tvRegistrationCode;
        TextView tvNoId;

        int updateClassId;
        int updateModuleId;

        CardView imgEditAsm;
        CardView imgDeleteAsm;

        Button btnDeleteAsm, btnUpdateAsm;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvClassName = itemView.findViewById(R.id.tv_className);
            tvModuleName = itemView.findViewById(R.id.tv_moduleName);
            tvTrainerName = itemView.findViewById(R.id.tv_trainerName);
            tvRegistrationCode = itemView.findViewById(R.id.tv_registrationCode);
            tvNoId = itemView.findViewById(R.id.tv_noId);
            imgEditAsm = itemView.findViewById(R.id.imgEditASM);
            btnUpdateAsm = itemView.findViewById(R.id.imgEditASM1);
            imgDeleteAsm = itemView.findViewById(R.id.imgDeleteASM);
            btnDeleteAsm = itemView.findViewById(R.id.imgDeleteASM1);
            btnDeleteAsm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = getAdapterPosition();
                    deleteAsm(id);
                }
            });

            if(MainActivity.typeUser.equals(Language.trainer)) {
                btnDeleteAsm.setVisibility(View.GONE);
                btnUpdateAsm.setVisibility(View.GONE);
                imgDeleteAsm.setVisibility(View.GONE);
                imgEditAsm.setVisibility(View.GONE);
            }

            btnUpdateAsm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String className = (String) tvClassName.getText();
                    String moduleName = (String) tvModuleName.getText();
                    String trainerId = (String) tvTrainerName.getText();
                    Intent intent = new Intent(context, EditAssignmentActivity.class);
                    intent.putExtra("className", className);
                    intent.putExtra("moduleName", moduleName);
                    intent.putExtra("trainerId", trainerId);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setData() {
        Call<ArrayList<Assignment>> calls = asmService.getAllAssignment();
        calls.enqueue(new Callback<ArrayList<Assignment>>() {
            @Override
            public void onResponse(Call<ArrayList<Assignment>> call, Response<ArrayList<Assignment>> response) {
                asmList.clear();
                asmList.addAll(response.body());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Assignment>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "AssignmentView Model: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        notifyDataSetChanged();
    }

    public void showDialogDelete() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        tvThisAssignment = dialog.findViewById(R.id.tv_noti);
        tvThisAssignment.setText("This Assignment?");
        dialog.show();
        btnOk = dialog.findViewById(R.id.btnOk);
        btnCancel = dialog.findViewById(R.id.btn_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
