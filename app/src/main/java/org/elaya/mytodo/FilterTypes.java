package org.elaya.mytodo;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * FilterTypes
 */

final class FilterTypes {
    public static final int FT_NONE=0;
    public static final int FT_CUSTOM=1;
    private static final int[] filterResources={R.string.ft_all,R.string.ft_custom,R.string.ft_active_todo,R.string.ft_to_late,R.string.ft_should_start,R.string.ft_finished};

    private static String[] getFilterTypes(Context pContext){
        String filters[]=new String[filterResources.length];
        for(int lCnt=0;lCnt<filterResources.length;lCnt++){
            filters[lCnt]=pContext.getResources().getString(filterResources[lCnt]);
        }
        return filters;
    }

    static void setSpinner(Context pContext, Spinner pSpinner)
    {
        ArrayAdapter<String> lBuildInFilterAdapter=new ArrayAdapter<>(pContext,R.layout.support_simple_spinner_dropdown_item,FilterTypes.getFilterTypes(pContext));
        pSpinner.setAdapter(lBuildInFilterAdapter);
    }
}
