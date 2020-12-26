package vn.com.r2s.fms.ui.module;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.ModuleActivity;
import vn.com.r2s.fms.Language.Language;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.ClassAdaperTrainee;
import vn.com.r2s.fms.adapter.ClassAdapterTrainer;
import vn.com.r2s.fms.adapter.ModuleAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.model.Assignment;
import vn.com.r2s.fms.model.Enrollment;
import vn.com.r2s.fms.model.Module;

public class ModuleFragment extends Fragment {
    RecyclerView rec_module;
    Button btnAddModule;
    CardView btnAddModulebr;
    ModuleViewModel moduleViewModel;
    ModuleAdapter moduleAdapter;
    private ArrayList<Module> modulelist;
    String name = MainActivity.username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_module, container, false);

        init(root);
        event();
        rec_module.setHasFixedSize(true);
        rec_module.setLayoutManager(new LinearLayoutManager(getContext()));
        modulelist = new ArrayList<>();
        moduleAdapter = new ModuleAdapter(getContext(), modulelist);
        rec_module.setAdapter(moduleAdapter);
        retrieveTasks();
        return root;
    }

    public void event() {
        if(MainActivity.typeUser.equals("Admin")){
            btnAddModulebr.setVisibility(View.VISIBLE);
            btnAddModule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ModuleActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            btnAddModulebr.setVisibility(View.INVISIBLE);
        }

}

    public void init(View root) {
        btnAddModule = root.findViewById(R.id.btnAddModule);
        rec_module = root.findViewById(R.id.rec_Module);
        btnAddModulebr = root.findViewById(R.id.btnAddModulebr);
    }

    private void retrieveTasks() {
        moduleViewModel.getModuleList().observe(getViewLifecycleOwner(), modules -> {
            modulelist.clear();
            modulelist.addAll(modules);
            moduleAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }

}
