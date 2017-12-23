package org.elaya.mytodo.filter;

import android.content.Context;
import android.support.annotation.Nullable;

import org.elaya.mytodo.R;

import java.util.ArrayList;
import java.util.List;


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

    @Nullable
    public static FilterSelection getCurrentFilter()
    {
        return currentFilter;
    }

    public static FilterSelection makeAllSelection(Context pContext)
    {
        return new FilterConditionSelection(pContext.getString(R.string.ft_all),"",false);
    }

    public static int getSelected(List<FilterSelection> pList){
        if(null != currentFilter) {
            int lCnt=0;
            for(FilterSelection lItem:pList){
                if(lItem.isSameKind(currentFilter)){
                    return lCnt;
                }
                lCnt++;
            }
        }
        return 0;
    }
}
