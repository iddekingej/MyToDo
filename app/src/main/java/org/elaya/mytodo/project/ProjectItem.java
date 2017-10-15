package org.elaya.mytodo.project;

/**
 * Object representing a project
 */

public class ProjectItem {
    public static final String F_ID ="_id";
    public static final String F_TABLE_NAME ="projects";
    public static final String F_PROJECTNAME ="projectname";
    public static final String F_FILTER_TYPE ="filter_type";

    private final String projectName;
    private final long id;
    private  long filterType;
    public ProjectItem(long pId,String pProjectName,long pFilterType)
    {
        projectName=pProjectName;
        filterType=pFilterType;
        id=pId;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public long getFilterType()
    {
        return filterType;
    }

    public void setFilterType(long pFilterType)
    {
        filterType=pFilterType;
    }

    public long getId()
    {
        return id;
    }
}
