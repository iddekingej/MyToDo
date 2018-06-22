package org.elaya.mytodo.todo;

import android.database.Cursor;
import android.support.annotation.NonNull;

import org.elaya.mytodo.tools.ModelItem;

/**
 * Class representing a to do item belonging to a project
 */

public class TodoItem extends ModelItem {

    public static final String TABLE_NAME="todoitems";
    public static final String F_ID_STATUS="id_status";
    public static final String F_ID_PROJECT="id_project";
    public static final String F_TITLE="title";
    public static final String F_COMMENT="comment";
    public static final String F_START_DATE="start_date";
    public static final String F_END_DATE="end_date";

    private final long idStatus;
    private final String title;
    private final String comment;
    private final Long startDate;
    private final Long endDate;

    public TodoItem(@NonNull Cursor pCursor)
    {
        super(pCursor);

        idStatus=getCursorLong(pCursor,F_ID_STATUS);
        title=getCursorString(pCursor,F_TITLE);
        comment=getCursorString(pCursor,F_COMMENT);
        startDate=getCursorLongObject(pCursor,F_START_DATE);
        endDate=getCursorLongObject(pCursor,F_END_DATE);
    }


    public long getIdStatus() { return idStatus; }

    public String getTitle()
    {
        return title;
    }

    public String getComment()
    {
        return comment;
    }
    public Long getEndDate(){ return endDate;}
    public Long getStartDate(){return startDate;}
}
