package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;



/**
 * Used for displaying the list of status defined in the app
 */

public class StatusListAdapter extends StatusBaseAdapter {


    public StatusListAdapter(Context pContext, @NonNull  Cursor pCursor)
    {
        super(pContext,pCursor);
    }

    /**
     * Get View resource
     *
     * @return View resource used in list
     */

    protected int getViewResource()
    {
        return R.layout.status_item;
    }


    /**
     * Fill the view
     *
     * @param pView     View with the elements
     * @param pStatus   StatusItem used for filling the data
     */
    public void fillView(@NonNull View pView,@NonNull  StatusItem pStatus) {
        TextView lView=(TextView)(pView.findViewById(R.id.description));
        TextView lPositionLabel=(TextView)(pView.findViewById(R.id.position));
        lView.setText(pStatus.getDescription());
        lPositionLabel.setText(String.valueOf(pStatus.getPosition()));
        TextView lActionType=(TextView)(pView.findViewById(R.id.actionType));
        lActionType.setText(ActionTypes.getActionTypesById(pView.getContext(),pStatus.getActionType()));
        TextView lActiveElement=(TextView)pView.findViewById(R.id.active);
        lActiveElement.setText(pStatus.getActive()?"O":"X");

    }

}
