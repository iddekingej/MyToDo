package org.elaya.mytodo;

/**
 * Class representing a Status item
 */

public class StatusItem {
    private long id;
    private long position;
    private long actionType;
    private String description;

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
