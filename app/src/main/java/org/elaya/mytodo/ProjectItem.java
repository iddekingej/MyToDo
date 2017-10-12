package org.elaya.mytodo;

/**
 * Object representing a project
 */

class ProjectItem {


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
