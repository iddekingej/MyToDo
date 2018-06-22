package org.elaya.mytodo.project;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Object representing a project
 */

public class ProjectItem {
    private static final String F_ID ="_id";
    public static final String F_TABLE_NAME ="projects";
    public static final String F_PROJECT_NAME ="projectname";


    private final String projectName;
    private final long id;
    public ProjectItem( Cursor pCursor)
    {
        int lIndex=pCursor.getColumnIndex(F_ID);
        id=pCursor.getLong(lIndex);
        lIndex=pCursor.getColumnIndex(F_PROJECT_NAME);
        projectName=pCursor.getString(lIndex);


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
