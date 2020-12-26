package vn.com.r2s.fms.ui.assignment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.AssignmentActivity;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.AssignmentAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.LoginService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Module;

public class AssignmentFragment extends Fragment {

    Button btnAdd;
    Button btnSearch;
    RecyclerView rec_Assignment;
    EditText edt_asmSearch;
    Dialog dialog;
    Button btnOkFail;
    TextView tvFailed;

    AssignmentService asmService;
    ClassicService classicService;
    ModuleService moduleService;
    LoginService trainerService;

    AssignmentAdapter asmAdapter;
    AssignmentViewModel asmViewModel;
    ArrayList<Assignment> asmList;
    ArrayList<Assignment> asmListByClassId;
    ArrayList<Assignment> asmListByModuleId;
    ArrayList<Assignment> asmListByTrainerId;
    ArrayList<Assignment> asmListByRegistrationCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        asmViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        View root = inflater.inflate(R.layout.fragment_assignment, container, false);

        rec_Assignment = root.findViewById(R.id.rec_Assignment);
        btnAdd = root.findViewById(R.id.btnAddASM);
        btnSearch = root.findViewById(R.id.btn_searchASM);
        edt_asmSearch = root.findViewById(R.id.edt_searchASM);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_asmSearch.getText().toString().equals("")) {
                    getData();
                } else {
                    String className = edt_asmSearch.getText().toString();
                    classicService = APIUtility.getClassicService();
                    classicService.getClassByClassName(className).enqueue(new Callback<Classic>() {
                        @Override
                        public void onResponse(Call<Classic> call, Response<Classic> response) {
                            try{
                                getData2(response.body().getClassID());
                            } catch (Exception exception) {
                                showDialogFailed();
                            }

                        }

                        @Override
                        public void onFailure(Call<Classic> call, Throwable t) {

                        }
                    });
                }


            }
        });

        if (MainActivity.typeUser.equals(Language.admin)) {
            getData();
            retrieveTasks();
        } else if(MainActivity.typeUser.equals(Language.trainer)) {
            getData();
            retrieveTasks();
            btnAdd.setVisibility(View.GONE);
        } else {
            btnAdd.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            rec_Assignment.setVisibility(View.GONE);
        }

        final TextView textView = root.findViewById(R.id.text_home);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AssignmentActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    public void retrieveTasks() {
        asmViewModel.getAsmList().observe(getViewLifecycleOwner(), asm -> {
            asmList.clear();
            asmList.addAll(asm);
            asmAdapter.notifyDataSetChanged();
        });
    }

    public void retrieveTasksClass(int classId) {
        asmService = APIUtility.getAssignmentService();
        asmService.getAssignmentByClassId(classId).enqueue(new Callback<ArrayList<Assignment>>() {
            @Override
            public void onResponse(Call<ArrayList<Assignment>> call, Response<ArrayList<Assignment>> response) {
                asmList.clear();
                asmListByClassId.addAll(response.body());
                asmAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ArrayList<Assignment>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }

    public void getData() {
        rec_Assignment.setHasFixedSize(true);
        rec_Assignment.setLayoutManager(new LinearLayoutManager(getContext()));

        asmList = new ArrayList<>();
        asmAdapter = new AssignmentAdapter(getContext(), asmList);
        rec_Assignment.setAdapter(asmAdapter);
        retrieveTasks();
    }

    public void getData2(int classId) {
        rec_Assignment.setHasFixedSize(true);
        rec_Assignment.setLayoutManager(new LinearLayoutManager(getContext()));

        asmListByClassId = new ArrayList<>();
        asmAdapter = new AssignmentAdapter(getContext(), asmListByClassId);
        rec_Assignment.setAdapter(asmAdapter);
        retrieveTasksClass(classId);
    }

    public void showDialogFailed() {

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_faild);
        dialog.show();
        btnOkFail = dialog.findViewById(R.id.btnOk);
        tvFailed = dialog.findViewById(R.id.tvFailed);

        tvFailed.setText("Class not found");

        btnOkFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
