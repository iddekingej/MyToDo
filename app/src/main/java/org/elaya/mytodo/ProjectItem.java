package org.elaya.mytodo;

/**
 * Object representing a project
 */

class ProjectItem {

    private final String projectName;
    private final long id;

    public ProjectItem(long pId,String pProjectName)
    {
        projectName=pProjectName;
        id=pId;
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
