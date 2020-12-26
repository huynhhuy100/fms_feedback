package vn.com.r2s.fms.ui.result;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyDecimalFormator extends ValueFormatter {
    protected DecimalFormat mFormat;
    public MyDecimalFormator(DecimalFormat Format) {
        this.mFormat = Format;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value);
    }
}
