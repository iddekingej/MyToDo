package org.elaya.mytodo.filter;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Created by jeroen on 11/22/17.
 */

public class FilterItem {
    private long   id;
    private String name;
    private long   dateFilter;
    public static final String F_TABLE_NAME="filters";
    public static final String F_ID="_id";
    public static final String F_NAME="name";
    public static final String F_DATE_FILTER="date_filter";

    public FilterItem(@NonNull Cursor pCursor)
    {
        int lIdx=pCursor.getColumnIndex(F_ID);
        id=pCursor.getLong(lIdx);
        lIdx=pCursor.getColumnIndex(F_NAME);
        name=pCursor.getString(lIdx);
        lIdx=pCursor.getColumnIndex(F_DATE_FILTER);
        dateFilter=pCursor.getLong(lIdx);
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public long getDateFilter()
    {
        return dateFilter;
    }
}
