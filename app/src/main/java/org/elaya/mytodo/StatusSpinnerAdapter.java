package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Adapter used for filling the Status Dropdown list.
 */

public class StatusSpinnerAdapter extends StatusBaseAdapter {

    public StatusSpinnerAdapter(Context pContext,@NonNull Cursor pCursor) {
        super(pContext, pCursor);
    }

    @Override
    public View newView(Context pContext, Cursor pCursor, ViewGroup pViewGroup) {
        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(R.layout.status_spinner,pViewGroup,false);
    }

    @Override
    protected void fillView(@NonNull View pView, @NonNull  StatusItem pStatus) {
        TextView lView=(TextView)(pView.findViewById(R.id.description));
        lView.setText(pStatus.getDescription());

    }

}
