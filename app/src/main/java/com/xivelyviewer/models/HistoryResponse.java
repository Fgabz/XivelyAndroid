package com.xivelyviewer.models;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Fanilo on 12/01/2015.
 */

@lombok.Data
public class HistoryResponse extends FeedResponse
{
    private List<LinkedHashMap<Object, Object>> datapoints;
}
