package org.elaya.mytodo.Models;

/**
 * Class representing a Status item
 */

public class StatusItem {
    /**
     * Unique ID of status
     */

    private final long id;
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
     * @param pId                Unique ID of status (primary key)
     * @param pPosition          Position inside status selection list
     * @param pActionType        Action type
     * @param pDescription       Status description
     * @param pActive            true=>Status is active (selectable for new to do's)
     */
    public StatusItem(long pId,long pPosition,long pActionType,String pDescription,boolean pActive)
    {
        id=pId;
        position=pPosition;
        description=pDescription;
        actionType=pActionType;
        active=pActive;
    }

    /**
     * Get unique ID (pk) of status
     * @return
     */
    public long getId()
    {
        return id;
    }

    /**
     * Get the current position of the status inside the status select type
     * @return
     */
    public long getPosition(){return position;}

    /**
     * Get the action type
     * @return
     */
    public long getActionType(){ return actionType;}

    /**
     * Get the description of the status
     * @return
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Returns true if status is active
     * @return
     */
    public boolean getActive(){ return active;}
}
