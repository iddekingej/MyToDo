package org.elaya.mytodo.filter;

import android.content.Context;

import org.elaya.mytodo.R;

import java.util.ArrayList;


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
        return new FilterConditionSelection(pContext.getString(R.string.ft_all),"",false);
    }

    public static int getSelected(ArrayList<FilterSelection> pList){
        if(null != currentFilter) {
            for (int lCnt = 0; lCnt < pList.size(); lCnt++) {
                if (pList.get(lCnt).isSameKind(currentFilter)) {
                    return lCnt;
                }
            }
        }
        return 0;
    }
}
