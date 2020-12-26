package vn.com.r2s.fms.ui.result;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.ByPercentResultActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.adapter.SectionPageAdapter;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Classic;
import vn.com.r2s.fms.model.Module;


public class ResultFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    Button btnOverview, btnDetail;
    Spinner spinnerClass, spinnerModule;
    TextView tvClass;
    public static String classNameSP;

    private ArrayList<Classic> classics;
    private ClassicService classicService;
    private ModuleService moduleService;

    public ResultFragment() {
        classicService = APIUtility.getClassicService();
        moduleService = APIUtility.getModuleService();


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_result, container, false);

        init(root);


        tabLayout.setVisibility(View.GONE);

        getSpinnerClass();


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.array_item_topic, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerModule.setAdapter(adapter);


        //xem details
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ByPercentResultActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

    //init các view
    private void init(View root) {
        viewPager = root.findViewById(R.id.vpTest);
        tabLayout = root.findViewById(R.id.tabTest);
        btnOverview = root.findViewById(R.id.btnOverview);
        btnDetail = root.findViewById(R.id.btnDetails);
        spinnerClass = root.findViewById(R.id.spClass);
        spinnerModule = root.findViewById(R.id.spModule);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    //setViewPager

    private void setUpViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
        adapter.addFragment(new ByClassFragment(), "ByClass");
        adapter.addFragment(new ByTopicFragment(), "ByTopic");
        viewPager.setAdapter(adapter);
    }


    //get Spinner Class
    public void getSpinnerClass() {
        Call<ArrayList<Classic>> calls = classicService.getAllClass();
        calls.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> call, Response<ArrayList<Classic>> response) {
                ArrayList<Classic> classics = response.body();
                ArrayList<String> className = new ArrayList<>();
                className.clear();
                populateUIClass(classics);
                for (int i = 0; i < classics.size(); i++) {
                    className.add(classics.get(i).getClassName());
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, className);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerClass.setAdapter(adapter);
                    spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getContext(), "Bạn đã chọn: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
//                            String tag = "android:stwicher:"+R.id.vpTest+":"+1;
//                            String nameClass =adapterView.getItemAtPosition(i).toString();
//                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                            ByClassFragment byClassFragment = (ByClassFragment)fragmentManager.findFragmentByTag(tag);
//                            byClassFragment.tvClass.setText(nameClass);
                    }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Classic>> call, Throwable t) {

            }
        });
    }

    private void populateUIClass(ArrayList<Classic> classics) {
        if (classics == null) {
            return;
        }
    }

    //get Spinner Module
    public void getSpinnerModule() {
        Call<ArrayList<Module>> calls = moduleService.getAllModule();
        calls.enqueue(new Callback<ArrayList<Module>>() {
            @Override
            public void onResponse(Call<ArrayList<Module>> call, Response<ArrayList<Module>> response) {
                ArrayList<Module> modules = response.body();
                ArrayList<String> moduleName = new ArrayList<>();
                moduleName.clear();
                populateUIModule(modules);
                for (int i = 0; i < modules.size(); i++) {
                    moduleName.add(modules.get(i).getModuleName());
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, moduleName);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerModule.setAdapter(adapter);
                    spinnerModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getContext(), "Bạn đã chọn: " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            String nameClass = adapterView.getItemAtPosition(i).toString();
                            bundle.putString("nameClass",nameClass);
                            ByClassFragment byClassFragment = new ByClassFragment();
                            byClassFragment.setArguments(bundle);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Module>> call, Throwable t) {

            }
        });
    }

    private void populateUIModule(ArrayList<Module> modules) {
        if (modules == null) {
            return;
        }
    }


}