package vn.com.r2s.fms.ui.classic;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.ClassicService;
import vn.com.r2s.fms.model.Classic;

public class ClassViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Classic>> classlist;
    private ClassicService classicService;

    public ClassViewModel(Application application) {
        super(application);
        classicService = APIUtility.getClassicService();
    }

    public MutableLiveData<ArrayList<Classic>> getClasslist() {
        if (classlist == null) {
            classlist = new MutableLiveData<ArrayList<Classic>>();

        }
        loadClass();
        return classlist;
    }

    private void loadClass() {
        Call<ArrayList<Classic>> call = classicService.getAllClass();
        call.enqueue(new Callback<ArrayList<Classic>>() {
            @Override
            public void onResponse(Call<ArrayList<Classic>> call, Response<ArrayList<Classic>> response) {
                classlist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Classic>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteClass(int key) {
        Call<Void> call = classicService.deleteClass(key);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadClass();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

