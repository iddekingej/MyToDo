package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CursorAdapter;

/**
 * Created by jeroen on 9/1/17.
 */

public abstract class StatusBaseAdapter extends CursorAdapter {
    protected final int idIndex;
    protected final int positionIndex;
    protected final int actionTypeIndex;
    protected final int descriptionIndex;

    public StatusBaseAdapter(Context pContext, Cursor pCursor) {
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

    abstract protected void fillView(View pView,StatusItem pStatus);
}
