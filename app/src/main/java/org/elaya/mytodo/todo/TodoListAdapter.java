package org.elaya.mytodo.todo;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.elaya.mytodo.R;
import org.elaya.mytodo.todo.TodoItem;
import org.elaya.mytodo.tools.DateHandler;

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
        idIndex=pCursor.getColumnIndex(TodoItem.F_ID);
        idProjectIndex=pCursor.getColumnIndex(TodoItem.F_ID_PROJECT);
        idStatusIndex=pCursor.getColumnIndex(TodoItem.F_ID_STATUS);
        titleIndex=pCursor.getColumnIndex(TodoItem.F_TITLE);
        commentIndex=pCursor.getColumnIndex(TodoItem.F_COMMENT);
        statusdescIndex=pCursor.getColumnIndex("statusdesc");
        isFinishedIndex=pCursor.getColumnIndex("isfinished");
        startDateIndex=pCursor.getColumnIndex(TodoItem.F_START_DATE);
        endDateIndex=pCursor.getColumnIndex(TodoItem.F_END_DATE);
    }



    @Override
    public View newView(Context pContext, Cursor pCursor, ViewGroup pViewGroup) {
        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(R.layout.todo_item, pViewGroup, false);
    }

    private void setDateElement(Cursor pCursor,View pView,int pElement,Long pValue)
    {
        TextView lElement=pView.findViewById(pElement);
        if(pValue==null){
            lElement.setText("");
        } else {
            lElement.setText(DateHandler.getDateFromLong(pValue));
        }
    }

    @Override
    public void bindView(@NonNull  View pView, Context pContext,@NonNull  Cursor pCursor) {
        long lId=pCursor.getLong(idIndex);
        long lIdProject=pCursor.getLong(idProjectIndex);
        long lIdStatus=pCursor.getLong(idStatusIndex);
        String lTitle=pCursor.getString(titleIndex);
        String lComment=pCursor.getString(commentIndex);
        String lStatus=pCursor.getString(statusdescIndex);
        TextView lTitleWidget= pView.findViewById(R.id.title);
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
        TextView lStatusWidget= pView.findViewById(R.id.status);
        lStatusWidget.setText(lStatus);
        setDateElement(pCursor,pView,R.id.startDate,lStartDate);
        setDateElement(pCursor,pView,R.id.endDate,lEndDate);
        pView.setTag(new TodoItem(lId,lIdProject,lIdStatus,lTitle,lComment,lStartDate,lEndDate));

    }
}
