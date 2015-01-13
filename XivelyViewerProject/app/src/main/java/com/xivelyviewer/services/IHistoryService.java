package com.xivelyviewer.services;

import com.xivelyviewer.models.HistoryResponse;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Fanilo on 12/01/2015.
 */
public interface IHistoryService
{
    @GET("/v2/feeds/{feed_id}/datastreams/{id}")
    public HistoryResponse getHistory(
            @Path("feed_id") final String id,
            @Path("id") final String datastreamId,
            @Header("X-ApiKey") final String apiKey,
            @Query("start") final String start,
            @Query("end") final String end);
}
