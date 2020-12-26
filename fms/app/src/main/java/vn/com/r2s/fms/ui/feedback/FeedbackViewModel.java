package vn.com.r2s.fms.ui.feedback;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.FeedbackService;
import vn.com.r2s.fms.model.Feedback;

public class FeedbackViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Feedback>> Feedbacklist;
    private FeedbackService feedbackService;

    public FeedbackViewModel(Application application){
        super(application);
        feedbackService = APIUtility.getFeedbackService();
    }

    public MutableLiveData<ArrayList<Feedback>> getFeedbacklist() {
        if(Feedbacklist == null){
            Feedbacklist = new MutableLiveData<>();

        }
        loadFeedback();
        return Feedbacklist;
    }

    private void loadFeedback() {
        Call<ArrayList<Feedback>> call = feedbackService.getFB();
        call.enqueue(new Callback<ArrayList<Feedback>>() {
            @Override
            public void onResponse(Call<ArrayList<Feedback>> call, Response<ArrayList<Feedback>> response) {
                Feedbacklist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Feedback>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "FeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void deleteFeedback(long key){
        Call<Void> call = feedbackService.deletefb(key);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadFeedback();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "FeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
