package vn.com.r2s.fms.ui.typefeedback;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.MutableLiveData;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;

import vn.com.r2s.fms.api.TypeFeedbackService;

import vn.com.r2s.fms.model.TypeFeedback;

public class TypeFeedbackViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<TypeFeedback>> TypeFeedbacklist;
    private TypeFeedbackService typeFeedbackService;

    public TypeFeedbackViewModel(Application application){
        super(application);
        typeFeedbackService = APIUtility.getTypeFeedbackService();
    }

    public MutableLiveData<ArrayList<TypeFeedback>> getTFB() {
        if(TypeFeedbacklist == null){
            TypeFeedbacklist = new MutableLiveData<ArrayList<TypeFeedback>>();

        }
        loadTypeFeedback();
        return TypeFeedbacklist;
    }

    private void loadTypeFeedback() {
        Call<ArrayList<TypeFeedback>> call = typeFeedbackService.getTFB();
        call.enqueue(new Callback<ArrayList<TypeFeedback>>() {
            @Override
            public void onResponse(Call<ArrayList<TypeFeedback>> call, Response<ArrayList<TypeFeedback>> response) {
                TypeFeedbacklist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<TypeFeedback>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "TypeFeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void deleteTypeFeedback(long key){
        Call<Void> call = typeFeedbackService.deletetfb(key);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadTypeFeedback();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "TypeFeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

