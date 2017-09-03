package org.elaya.mytodo;

import android.app.Activity;

/**
 * Action types used in the status table
 */

class ActionTypes {
    private static final Integer[] actionTypesId={ R.string.at_not_started,R.string.at_not_active,R.string.at_started,R.string.at_finished,R.string.at_removed};
    private static String[] actionTypesCache =null;
    public static final long NOT_STARTED=0;
    public static final long NOT_ACTIVE=1;
    public static final long STARTED=2;
    public static final long FINISHED=3;
    public static final long REMOVED=4;
    public static final long MAX=4;
    public static  String[] getActionTypes(Activity pActivity){
        if(actionTypesCache == null){
            actionTypesCache =new String[actionTypesId.length];

            for(int lCnt=0;lCnt<actionTypesId.length;lCnt++){
                actionTypesCache[lCnt]= pActivity.getResources().getString(actionTypesId[lCnt]);
            }
        }
        return actionTypesCache;
    }

    private ActionTypes()
    {

    }
}
