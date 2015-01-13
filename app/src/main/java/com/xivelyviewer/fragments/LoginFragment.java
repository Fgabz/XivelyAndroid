package com.xivelyviewer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.xivelyviewer.R;
import com.xivelyviewer.models.FeedResponse;
import com.xivelyviewer.services.IFeedService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginFragment extends Fragment
{

    private static final String TAG = "LoginFragment";
    @InjectView(R.id.feedid) MaterialEditText feedId;
    @InjectView(R.id.email_sign_in_button) ButtonRectangle email_sign_in_button;
    @InjectView(R.id.message) TextView message;
    @InjectView(R.id.apifield) MaterialEditText apiKey;
    private final IFeedService feedService;





    public static LoginFragment newInstance()
    {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    public LoginFragment()
    {
        this.feedService = new RestAdapter.Builder()
                .setEndpoint("https://api.xively.com")
                .build()
                .create(IFeedService.class);
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);

        // Inflate the layout for this fragment
        return view;
    }

    @OnClick(R.id.email_sign_in_button)
    public void submit()
    {

        message.setText("Getting the feeds...");

        feedService.getUserFeed("859455437", "Rm6s56YK2Wj6Irz8KveLwfa4N1ddVEM3qCrgbRUrWidXGB2a",
                new Callback<FeedResponse>() {
//        feedService.getUserFeed(feedId.getText().toString(), apiKey.getText().toString(),
//                new Callback<FeedResponse>() {
            @Override public void success(FeedResponse feedResponse, Response response)
            {
                if (feedResponse.getErrors() == null)
                {
                    getFragmentManager()
                            .beginTransaction().remove(LoginFragment.this)
                            .replace(R.id.container,
                                    DataFragment.newInstance("Rm6s56YK2Wj6Irz8KveLwfa4N1ddVEM3qCrgbRUrWidXGB2a",
                                            "859455437"))
                            .addToBackStack("xively")
                            .commit();
                }
                else
                    message.setText(feedResponse.getErrors().toString());
            }

            @Override public void failure(RetrofitError error)
            {
                Log.d(TAG, "failure : " + error.getMessage());
                message.setText(error.toString());
            }
        });
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
