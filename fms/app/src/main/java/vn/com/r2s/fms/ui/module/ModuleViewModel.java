package vn.com.r2s.fms.ui.module;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ModuleService;
import vn.com.r2s.fms.model.Module;

public class ModuleViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Module>> moduleList;
    private ModuleService moduleService;

    public ModuleViewModel(Application application) {
        super(application);
        moduleService = APIUtility.getModuleService();
    }

    public MutableLiveData<ArrayList<Module>> getModuleList() {
        if (moduleList == null) {
            moduleList = new MutableLiveData<ArrayList<Module>>();

        }
        loadModule();
        return moduleList;
    }

    private void loadModule() {
        Call<ArrayList<Module>> call = moduleService.getAllModule();
        call.enqueue(new Callback<ArrayList<Module>>() {
            @Override
            public void onResponse(Call<ArrayList<Module>> call, Response<ArrayList<Module>> response) {
                moduleList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Module>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteModule(int key) {
        Call<Void> call = moduleService.deleteModule(key);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadModule();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
