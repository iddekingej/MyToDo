package org.elaya.mytodo.status;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import org.elaya.mytodo.status.StatusItem;

/**
 * Base class of @see CursorAdapter for the Status table
 */

abstract class StatusBaseAdapter extends CursorAdapter {


    StatusBaseAdapter(Context pContext, @NonNull Cursor pCursor) {
        super(pContext,pCursor,0);
    }

    @Override
    public void bindView(@NonNull View pView, Context pContext,@NonNull Cursor pCursor) {

        StatusItem lStatusItem=new StatusItem(pCursor);
        pView.setTag(lStatusItem);
        fillView(pView,lStatusItem);
    }

    /**
     * Create view for each item displayed in list.
     *
     * @param pContext    Context in which the list is used
     * @param pCursor     Cursor used in this list
     * @param pViewGroup  Parent of the new view
     * @return            Return a new view
     */
    @Override
    public View newView(Context pContext, Cursor pCursor, ViewGroup pViewGroup) {
        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(getViewResource(),pViewGroup,false);
    }

    protected abstract int getViewResource();

    protected abstract void fillView(View pView,StatusItem pStatus);
}
