package org.elaya.mytodo;

/**
 * Object representing a project
 */

class ProjectItem {
    private String projectName;
    private final long id;

    public ProjectItem(long pId,String pProjectName)
    {
        projectName=pProjectName;
        id=pId;
    }

    public void setProjectName(String pProjectName){
        projectName=pProjectName;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public long getId()
    {
        return id;
    }
}
