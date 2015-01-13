package com.xivelyviewer.models;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Fanilo on 01/01/2015.
 */

@lombok.Data
public class FeedResponse
{
    private String id;
    private String title;
    private String errors;
    private List<LinkedHashMap<Object, Object>> datastreams;
}
