package org.elaya.mytodo.tools;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import org.elaya.mytodo.R;

/**
 * Some utility functions
 */

public class Helpers {
    private Helpers()
    {

    }
    /**
     * Open help pages
     *
     * @param pFrom  Activity that requested the help page
     * @param pPage  Help page name
     */
    public static void openHelp(@NonNull Activity pFrom,@NonNull String pPage){
        Intent lIntent=new Intent(pFrom,HelpActivity.class);
        lIntent.putExtra("page",pPage);
        lIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pFrom.startActivity(lIntent);
    }

    /**
     * Open a warning message box
     * @param pActivity Activity that opened the message box
     * @param pWarning  Message to display (Is a string resource)
     */
    public static void warning(@NonNull Activity pActivity, @StringRes int pWarning) {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pActivity);
        lBuilder.setTitle(R.string.failure_title);
        lBuilder.setMessage(pWarning);
        lBuilder.setPositiveButton(R.string.ok, null);
        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

    public static void confirm(@NonNull Activity pActivity, @StringRes int pTitle,@StringRes int pYes,@StringRes int pNo, DialogInterface.OnClickListener pConfirmed)
    {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pActivity);
        lBuilder.setTitle(R.string.title_confirmation);
        lBuilder.setMessage(pTitle);
        lBuilder.setPositiveButton(pYes,pConfirmed);
        lBuilder.setNegativeButton(pNo,null);
        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

    public static void confirmDelete(@NonNull Activity pActivity, @StringRes int pTitle, DialogInterface.OnClickListener pConfirmed)
    {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(pActivity);
        lBuilder.setTitle(R.string.title_delete);
        lBuilder.setMessage(pTitle);
        lBuilder.setPositiveButton(R.string.delete,pConfirmed);
        lBuilder.setNegativeButton(R.string.keep,null);
        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

    public static void setListViewHeightToContent(ListView pView)
    {
        Adapter lAdapter=pView.getAdapter();
        int lHeight=0;
        View lItem=null;
        int lWidth= View.MeasureSpec.makeMeasureSpec(pView.getWidth(), View.MeasureSpec.UNSPECIFIED);

        for(int lCnt=0;lCnt<lAdapter.getCount();lCnt++){
            lItem=lAdapter.getView(lCnt,lItem,pView);
            if(lCnt==0) {
                lItem.setLayoutParams(new ViewGroup.LayoutParams(lWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            lItem.measure(lWidth, View.MeasureSpec.UNSPECIFIED);
            lHeight+=lItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams lParams=pView.getLayoutParams();
        lParams.height=lHeight;
        pView.setLayoutParams(lParams);
    }

}
