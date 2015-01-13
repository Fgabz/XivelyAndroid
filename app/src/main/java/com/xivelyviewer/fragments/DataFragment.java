package com.xivelyviewer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xivelyviewer.R;
import com.xivelyviewer.adapters.DataAdapter;
import com.xivelyviewer.models.FeedResponse;
import com.xivelyviewer.services.IFeedService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DataFragment extends Fragment
{


    @InjectView(R.id.listView) ListView mListView;
    @InjectView(R.id.swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;
    private DataAdapter mAdatper;


    private static String mApiKey;
    private static String mFeedId;
    private String mId;
    private final IFeedService feedService;

    public static DataFragment newInstance(String apikey, String feedid)
    {
        mApiKey = apikey;
        mFeedId = feedid;
        DataFragment fragment = new DataFragment();

        return fragment;
    }

    public DataFragment()
    {
        this.feedService = new RestAdapter.Builder()
                .setEndpoint("https://api.xively.com")
                .build()
                .create(IFeedService.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mAdatper = new DataAdapter(getActivity());
        this.getDatas();
      //  this.startFeedIntentService();
    }

//    private void startFeedIntentService(){
//        Intent feedService = new Intent(getActivity(), FeedService.class);
//
//        feedService.putExtra("FEED_ID", mFeedId);
//        feedService.putExtra("API_KEY", mApiKey);
//
//        getActivity().startService(feedService);
//    }


    private void getDatas(){
        feedService.getUserFeed(mFeedId, mApiKey, new Callback<FeedResponse>()
        {
            @Override public void success(FeedResponse feedResponse, Response response)
            {
                mAdatper.clear();
                mAdatper.addAll(feedResponse.getDatastreams());
                mAdatper.notifyDataSetChanged();
            }

            @Override public void failure(RetrofitError error)
            {

            }
        });

        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        ButterKnife.inject(this, view);
        mListView.setAdapter(mAdatper);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh()
            {
                getDatas();
            }
        });

        return view;
    }

    @Override public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnItemClick(R.id.listView)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container,
                        ChartFragment.newInstance(mApiKey,
                                mFeedId, (String) mAdatper.getItem(position).get("id")))
                .addToBackStack("xively")
                .commit();
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
