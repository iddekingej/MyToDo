package org.elaya.mytodo.status;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import org.elaya.mytodo.R;

/**
 * Adapter used for filling the Status Dropdown list.
 */

public class StatusSpinnerAdapter extends StatusBaseAdapter {

    public StatusSpinnerAdapter(Context pContext,@NonNull Cursor pCursor) {
        super(pContext, pCursor);
    }
    
    @Override
    protected int getViewResource() {
        return R.layout.status_spinner;
    }

    @Override
    protected void fillView(@NonNull View pView, @NonNull StatusItem pStatus) {
        TextView lView= pView.findViewById(R.id.description);
        lView.setText(pStatus.getDescription());

    }

}
