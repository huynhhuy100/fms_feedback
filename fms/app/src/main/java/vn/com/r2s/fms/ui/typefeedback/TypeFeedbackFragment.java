package vn.com.r2s.fms.ui.typefeedback;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;


import vn.com.r2s.fms.Activity.TypeFeedbackActivity;
import vn.com.r2s.fms.R;

import vn.com.r2s.fms.adapter.TypeFeedbackAdapter;
import vn.com.r2s.fms.model.TypeFeedback;


public class TypeFeedbackFragment extends Fragment {

    private TypeFeedbackViewModel typeFeedbackViewModel;
    private RecyclerView recyclerView;
    private ArrayList<TypeFeedback> TypeFeedbacklist;
    private TypeFeedbackAdapter adapter;
    private Button Addtfb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        typeFeedbackViewModel = ViewModelProviders.of(this).get(TypeFeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_typefeedback, container, false);

        recyclerView = root.findViewById(R.id.rvTypeFeedback);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Addtfb = root.findViewById(R.id.Addtfb);
        Addtfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TypeFeedbackActivity.class));
            }
        });

        TypeFeedbacklist = new ArrayList<>();
        adapter = new TypeFeedbackAdapter(getContext(), TypeFeedbacklist);
        recyclerView.setAdapter(adapter);
        retrieveTasks();




        return root;
    }
    private void retrieveTasks(){
            typeFeedbackViewModel.getTFB().observe(getViewLifecycleOwner(), typeFeedbacklist -> {
            TypeFeedbacklist.clear();
            TypeFeedbacklist.addAll(typeFeedbacklist);
            adapter.notifyDataSetChanged();
    });
    }

    @Override
    public void onResume(){
        super.onResume();
        retrieveTasks();
    }
}
