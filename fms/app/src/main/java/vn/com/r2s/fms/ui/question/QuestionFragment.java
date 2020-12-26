package vn.com.r2s.fms.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.r2s.fms.Activity.QuestionActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.QuestionAdapter;
import vn.com.r2s.fms.adapter.TopicAdapter;
import vn.com.r2s.fms.model.Question;
import vn.com.r2s.fms.model.Topic;

public class QuestionFragment extends Fragment  {
    QuestionFragment questionFragment ;
    private QuestionViewModel questionViewModel ;
    private RecyclerView recyclerView ;
    private ArrayList<Question> data ;
    private QuestionAdapter adapter ;
    private Spinner spinner ;
    private ImageButton imageButtonAdd ;

    private ArrayList<Topic> dataTopic ;
    private TopicAdapter topicAdapter ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_question, container, false);
        recyclerView = root.findViewById(R.id.rcv_questions);
        imageButtonAdd = root.findViewById(R.id.imgbAddQuestion) ;
        spinner = root.findViewById(R.id.spinnerTopic) ;
        init() ;
        customSpinner();
        return root;
    }
    // update UI
    public void retrieveTasks(){
        questionViewModel.getQuestionlist().observe(getViewLifecycleOwner(), new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(ArrayList<Question> questions) {
                data.clear();
                data.addAll(questions);
                adapter.notifyDataSetChanged();
            }
        });
        // TOPIC
        questionViewModel.getTopiclist().observe(getViewLifecycleOwner(), new Observer<ArrayList<Topic>>() {
            @Override
            public void onChanged(ArrayList<Topic> topics) {
                dataTopic.clear();
                dataTopic.addAll(topics);
                topicAdapter.notifyDataSetChanged();
            }
        });
    }

    // spinner
    private void customSpinner() {
        dataTopic = new ArrayList<>();
        topicAdapter = new TopicAdapter(getContext(), R.layout.topic_item,dataTopic );
        spinner.setAdapter(topicAdapter);
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), dataTopic.get(position).toString(), Toast.LENGTH_SHORT).show();
                Log.d("dataTopic","click") ;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("dataTopic","error") ;
            }
        } );
    }

    private void init() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        data = new ArrayList<>();
        adapter = new QuestionAdapter(getContext(), data, questionFragment);
        recyclerView.setAdapter(adapter);

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), QuestionActivity.class));
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        retrieveTasks();
    }

}