package vn.com.r2s.fms.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import vn.com.r2s.fms.Activity.UserActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.UserAdapter;
import vn.com.r2s.fms.model.User;

public class UserFragment extends Fragment {

    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private ArrayList<User> data;
    private UserAdapter adapter;
    private FloatingActionButton faAdd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        recyclerView = root.findViewById(R.id.rvUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        faAdd = root.findViewById(R.id.faAdd);
        faAdd.setOnClickListener(view -> startActivity(new Intent(getContext()
                , UserActivity.class)));

        data = new ArrayList<>();
        adapter = new UserAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);
        retrieveTasks();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0
                , ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder
                    , @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                List<User> tasks = adapter.getTasks();
                User user = tasks.get(position);
                userViewModel.deleteUser(user.getId());
                retrieveTasks();
            }
        }).attachToRecyclerView(recyclerView);

        return root;
    }
    private void retrieveTasks(){
        userViewModel.getUserlist().observe(getViewLifecycleOwner(), users -> {
            data.clear();
            data.addAll(users);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        retrieveTasks();
    }
}