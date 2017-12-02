package org.elaya.mytodo.filter;


import android.support.annotation.NonNull;

/**
 * Class representing a user defined filter (filter/filter_status tables)
 */

public class FilterFilterSelection implements FilterSelection {

    private final FilterItem filter;

    public FilterFilterSelection(FilterItem pFilter)
    {
        filter=pFilter;
    }

    @Override
    @NonNull public String getCondition() {

        String lCondition="(t.id_status in (select fs.id_status from filter_status fs where fs.id_filter="+Long.toString(filter.getId())+"))";
        if(filter.getDateFilter()==DateFilter.DF_AFTER_START){
            lCondition ="("+lCondition+ "or (t.start_date<=strftime('%s','now')))";
        } else if(filter.getDateFilter()==DateFilter.DF_AFTER_END){
            lCondition = "(" + lCondition+ "or (t.end_date<=strftime('%s','now')))";
        }
        return lCondition;
    }

    public String toString()
    {
        return filter.getName();
    }
}
