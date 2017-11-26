package org.elaya.mytodo.todo;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.DateHandler;
import org.joda.time.DateTime;

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

    private TextView setDateElement(@NonNull View pView, int pElement, @Nullable Long pValue)
    {
        TextView lElement=pView.findViewById(pElement);
        if(pValue==null){
            lElement.setText("");
        } else {
            lElement.setText(DateHandler.getDateTextFromLong(pValue));
        }
        return lElement;
    }

    @Override
    public void bindView(@NonNull  View pView, @NonNull Context pContext, @NonNull  Cursor pCursor) {
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
        Long lStartDateEpoch=null;
        DateTime lStartDate=null;
        if(!pCursor.isNull(startDateIndex)){
            lStartDateEpoch=pCursor.getLong(startDateIndex);
            lStartDate=DateHandler.getDateFromLong(lStartDateEpoch);
        }
        Long lEndDateEpoch=null;
        DateTime lEndDate=null;
        if(!pCursor.isNull(endDateIndex)){
            lEndDateEpoch=pCursor.getLong(endDateIndex);
            lEndDate=DateHandler.getDateFromLong(lEndDateEpoch);
        }
        TextView lStatusWidget= pView.findViewById(R.id.status);
        lStatusWidget.setText(lStatus);


        TextView lStartView=setDateElement(pView,R.id.startDate,lStartDateEpoch);
        TextView lEndView=setDateElement(pView,R.id.endDate,lEndDateEpoch);

        lStartView.setTextColor(lTitleWidget.getTextColors());
        lEndView.setTextColor(lTitleWidget.getTextColors());
        if(lEndDate != null && !lEndDate.isAfterNow()){
            lEndView.setTextColor(pContext.getResources().getColor(R.color.after_end));

        } else if(lStartDate != null && !lStartDate.isAfterNow()){
            lStartView.setTextColor(pContext.getResources().getColor(R.color.after_start));
        }
        pView.setTag(new TodoItem(lId,lIdProject,lIdStatus,lTitle,lComment,lStartDateEpoch,lEndDateEpoch));

    }
}
