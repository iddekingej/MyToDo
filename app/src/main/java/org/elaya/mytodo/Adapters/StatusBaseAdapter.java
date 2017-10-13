package org.elaya.mytodo.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import org.elaya.mytodo.Models.StatusItem;

/**
 * Base class of @see CursorAdapter for the Status table
 */

public abstract class StatusBaseAdapter extends CursorAdapter {
    private final int idIndex;
    private final int positionIndex;
    private final int actionTypeIndex;
    private final int descriptionIndex;
    private final int activeIndex;

    public StatusBaseAdapter(Context pContext, @NonNull Cursor pCursor) {
        super(pContext,pCursor,0);
        idIndex = pCursor.getColumnIndex("_id");
        positionIndex=pCursor.getColumnIndex("position");
        actionTypeIndex=pCursor.getColumnIndex("action_type");
        descriptionIndex=pCursor.getColumnIndex("description");
        activeIndex=pCursor.getColumnIndex("active");
    }

    @Override
    public void bindView(@NonNull View pView, Context pContext,@NonNull Cursor pCursor) {
        long lId=pCursor.getLong(idIndex);
        long lPosition=pCursor.getLong(positionIndex);
        long lActionType=pCursor.getLong(actionTypeIndex);
        boolean lActive=pCursor.getLong(activeIndex)==1;
        String lDescription=pCursor.getString(descriptionIndex);
        StatusItem lStatusItem=new StatusItem(lId,lPosition,lActionType,lDescription,lActive);
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

    abstract protected int getViewResource();

    protected abstract void fillView(View pView,StatusItem pStatus);
}
