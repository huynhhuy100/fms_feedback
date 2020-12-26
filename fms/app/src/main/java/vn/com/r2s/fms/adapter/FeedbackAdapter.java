package vn.com.r2s.fms.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.r2s.fms.Activity.CreateFeedbackActivity;
import vn.com.r2s.fms.Activity.ReviewFeedbackActivity;
import vn.com.r2s.fms.Constants;
import vn.com.r2s.fms.MainActivity;
import vn.com.r2s.fms.R;
import vn.com.r2s.fms.api.APIUtility;
import vn.com.r2s.fms.api.FeedbackService;
import vn.com.r2s.fms.api.TypeFeedbackService;
import vn.com.r2s.fms.model.Feedback;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Feedback> Feedbacklist;
    private FeedbackService feedbackService;
    private TypeFeedbackService typeFeedbackService;
    private FeedbackAdapter adapter;
    private Dialog dialog;
    private Button btnOk, btnCancel, btnOKK;
    private TextView textview;
    String name = MainActivity.username;
    public FeedbackAdapter(Context context, ArrayList<Feedback> Feedbacklist) {
        this.context = context;
        this.Feedbacklist = Feedbacklist;
    }


    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        feedbackService = APIUtility.getFeedbackService();
        typeFeedbackService = APIUtility.getTypeFeedbackService();
        adapter = new FeedbackAdapter(context, Feedbacklist);
        context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.feedback_item, parent, false);
        FeedbackAdapter.ViewHolder viewHolder = new FeedbackAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int i) {
        holder.tvfbid.setText(String.valueOf(Feedbacklist.get(i).getFeedbackId()));
        holder.tvadminid.setText(String.valueOf(Feedbacklist.get(i).getAdminId()));
        holder.tvtitle.setText(String.valueOf(Feedbacklist.get(i).getTitle()));

    }


    @Override
    public int getItemCount() {
        if (Feedbacklist == null) {
            return 0;
        }
        return Feedbacklist.size();
    }

    public void setTasks(ArrayList<Feedback> Feedbacklist) {
        this.Feedbacklist = Feedbacklist;
        notifyDataSetChanged();
    }

    public ArrayList<Feedback> getTasks() {
        return Feedbacklist;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvfbid, tvadminid, tvtitle;
        Button imbtnEdit, imbtndDelete,imbtneyes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvfbid = itemView.findViewById(R.id.tvfbid);
            tvadminid = itemView.findViewById(R.id.tvadminid);
            tvtitle = itemView.findViewById(R.id.tvfbtitle);
            imbtnEdit = itemView.findViewById(R.id.imgEdit);
            imbtndDelete = itemView.findViewById(R.id.imgDelete);
            imbtneyes = itemView.findViewById(R.id.eyes);
            imbtneyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long elementId = Feedbacklist.get(getAdapterPosition()).getFeedbackId();
                    Intent i = new Intent(context, CreateFeedbackActivity.class);
                    i.putExtra(Constants.VIEW, elementId);
                    context.startActivity(i);
                }
            });
            imbtnEdit.setOnClickListener(v -> {
                long elementId = Feedbacklist.get(getAdapterPosition()).getFeedbackId();
                Intent i = new Intent(context, ReviewFeedbackActivity.class);
                i.putExtra(Constants.UPDATE_FEEDBACK_ID, elementId);
                context.startActivity(i);
            });

            imbtndDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getAdapterPosition();
                    dialogDelete(id);

                }
            });

        }
    }
    private void dialogDelete(final int idd) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification);
        dialog.show();
        btnOk = dialog.findViewById(R.id.btnOk);
        btnCancel = dialog.findViewById(R.id.btn_Cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mFeedbackId = Feedbacklist.get(idd).getFeedbackId();
                Call<Void> call = feedbackService.deletefb(mFeedbackId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        Toast.makeText(context, "Delete Feedback successful", Toast.LENGTH_SHORT).show();
                        loadFeedback();
                        showDialogDelSuccess("Deleted Success");
                        setTasks(Feedbacklist);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void loadFeedback() {
        Call<ArrayList<Feedback>> calls = feedbackService.getFB();
        calls.enqueue(new Callback<ArrayList<Feedback>>() {
            @Override
            public void onResponse(Call<ArrayList<Feedback>> calls, Response<ArrayList<Feedback>> response) {
                Feedbacklist.clear();
                Feedbacklist.addAll(response.body());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Feedback>> calls, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "FeedbackViewModel: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        notifyDataSetChanged();
    }
    public void showDialogDelSuccess(String str) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success);
        btnOKK = dialog.findViewById(R.id.btnSuccess) ;
        textview = dialog.findViewById(R.id.textview);
        textview.setText(str);
        dialog.show();
        btnOKK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

    }
}