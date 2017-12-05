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

    private final int statusDescIndex;
    private final int isFinishedIndex;


    public TodoListAdapter(Context pContext,@NonNull Cursor pCursor){
        super(pContext,pCursor,0);

        statusDescIndex =pCursor.getColumnIndex("statusdesc");
        isFinishedIndex=pCursor.getColumnIndex("isfinished");

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


        TodoItem lTodoItem=new TodoItem(pCursor);

        String lStatus=pCursor.getString(statusDescIndex);
        TextView lTitleWidget= pView.findViewById(R.id.title);
        lTitleWidget.setText(lTodoItem.getTitle());

        if(pCursor.getLong(isFinishedIndex)==1){
            lTitleWidget.setBackgroundResource(R.drawable.strike);
        } else {
            lTitleWidget.setBackgroundResource(0);
        }

        Long lStartDateEpoch=lTodoItem.getEndDate();
        DateTime lStartDate=null;
        if(lStartDateEpoch != null){
            lStartDate=DateHandler.getDateFromLong(lStartDateEpoch);
        }
        Long lEndDateEpoch=lTodoItem.getEndDate();
        DateTime lEndDate=null;
        if(lEndDateEpoch != null){
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
        pView.setTag(lTodoItem);

    }
}
