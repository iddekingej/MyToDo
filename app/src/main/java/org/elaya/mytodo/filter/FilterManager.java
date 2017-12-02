package org.elaya.mytodo.filter;

import android.content.Context;

import org.elaya.mytodo.R;



/**
 * Manages To do filters
 */

public class FilterManager {
    private FilterManager()
    {

    }

    private static FilterSelection currentFilter=null;

    public static void setCurrentFilter(FilterSelection pCurrent)
    {
        currentFilter=pCurrent;
    }

    public static FilterSelection getCurrentFilter()
    {
        return currentFilter;
    }

    public static FilterSelection makeAllSelection(Context pContext)
    {
        return new FilterConditionSelection(pContext.getString(R.string.ft_all),"");
    }
}
