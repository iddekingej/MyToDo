package org.elaya.mytodo;

import android.content.Context;
import android.database.Cursor;
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

    public TodoListAdapter(Context pContext,Cursor pCursor, View pParent){
        super(pContext,pCursor,0);
        idIndex=pCursor.getColumnIndex("_id");
        idProjectIndex=pCursor.getColumnIndex("id_project");
        idStatusIndex=pCursor.getColumnIndex("id_status");
        titleIndex=pCursor.getColumnIndex("title");
        commentIndex=pCursor.getColumnIndex("comment");
    }
    @Override
    public View newView(Context pContext, Cursor pCursor, ViewGroup pViewGroup) {
        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(R.layout.todo_item,pViewGroup,false);
    }

    @Override
    public void bindView(View pView, Context pContext, Cursor pCursor) {
        long lId=pCursor.getLong(idIndex);
        long lIdProject=pCursor.getLong(idProjectIndex);
        long lIdStatus=pCursor.getLong(idStatusIndex);
        String lTitle=pCursor.getString(titleIndex);
        String lComment=pCursor.getString(commentIndex);
        TextView lTitleWidget=(TextView)pView.findViewById(R.id.title);
        lTitleWidget.setText(lTitle);
        pView.setTag(new TodoItem(lId,lIdProject,lIdStatus,lTitle,lComment));

    }
}
