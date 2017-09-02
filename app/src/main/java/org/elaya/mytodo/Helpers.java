package org.elaya.mytodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

/**
 * Created by jeroen on 8/31/17.
 */

public class Helpers {
    public static void openHelp(Activity pFrom,String pPage){
        Intent lIntent=new Intent(pFrom,HelpActivity.class);
        lIntent.putExtra("page",pPage);
        pFrom.startActivity(lIntent);
    }

    public static void warning(Activity pActivity,int pWarning) {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pActivity);
        lBuilder.setTitle(R.string.failure_title);
        lBuilder.setMessage(pWarning);
        lBuilder.setPositiveButton(R.string.ok, null);
        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

}
