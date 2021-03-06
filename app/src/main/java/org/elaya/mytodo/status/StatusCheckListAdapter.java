package org.elaya.mytodo.status;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;

import org.elaya.mytodo.R;

import java.util.Set;

/**
 * This adapter is for a list of status and with checkboxes
 */

public class StatusCheckListAdapter extends StatusBaseAdapter {

    private final Set<Long> enabledStatusSet;

    public StatusCheckListAdapter(Context pContext,@NonNull Cursor pCursor, Set<Long> pEnabledStatusSet)
    {
        super(pContext,pCursor);
        enabledStatusSet=pEnabledStatusSet;
    }

    /**
     * Get View resource
     *
     * @return View resource used in list
     */

    protected int getViewResource()
    {
        return R.layout.list_statuscheck;
    }

    @Override
    protected void fillView(@NonNull  View pView,@NonNull StatusItem pStatus) {
        CheckBox lCheck= pView.findViewById(R.id.statusCheck);
        lCheck.setText(pStatus.getDescription());
        if(enabledStatusSet.contains(pStatus.getId())){
            lCheck.setChecked(true);
        }
    }


}
