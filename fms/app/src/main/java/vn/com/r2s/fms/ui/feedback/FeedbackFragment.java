package vn.com.r2s.fms.ui.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import vn.com.r2s.fms.Activity.ReviewFeedbackActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.FeedbackAdapter;
import vn.com.r2s.fms.model.Feedback;

public class FeedbackFragment extends Fragment {

    private FeedbackViewModel feedbackViewModel;
    private RecyclerView recyclerView;
    private ArrayList<Feedback> Feedbacklist;
    private FeedbackAdapter adapter;
    private Button Addfb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feedbackViewModel = ViewModelProviders.of(this).get(FeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);

        recyclerView = root.findViewById(R.id.rvFeedback);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Addfb = root.findViewById(R.id.Addfb);
        Addfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ReviewFeedbackActivity.class));
            }
        });


        Feedbacklist = new ArrayList<>();
        adapter = new FeedbackAdapter(getContext(), Feedbacklist);
        recyclerView.setAdapter(adapter);
        retrieveTasks();


        return root;
    }

    private void retrieveTasks() {
        feedbackViewModel.getFeedbacklist().observe(getViewLifecycleOwner(), feedbacklist -> {
            Feedbacklist.clear();
            Feedbacklist.addAll(feedbacklist);
            adapter.notifyDataSetChanged();
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        retrieveTasks();
    }
}
