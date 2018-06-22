package org.elaya.mytodo.status;

import android.database.Cursor;
import android.support.annotation.NonNull;

import org.elaya.mytodo.tools.ModelItem;

/**
 * Class representing a Status item
 */

public class StatusItem extends ModelItem {

    public static final String TABLE_NAME="status";
    public static final String F_POSITION="position";
    public static final String F_DESCRIPTION="description";
    public static final String F_ACTION_TYPE="action_type";
    public static final String F_ACTIVE="active";
    /**
     * position is status selection list
     */
    private final long position;

    /**
     * Type of action (@see ActionTypes)
     */
    private final long actionType;

    /**
     * Status description
     */
    private final String description;

    /**
     * Is status active (=selectable for new to do's
     */
    private final boolean active;


    /**
     * Setup status object
     */
    public StatusItem(@NonNull Cursor pCursor)
    {
        super(pCursor);

        position=getCursorLong(pCursor,F_POSITION);
        description=getCursorString(pCursor,F_DESCRIPTION);
        actionType=getCursorLong(pCursor,F_ACTION_TYPE);
        active=getCursorLong(pCursor,F_ACTIVE)==1;
    }

      /**
     * Get the current position of the status inside the status select type
     * @return Current position
     */
    public long getPosition(){return position;}

    /**
     * Get the action type
     *
     * @return Action type
     */
    public long getActionType(){ return actionType;}

    /**
     * Get the description of the status
     *
     * @return description of the status
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Returns true if status is active
     *
     * @return true if status is active
     */
    public boolean getActive(){ return active;}
}
