package org.elaya.mytodo.tools;

import android.database.Cursor;

/**
 * Base class for DB objects
 */

public class ModelItem {

    private long id;

    protected ModelItem(Cursor pCursor)
    {
        id=getCursorLong(pCursor,"_id");
    }

    public long getId()
    {
        return id;
    }

    protected long getCursorLong(Cursor pCursor,String pName)
    {
        int lIndex=pCursor.getColumnIndex(pName);
        return pCursor.getLong(lIndex);
    }

    protected Long getCursorLongObject(Cursor pCursor,String pName)
    {
        int lIndex=pCursor.getColumnIndex(pName);
        if(pCursor.isNull(lIndex)){
            return null;
        }
        return pCursor.getLong(lIndex);

    }

    protected String getCursorString(Cursor pCursor,String pName)
    {
        int lIndex=pCursor.getColumnIndex(pName);
        return pCursor.getString(lIndex);
    }
}
