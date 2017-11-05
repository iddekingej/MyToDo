package org.elaya.mytodo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.elaya.mytodo.tools.ActionTypes;
import org.elaya.mytodo.R;
import org.elaya.mytodo.status.StatusItem;


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
    public void fillView(@NonNull View pView,@NonNull StatusItem pStatus) {
        TextView lView= pView.findViewById(R.id.description);
        TextView lPositionLabel= pView.findViewById(R.id.position);
        lView.setText(pStatus.getDescription());
        lPositionLabel.setText(String.valueOf(pStatus.getPosition()));
        TextView lActionType= pView.findViewById(R.id.actionType);
        lActionType.setText(ActionTypes.getActionTypesById(pView.getContext(),pStatus.getActionType()));
        ImageView lActiveElement= pView.findViewById(R.id.active);
        lActiveElement.setImageResource(pStatus.getActive()?R.drawable.on:R.drawable.off);

    }

}
