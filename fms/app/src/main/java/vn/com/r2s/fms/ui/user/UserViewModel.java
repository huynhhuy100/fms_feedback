package vn.com.r2s.fms.ui.user;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.UserService;
import vn.com.r2s.fms.model.User;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<User>> userlist;
    private UserService userService;

    public UserViewModel(Application application){
        super(application);
        userService = APIUtility.getUserService();
    }

    public MutableLiveData<ArrayList<User>> getUserlist() {
        if(userlist == null){
            userlist = new MutableLiveData<ArrayList<User>>();

        }
        loadUser();
        return userlist;
    }

    private void loadUser() {
        Call<ArrayList<User>> call = userService.getUsers();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                userlist.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void deleteUser(long key){
        Call<Void> call = userService.delete(key);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loadUser();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplication().getBaseContext(), "UserViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

