package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Used for listing all to do's belonging to a project
 */

class TodoListAdapter extends CursorAdapter {

    private final int idIndex;
    private final int idProjectIndex;
    private final int idStatusIndex;
    private final int titleIndex;
    private final int commentIndex;
    private final int statusdescIndex;
    private final int isFinishedIndex;
    private final int startDateIndex;
    private final int endDateIndex;

    public TodoListAdapter(Context pContext,@NonNull Cursor pCursor){
        super(pContext,pCursor,0);
        idIndex=pCursor.getColumnIndex("_id");
        idProjectIndex=pCursor.getColumnIndex("id_project");
        idStatusIndex=pCursor.getColumnIndex("id_status");
        titleIndex=pCursor.getColumnIndex("title");
        commentIndex=pCursor.getColumnIndex("comment");
        statusdescIndex=pCursor.getColumnIndex("statusdesc");
        isFinishedIndex=pCursor.getColumnIndex("isfinished");
        startDateIndex=pCursor.getColumnIndex("start_date");
        endDateIndex=pCursor.getColumnIndex("end_date");
    }



    @Override
    public View newView(Context pContext, Cursor pCursor, ViewGroup pViewGroup) {
        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(R.layout.todo_item, pViewGroup, false);
    }
    @Override
    public void bindView(@NonNull  View pView, Context pContext,@NonNull  Cursor pCursor) {
        long lId=pCursor.getLong(idIndex);
        long lIdProject=pCursor.getLong(idProjectIndex);
        long lIdStatus=pCursor.getLong(idStatusIndex);
        String lTitle=pCursor.getString(titleIndex);
        String lComment=pCursor.getString(commentIndex);
        String lStatus=pCursor.getString(statusdescIndex);
        TextView lTitleWidget=(TextView)pView.findViewById(R.id.title);
        lTitleWidget.setText(lTitle);
        if(pCursor.getLong(isFinishedIndex)==1){
            lTitleWidget.setBackgroundResource(R.drawable.strike);
        } else {
            lTitleWidget.setBackgroundResource(0);
        }
        Long lStartDate=null;
        if(!pCursor.isNull(startDateIndex)){
            lStartDate=pCursor.getLong(startDateIndex);
        }
        Long lEndDate=null;
        if(!pCursor.isNull(endDateIndex)){
            lEndDate=pCursor.getLong(endDateIndex);
        }
        TextView lStatusWidget=(TextView)pView.findViewById(R.id.status);
        lStatusWidget.setText(lStatus);
        pView.setTag(new TodoItem(lId,lIdProject,lIdStatus,lTitle,lComment,lStartDate,lEndDate));

    }
}
