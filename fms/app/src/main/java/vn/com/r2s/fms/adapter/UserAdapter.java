package vn.com.r2s.fms.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.com.r2s.fms.Activity.UserActivity;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.UserService;
import vn.com.r2s.fms.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> userList;
    private UserService userService;
    public UserAdapter(Context context, ArrayList<User> userList){this.userList = userList;}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        userService = APIUtility.getUserService();
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =  layoutInflater.inflate(R.layout.user_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tvFirstname.setText("First name: "+userList.get(i).getFirstName());
        holder.tvLastname.setText("Last name: "+userList.get(i).getLastName());
        holder.tvEmail.setText("Email: "+userList.get(i).getEmail());
    }



    @Override
    public int getItemCount() {
        if (userList==null){
            return 0;
        }
        return userList.size();
    }

    public void setTasks(ArrayList<User> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }

    public ArrayList<User> getTasks(){return userList;}


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvFirstname;
        TextView tvLastname;
        TextView tvEmail;
        ImageButton imbtnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFirstname = itemView.findViewById(R.id.tvFname);
            tvLastname = itemView.findViewById(R.id.tvLname);
            tvEmail = itemView.findViewById(R.id.tvemail);
            imbtnEdit = itemView.findViewById(R.id.imgEdit);

            imbtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long elementId = userList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, UserActivity.class);
                    i.putExtra(Constants.UPDATE_USER_ID,elementId);
                    context.startActivity(i);
                }
            });
        }
    }

}