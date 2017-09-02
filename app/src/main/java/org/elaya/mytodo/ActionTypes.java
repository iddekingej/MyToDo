package org.elaya.mytodo;

import android.app.Activity;

/**
 * Created by jeroen on 9/1/17.
 */

public class ActionTypes {
    public final static Integer actionTypesId[]={ R.string.at_not_started,R.string.at_not_active,R.string.at_started,R.string.at_finished,R.string.at_removed};
    private static String actionTypes[]=null;
    public final long NOT_STARTED=0;
    public final long NOT_ACTIVE=1;
    public final long STARTED=2;
    public final long FINISHED=3;
    public final long REMOVED=4;

    public static  String[] getActionTypes(Activity pActivity){
        if(actionTypes == null){
            actionTypes=new String[actionTypesId.length];

            for(int lCnt=0;lCnt<actionTypesId.length;lCnt++){
                actionTypes[lCnt]= pActivity.getResources().getString(actionTypesId[lCnt]);
            }
        }
        return actionTypes;
    }

}
