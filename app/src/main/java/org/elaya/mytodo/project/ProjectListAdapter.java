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

class ProjectListAdapter extends CursorAdapter {

    private final int numFinishedIndex;
    private final int numActiveIndex;
    private final int numNotActiveIndex;


    public ProjectListAdapter(Context pContext,@NonNull Cursor pCursor){
        super(pContext,pCursor,0);

        numFinishedIndex=pCursor.getColumnIndex("num_finished");
        numActiveIndex=pCursor.getColumnIndex("num_active");
        numNotActiveIndex=pCursor.getColumnIndex("num_not_active");
    }

    public void bindView(@NonNull View pView, Context pContext, @NonNull Cursor pCursor)
    {

        ProjectItem lProject=new ProjectItem(pCursor);

        TextView lProjectWidget= pView.findViewById(R.id.projectName);
        lProjectWidget.setText(lProject.getProjectName());

        TextView lNumFinishedElement= pView.findViewById(R.id.numFinished);
        lNumFinishedElement.setText(pCursor.getString(numFinishedIndex));

        TextView lNumActiveElement= pView.findViewById(R.id.numActive);
        lNumActiveElement.setText(pCursor.getString(numActiveIndex));

        TextView lNumNotActiveElement= pView.findViewById(R.id.numNotActive);
        lNumNotActiveElement.setText(pCursor.getString(numNotActiveIndex));

        pView.setTag(lProject);
    }

    public View newView(Context pContext,Cursor pCursor,ViewGroup pViewGroup)
    {
        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(R.layout.project_item,pViewGroup,false);
    }
}
