package org.elaya.mytodo.project;

/**
 * Object representing a project
 */

public class ProjectItem {
    public static final String F_ID ="_id";
    public static final String F_TABLE_NAME ="projects";
    public static final String F_PROJECTNAME ="projectname";
    public static final String F_FILTER_TYPE ="filter_type";
    public static final String F_DATE_FILTER ="date_filter";

    private final String projectName;
    private final long id;
    private  long filterType;
    private  long dateFilter;
    public ProjectItem(long pId,String pProjectName,long pFilterType,long pDateFilter)
    {
        projectName=pProjectName;
        filterType=pFilterType;
        dateFilter=pDateFilter;
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

    public void setDateFilter(long pDateFilter){ dateFilter=pDateFilter;}

    public long getDateFilter(){ return dateFilter;}
}
