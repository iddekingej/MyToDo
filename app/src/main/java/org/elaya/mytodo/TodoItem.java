package org.elaya.mytodo;

/**
 * Class representing a to do item belonging to a project
 */

public class TodoItem {
    private long id;
    private long idProject;
    private long idStatus;
    private String title;
    private String comment;

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
