package vn.com.r2s.fms.ui.traineefeedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.FeedbackAdapter;
import vn.com.r2s.fms.adapter.TraineeFeedbackAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.AssignmentService;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.EnrollmentService;
import vn.com.r2s.fms.api.FeedbackService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Feedback;
import vn.com.r2s.fms.model.Module;


public class TraineeFeedbackFragment extends Fragment {
    private TraineeFeedbackViewModel traineeFeedbackViewModel;
    private RecyclerView rvtraineeFeedback;
    private ModuleService moduleService;
    private ClassicService classicService;
    private FeedbackService feedbackService;
    private ArrayList<Feedback> Feedbacklist;
    private ArrayList<Module> modulelist;
    private ArrayList<Classic> classiclist;

    private TraineeFeedbackAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         traineeFeedbackViewModel = ViewModelProviders.of(this).get(TraineeFeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_traineefeedback, container, false);
        rvtraineeFeedback = root.findViewById(R.id.rvtraineeFeedback);
        feedbackService = APIUtility.getFeedbackService();
        classicService = APIUtility.getClassicService();
        moduleService = APIUtility.getModuleService();
        rvtraineeFeedback.setHasFixedSize(true);
        rvtraineeFeedback.setLayoutManager(new LinearLayoutManager(getContext()));
        Feedbacklist = new ArrayList<>();
        modulelist = new ArrayList<>();
        classiclist = new ArrayList<>();
        adapter = new TraineeFeedbackAdapter(getContext(), Feedbacklist);
        rvtraineeFeedback.setAdapter(adapter);
        retrieveTasks();

        return root;
    }

    private void retrieveTasks() {
        traineeFeedbackViewModel.getFeedbacklist().observe(getViewLifecycleOwner(), feedbacklist -> {
            Feedbacklist.clear();
            Feedbacklist.addAll(feedbacklist);
            adapter.notifyDataSetChanged();
        });
        traineeFeedbackViewModel.getClasslist().observe(getViewLifecycleOwner(), Classlist -> {
            classiclist.clear();
            classiclist.addAll(Classlist);
            adapter.notifyDataSetChanged();
        });
        traineeFeedbackViewModel.getModulelist().observe(getViewLifecycleOwner(), Modulelist -> {
            modulelist.clear();
            modulelist.addAll(Modulelist);
            adapter.notifyDataSetChanged();
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }
}