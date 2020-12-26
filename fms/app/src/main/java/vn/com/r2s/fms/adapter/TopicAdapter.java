package vn.com.r2s.fms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.com.r2s.fms.R;
import vn.com.r2s.fms.model.Topic;


public class TopicAdapter  extends BaseAdapter {
    private Context context;
    private int layout;
    List<Topic> topicListList;

    public TopicAdapter(Context context, int layout, List<Topic> topicListList) {
        this.context = context;
        this.layout = layout;
        this.topicListList = topicListList;
    }

    @Override
    public int getCount() {
        return topicListList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// Hàm khởi tạo cho các dòng hiển thị trên listview
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        convertView = inflater.inflate( layout, null );
//Khai bao
        TextView tv_name = convertView.findViewById( R.id.tvTitleTopicName );
//        TextView tv_id = convertView.findViewById( R.id.tvIDTopic );
//Gán dữ liệu khi nhâp(chọn)
        tv_name.setText(topicListList.get( position ).gettopicName() );
//        tv_id.setText(String.valueOf(topicListList.get( position ).getTopicID() + " - " ));

        return convertView;
    }
}
