package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Used for displaying the list of status defined in the app
 */

public class StatusListAdapter extends StatusBaseAdapter {

    public StatusListAdapter(Context pContext, Cursor pCursor)
    {
        super(pContext,pCursor);

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
            return lInflater.inflate(R.layout.status_item,pViewGroup,false);
    }

    /**
     * Fill the view
     *
     * @param pView     View with the elements
     * @param pStatus   StatusItem used for filling the data
     */
    public void fillView(View pView, StatusItem pStatus) {
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
