package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Adapter for project list
 */

class ProjectListAdapter extends CursorAdapter {
    private final int idIndex;
    private final int projectNameIndex;
    private final int numFinishedIndex;
    private final int numActiveIndex;
    private final int numNotActiveIndex;

    public ProjectListAdapter(Context pContext,Cursor pCursor, View pParent){
        super(pContext,pCursor,0);
        idIndex=pCursor.getColumnIndex("_id");
        projectNameIndex=pCursor.getColumnIndex("projectname");
        numFinishedIndex=pCursor.getColumnIndex("num_finished");
        numActiveIndex=pCursor.getColumnIndex("num_active");
        numNotActiveIndex=pCursor.getColumnIndex("num_not_active");
    }

    public void bindView(View pView,Context pContext,Cursor pCursor)
    {
        long lId=pCursor.getLong(idIndex);
        String lProjectName=pCursor.getString(projectNameIndex);
        TextView lProjectWidget=(TextView)(pView.findViewById(R.id.projectName));
        lProjectWidget.setText(lProjectName);

        TextView lNumFinishedElement=(TextView)(pView.findViewById(R.id.numFinished));
        lNumFinishedElement.setText(pCursor.getString(numFinishedIndex));

        TextView lNumActiveElement=(TextView)(pView.findViewById(R.id.numActive));
        lNumActiveElement.setText(pCursor.getString(numActiveIndex));

        TextView lNumNotActiveElement=(TextView)(pView.findViewById(R.id.numNotActive));
        lNumNotActiveElement.setText(pCursor.getString(numNotActiveIndex));

        pView.setTag(new ProjectItem(lId,lProjectName));
    }

    public View newView(Context pContext,Cursor pCursor,ViewGroup pViewGroup)
    {
        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(R.layout.project_item,pViewGroup,false);
    }
}
