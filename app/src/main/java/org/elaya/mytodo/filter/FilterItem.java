package org.elaya.mytodo.filter;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Object representing a to do filter
 */

public class FilterItem {
    private final long   id;
    private final String name;
    private final long   dateFilter;
    public static final String F_TABLE_NAME="filters";
    private static final String F_ID="_id";
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
