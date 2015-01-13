//package com.xivelyviewer.services;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//import android.support.v4.app.NotificationCompat;
//
//import com.xivelyviewer.MainActivity;
//import com.xivelyviewer.R;
//import com.xivelyviewer.fragments.DataFragment;
//import com.xivelyviewer.models.FeedResponse;
//
//import java.util.LinkedHashMap;
//
//import retrofit.Callback;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//
//public class FeedService extends Service
//{
//
//    private String mFeedId = "FEED_ID";
//    private String mApiKey = "API_KEY";
//    private final IFeedService feedService;
//
//    public FeedService()
//    {
//        this.feedService = new RestAdapter.Builder()
//                .setEndpoint("https://api.xively.com")
//                .build()
//                .create(IFeedService.class);
//    }
//
//    @Override
//    public void onCreate()
//    {
//        handleService(mFeedId, mApiKey);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId)
//    {
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//
//    private void handleService(String param1, String param2)
//    {
//        feedService.getUserFeed(param2, param1, new Callback<FeedResponse>()
//        {
//            @Override public void success(FeedResponse feedResponse, Response response)
//            {
//                for (LinkedHashMap<Object, Object> o : feedResponse.getDatastreams())
//                {
//                    if (o.get("id").equals("Hygro.Buranderie2"))
//                    {
//                        final String name = o.get("id").toString();
//                        if (Float.valueOf(o.get("current_value").toString()) >= 60f)
//                        {
//                            final String value = o.get("current_value").toString();
//                            //createNotification(name, value);
//                        }
//                    }
//                }
//            }
//
//            @Override public void failure(RetrofitError error)
//            {
//
//            }
//        });
//    }
//
//
//    private void createNotification(String valueId, String value)
//    {
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle("Xively Viewer")
//                        .setContentText("Value of " + valueId + " is " + value);
//// Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, DataFragment.class);
//
//        // The stack builder object will contain an artificial back stack for the
//// started Activity.
//// This ensures that navigating backward from the Activity leads out of
//// your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//// Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(MainActivity.class);
//// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//// mId allows you to update the notification later on.
//        mNotificationManager.notify(0, mBuilder.build());
//
//
//    }
//
//    @Override
//    public IBinder onBind(Intent intent)
//    {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//}
