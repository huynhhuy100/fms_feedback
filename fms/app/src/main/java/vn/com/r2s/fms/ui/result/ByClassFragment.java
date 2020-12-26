package vn.com.r2s.fms.ui.result;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;
import java.util.ArrayList;

import vn.com.r2s.fms.R;


public class ByClassFragment extends Fragment {
    PieChart chart;
    TextView tvClass;
    String setLabel[] = {"%", "%", "%"};
    float getRandomValue[] = {70f, 40f, 60f};
    final int[] MY_COLORS = {Color.rgb(247,193,181),Color.rgb(245,144,122),Color.rgb(255,103,69) };
    ArrayList<Integer> colors = new ArrayList<>();
    String nameClass;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_by_class, container, false);

        Piechart(root);
        tvClass = root.findViewById(R.id.tvClassResult);

        return root;

    }

    public void init(View root) {

    }

    private void Piechart(View root) {
        chart = (PieChart) root.findViewById(R.id.pieChartbyClass);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 10);
        chart.setDragDecelerationFrictionCoef(0.92f);
        chart.setCenterText("");

        chart.setDrawHoleEnabled(false);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        chart.getLegend().setEnabled(false);


        chart.setEntryLabelColor(Color.WHITE);

        chart.setEntryLabelTextSize(11f);


        setData();

        chart.animateXY(1400, 1400);
    }

    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            if (getRandomValue[i] > 0) {
                entries.add(new PieEntry(getRandomValue[i], setLabel[i]));
                /* colors.add(pickColors[i]);*/
            }

        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        //   dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 0));
        dataSet.setSelectionShift(5f);
        for (int c : MY_COLORS) colors.add(c);
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyDecimalFormator(new DecimalFormat("###,###,###")));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }
}