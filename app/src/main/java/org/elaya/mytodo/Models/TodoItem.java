package org.elaya.mytodo.Models;

/**
 * Class representing a to do item belonging to a project
 */

public class TodoItem {
    private final long id;
    private final long idProject;
    private final long idStatus;
    private final String title;
    private final String comment;
    private final Long startDate;
    private final Long endDate;

    public TodoItem(long pId,long pIdProject,long pIdStatus,String pTitle,String pComment,Long pStartDate,Long pEndDate)
    {
        id=pId;
        idProject=pIdProject;
        idStatus=pIdStatus;
        title=pTitle;
        comment=pComment;
        startDate=pStartDate;
        endDate=pEndDate;
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
    public Long endDate(){ return endDate;}
    public Long startDate(){return startDate;}
}
