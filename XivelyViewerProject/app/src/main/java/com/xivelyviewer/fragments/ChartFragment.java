package com.xivelyviewer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xivelyviewer.R;
import com.xivelyviewer.models.HistoryResponse;
import com.xivelyviewer.services.IHistoryService;
import com.xivelyviewer.utils.MyMarkerView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;


public class ChartFragment extends Fragment
{

    // TODO: Rename and change types of parameters
    private static String mApiKey;
    private static String mFeedId;
    private static String mId;

    private final DateTime mEndTime;
    private final DateTime mBeginTime;

    private IHistoryService serviceHistory;

    @InjectView(R.id.linechart) LineChart mLineChat;

    public static ChartFragment newInstance(String apikey, String feedid, String datastreamid)
    {
        mApiKey = apikey;
        mFeedId = feedid;
        mId = datastreamid;

        ChartFragment fragment = new ChartFragment();

        return fragment;
    }

    public ChartFragment()
    {
        this.serviceHistory = new RestAdapter.Builder()
                .setEndpoint("https://api.xively.com")
                .build()
                .create(IHistoryService.class);

        //Current local date
        mEndTime = new DateTime();
        mBeginTime = mEndTime.minusDays(5);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    private void makeRequest()
    {

        final ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        final ArrayList<Entry> valsLineWidget = new ArrayList<>();
        final ArrayList<BarEntry> valsBarWidget = new ArrayList<>();
        final ArrayList<String> xVals = new ArrayList<>();
        final int colorNumbers = 0;

        AsyncTask as = new AsyncTask()
        {
            @Override protected Object doInBackground(Object[] params)
            {
                DateTimeFormatter fmt = ISODateTimeFormat.dateTime();

                HistoryResponse response = serviceHistory.getHistory(mFeedId, mId, mApiKey, mBeginTime.toString(fmt),
                        mEndTime.toString(fmt));

                return response;
            }
        };


        try
        {
            HistoryResponse response = (HistoryResponse) as.execute().get();
            addChartData(response, valsLineWidget, valsBarWidget, xVals);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        drawLineChart(valsLineWidget, xVals,
                ColorTemplate.VORDIPLOM_COLORS[colorNumbers], dataSets);

    }

    public void drawLineChart(ArrayList<Entry> valsLineWidget, ArrayList<String> xVals,
                              int color, ArrayList<LineDataSet> dataSets)
    {
        //ONE LINE DATA = ONE LINE , in the chart
        LineDataSet setWidget = new LineDataSet(valsLineWidget, mId);

        setWidget.setColor(color);
        setWidget.setCircleColor(color);

        setWidget.setCircleSize(5f);
        setWidget.setLineWidth(3f);
        mLineChat.setDrawXLabels(false);
        MyMarkerView mv = new MyMarkerView(this.getActivity().getBaseContext(),
                R.layout.custom_marker_view);
        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        dataSets.add(setWidget);
        mLineChat.setMarkerView(mv);

        LineData data = new LineData(xVals, dataSets);
        
        mLineChat.setDrawGridBackground(false);
        mLineChat.setDrawVerticalGrid(false);
        mLineChat.setDrawHorizontalGrid(false);
        mLineChat.setDrawYValues(true);
        mLineChat.setData(data);
        mLineChat.setTouchEnabled(true);
        mLineChat.setDragEnabled(true);
        mLineChat.setScaleEnabled(true);
        mLineChat.setPinchZoom(true);
        mLineChat.animateX(2500);

        //Name of the chart
        mLineChat.setDescription(mId);
    }

    public void addChartData(HistoryResponse historyResponse, ArrayList<Entry> valsLineWidget,
                             ArrayList<BarEntry> valsBarWidget, ArrayList<String> xVals)
    {
        List<LinkedHashMap<Object, Object>> l = historyResponse.getDatapoints();

        int i = 0;
        //BEGIN PARCOURS DE LA LISTE DE POINT X, Y
        try
        {
            for (LinkedHashMap<Object, Object> d : l)
            {

                DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
                DateTime dt = formatter.parseDateTime((String) d.get("at"));

                DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:MM");
                String date = dt.toString(fmt);

                //y for LineChart
                Entry e = new Entry(Float.parseFloat((String) d.get("value")),
                        i);
                e.setData(date);
                //y for BarChart
                i++;


                valsLineWidget.add(e);//List of Y

                xVals.add(date);//List of X

            }
        }
        catch(NullPointerException exc){
            Toast.makeText(this.getActivity().getBaseContext(),
                    "No Datas for this element", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        ButterKnife.inject(this, view);

        this.makeRequest();

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }


}
