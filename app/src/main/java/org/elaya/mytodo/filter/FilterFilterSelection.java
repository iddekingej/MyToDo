package org.elaya.mytodo.filter;


import android.support.annotation.NonNull;

/**
 * Created by jeroen on 11/26/17.
 */

public class FilterFilterSelection implements FilterSelection {

    private final FilterItem filter;

    public FilterFilterSelection(FilterItem pFilter)
    {
        filter=pFilter;
    }

    @Override
    @NonNull public String getCondition(String[] pData) {
        return "";
    }

    public String toString()
    {
        return filter.getName();
    }
}
