package com.xivelyviewer.services;

import com.xivelyviewer.models.FeedResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by Fanilo on 01/01/2015.
 */
public interface IFeedService
{
    @GET("/v2/feeds/{feed_id}")
    public void getUserFeed(
            @Path("feed_id") final String id,
            @Header("X-ApiKey") String apiKey,
            Callback<FeedResponse> callback);
}
