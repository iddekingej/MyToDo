package org.elaya.mytodo;

/**
 * Class representing a to do item belonging to a project
 */

class TodoItem {
    private final long id;
    private final long idProject;
    private final long idStatus;
    private final String title;
    private final String comment;

    public TodoItem(long pId,long pIdProject,long pIdStatus,String pTitle,String pComment)
    {
        id=pId;
        idProject=pIdProject;
        idStatus=pIdStatus;
        title=pTitle;
        comment=pComment;
    }

    public long getId()
    {
        return id;
    }

    public long getIdStatus() { return idStatus; }
    public long getIdProject()
    {
        return idProject;
    }

    public String getTitle()
    {
        return title;
    }

    public String getComment()
    {
        return comment;
    }
}
