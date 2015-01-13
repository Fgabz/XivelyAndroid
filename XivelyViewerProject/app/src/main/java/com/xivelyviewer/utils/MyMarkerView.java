package com.xivelyviewer.utils;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MarkerView;
import com.xivelyviewer.R;

/**
 * Created by Fanilo on 13/01/2015.
 */
public class MyMarkerView extends MarkerView
{
    private TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override public void refreshContent(Entry e, int dataSetIndex)
    {
        tvContent.setText(" " + e.getData().toString());

    }
}
