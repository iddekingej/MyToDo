package org.elaya.mytodo.tools;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.elaya.mytodo.R;

/**
 * Action types used in the status table
 */

public final class ActionTypes {
    private static final Integer[] actionTypesId={ R.string.at_not_started,R.string.at_not_active,R.string.at_started,R.string.at_finished,R.string.at_removed};
    @Nullable
    private static String[] actionTypesCache =null;
    public static final long NOT_STARTED=0;
    public static final long NOT_ACTIVE=1;
    public static final long STARTED=2;
    public static final long FINISHED=3;
    public static final long REMOVED=4;
    public static final long MAX=4;
    @Nullable
    public static  String[] getActionTypes(@NonNull Activity pActivity){
        if(actionTypesCache == null){
            actionTypesCache =new String[actionTypesId.length];

            for(int lCnt=0;lCnt<actionTypesId.length;lCnt++){
                actionTypesCache[lCnt]= pActivity.getResources().getString(actionTypesId[lCnt]);
            }
        }
        return actionTypesCache;
    }

    public static String getActionTypesById(@NonNull  Context pContext, long pId)
    {
        if(pId< actionTypesId.length){
            return pContext.getResources().getString(actionTypesId[(int)pId]);
        }
        return null;
    }

    private ActionTypes()
    {

    }
}
