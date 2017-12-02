package org.elaya.mytodo.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.elaya.mytodo.R;

/**
 * FilterTypes
 */

final class FilterTypes {
     private static final int[] filterResources={R.string.ft_all,R.string.ft_custom,R.string.ft_active_todo,R.string.ft_to_late,R.string.ft_should_start,R.string.ft_finished};

    /**
     * This class ise only used static, so hide constructor.
     */
    private FilterTypes()
    {

    }

    @NonNull
    private static String[] getFilterTypes(@NonNull Context pContext){
        String[] filters=new String[filterResources.length];
        for(int lCnt=0;lCnt<filterResources.length;lCnt++){
            filters[lCnt]=pContext.getResources().getString(filterResources[lCnt]);
        }
        return filters;
    }

    public static void setSpinner(@NonNull Context pContext, @NonNull Spinner pSpinner)
    {
        ArrayAdapter<String> lBuildInFilterAdapter=new ArrayAdapter<>(pContext,R.layout.support_simple_spinner_dropdown_item,FilterTypes.getFilterTypes(pContext));
        pSpinner.setAdapter(lBuildInFilterAdapter);
    }
}
