package org.elaya.mytodo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

/**
 * Some utility functions
 */

class Helpers {
    /**
     * Open help pages
     *
     * @param pFrom  Activity that requested the help page
     * @param pPage  Help page name
     */
    public static void openHelp(Activity pFrom,String pPage){
        Intent lIntent=new Intent(pFrom,HelpActivity.class);
        lIntent.putExtra("page",pPage);
        pFrom.startActivity(lIntent);
    }

    /**
     * Open a warning message box
     * @param pActivity Activity that opened the message box
     * @param pWarning  Message to display (Is a string resource)
     */
    public static void warning(Activity pActivity,int pWarning) {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pActivity);
        lBuilder.setTitle(R.string.failure_title);
        lBuilder.setMessage(pWarning);
        lBuilder.setPositiveButton(R.string.ok, null);
        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

}
