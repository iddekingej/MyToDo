package org.elaya.mytodo.project;

import android.support.annotation.NonNull;

/**
 * Object representing a project
 */

public class ProjectItem {
    public static final String F_ID ="_id";
    public static final String F_TABLE_NAME ="projects";
    public static final String F_PROJECTNAME ="projectname";


    private final String projectName;
    private final long id;
    public ProjectItem(long pId, @NonNull String pProjectName)
    {
        projectName=pProjectName;
        id=pId;

    }

    @NonNull
    public String getProjectName()
    {
        return projectName;
    }

    public long getId()
    {
        return id;
    }

    public String toString(){ return projectName;}
}
