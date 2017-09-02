package org.elaya.mytodo;

/**
 * Class representing a Status item
 */

class StatusItem {
    private final long id;
    private final long position;
    private final long actionType;
    private final String description;

    public StatusItem(long pId,long pPosition,long pActionType,String pDescription)
    {
        id=pId;
        position=pPosition;
        description=pDescription;
        actionType=pActionType;
    }

    public long getId()
    {
        return id;
    }

    public long getPosition(){return position;}
    public long getActionType(){ return actionType;}
    public String getDescription()
    {
        return description;
    }
}
