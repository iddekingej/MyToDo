package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CursorAdapter;

/**
 * Base class of @see CursorAdapter for the Status table
 */

abstract class StatusBaseAdapter extends CursorAdapter {
    private final int idIndex;
    private final int positionIndex;
    private final int actionTypeIndex;
    private final int descriptionIndex;

    StatusBaseAdapter(Context pContext, Cursor pCursor) {
        super(pContext,pCursor,0);
        idIndex = pCursor.getColumnIndex("_id");
        positionIndex=pCursor.getColumnIndex("position");
        actionTypeIndex=pCursor.getColumnIndex("action_type");
        descriptionIndex=pCursor.getColumnIndex("description");
    }

    @Override
    public void bindView(View pView, Context pContext, Cursor pCursor) {
        long lId=pCursor.getLong(idIndex);
        long lPosition=pCursor.getLong(positionIndex);
        long lActionType=pCursor.getLong(actionTypeIndex);
        String lDescription=pCursor.getString(descriptionIndex);
        StatusItem lStatusItem=new StatusItem(lId,lPosition,lActionType,lDescription);
        pView.setTag(lStatusItem);
        fillView(pView,lStatusItem);
    }

    protected abstract void fillView(View pView,StatusItem pStatus);
}
