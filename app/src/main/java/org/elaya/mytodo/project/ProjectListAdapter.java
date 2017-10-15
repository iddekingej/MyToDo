package org.elaya.mytodo.project;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.elaya.mytodo.R;

/**
 * Adapter for project list
 */

public class ProjectListAdapter extends CursorAdapter {
    private final int idIndex;
    private final int projectNameIndex;
    private final int numFinishedIndex;
    private final int numActiveIndex;
    private final int numNotActiveIndex;
    private final int filterTypeIndex;

    public ProjectListAdapter(Context pContext,@NonNull Cursor pCursor){
        super(pContext,pCursor,0);
        idIndex=pCursor.getColumnIndex("_id");
        projectNameIndex=pCursor.getColumnIndex(ProjectItem.F_PROJECTNAME);
        numFinishedIndex=pCursor.getColumnIndex("num_finished");
        numActiveIndex=pCursor.getColumnIndex("num_active");
        numNotActiveIndex=pCursor.getColumnIndex("num_not_active");
        filterTypeIndex=pCursor.getColumnIndex(ProjectItem.F_FILTER_TYPE);
    }

    public void bindView(@NonNull View pView, Context pContext, @NonNull Cursor pCursor)
    {
        long lId=pCursor.getLong(idIndex);
        String lProjectName=pCursor.getString(projectNameIndex);
        long   lFilterType=pCursor.getLong(filterTypeIndex);
        TextView lProjectWidget=(TextView)(pView.findViewById(R.id.projectName));
        lProjectWidget.setText(lProjectName);

        TextView lNumFinishedElement=(TextView)(pView.findViewById(R.id.numFinished));
        lNumFinishedElement.setText(pCursor.getString(numFinishedIndex));

        TextView lNumActiveElement=(TextView)(pView.findViewById(R.id.numActive));
        lNumActiveElement.setText(pCursor.getString(numActiveIndex));

        TextView lNumNotActiveElement=(TextView)(pView.findViewById(R.id.numNotActive));
        lNumNotActiveElement.setText(pCursor.getString(numNotActiveIndex));

        pView.setTag(new ProjectItem(lId,lProjectName,lFilterType));
    }

    public View newView(Context pContext,Cursor pCursor,ViewGroup pViewGroup)
    {
        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(R.layout.project_item,pViewGroup,false);
    }
}
