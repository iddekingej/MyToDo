package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;

import java.util.Set;

/**
 * Created by jeroen on 10/11/17.
 */

public class StatusCheckListAdapter extends StatusBaseAdapter {

    private Set<Long> enabledStatusSet;

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
    protected void fillView(View pView, StatusItem pStatus) {
        CheckBox lCheck=(CheckBox) pView.findViewById(R.id.statusCheck);
        lCheck.setText(pStatus.getDescription());
        if(enabledStatusSet.contains(pStatus.getId())){
            lCheck.setChecked(true);
        }
    }


}
