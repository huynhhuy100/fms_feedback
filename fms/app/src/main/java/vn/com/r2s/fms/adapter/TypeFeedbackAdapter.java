package vn.com.r2s.fms.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.TypeFeedbackActivity;
import vn.com.r2s.fms.AppExecutors;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.R;

import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.TypeFeedbackService;
import vn.com.r2s.fms.model.TypeFeedback;

public class TypeFeedbackAdapter extends RecyclerView.Adapter<TypeFeedbackAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TypeFeedback> typeFeedbacklist;
    private TypeFeedbackService typeFeedbackService;
    public TypeFeedbackAdapter(Context context, ArrayList<TypeFeedback> typeFeedbacklist){this.typeFeedbacklist = typeFeedbacklist;}


    
    @NonNull
    @Override
    public TypeFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        typeFeedbackService = APIUtility.getTypeFeedbackService();
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =  layoutInflater.inflate(R.layout.typefeedback_item,parent,false);
        TypeFeedbackAdapter.ViewHolder viewHolder = new TypeFeedbackAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TypeFeedbackAdapter.ViewHolder holder, int i) {
        holder.tvtfbid.setText(String.valueOf(typeFeedbacklist.get(i).getTypeId()));
        holder.tvtypename.setText(String.valueOf(typeFeedbacklist.get(i).getTypeName()));
    }





    @Override
    public int getItemCount() {
        if (typeFeedbacklist==null){
            return 0;
        }
        return typeFeedbacklist.size();
    }

    public void setTasks(ArrayList<TypeFeedback> typeFeedbacklist){
        this.typeFeedbacklist = typeFeedbacklist;
        notifyDataSetChanged();
    }

    public ArrayList<TypeFeedback> getTasks(){return typeFeedbacklist;}


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvtfbid, tvtypename;
        Button imbtnEdit,btndeltype;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvtfbid = itemView.findViewById(R.id.tvtfbid);
            tvtypename = itemView.findViewById(R.id.tvtypename);
            imbtnEdit = itemView.findViewById(R.id.imgEdit);
            btndeltype = itemView.findViewById(R.id.btndeltype);

            imbtnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long elementId = typeFeedbacklist.get(getAdapterPosition()).getTypeId();
                    Intent i = new Intent(context, TypeFeedbackActivity.class);
                    i.putExtra(Constants.UPDATE_TYPEFEEDBACK_ID, elementId);
                    context.startActivity(i);
                }
            });

        }
    }
}
