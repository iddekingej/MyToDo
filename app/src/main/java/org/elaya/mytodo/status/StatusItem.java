package org.elaya.mytodo.status;

import android.database.Cursor;

import org.elaya.mytodo.tools.ModelItem;

/**
 * Class representing a Status item
 */

public class StatusItem extends ModelItem {

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
    public StatusItem(Cursor pCursor)
    {
        super(pCursor);

        position=getCursorLong(pCursor,"position");
        description=getCursorString(pCursor,"description");
        actionType=getCursorLong(pCursor,"action_type");
        active=getCursorLong(pCursor,"active")==1;
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
