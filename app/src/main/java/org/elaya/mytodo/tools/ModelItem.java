package org.elaya.mytodo.tools;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Base class for DB objects
 */

public class ModelItem {

    private final long id;

    protected ModelItem(@NonNull Cursor pCursor)
    {
        id=getCursorLong(pCursor,"_id");
    }

    public long getId()
    {
        return id;
    }

    protected long getCursorLong(@NonNull Cursor pCursor, @NonNull String pName)
    {
        int lIndex=pCursor.getColumnIndex(pName);
        return pCursor.getLong(lIndex);
    }

    @Nullable
    protected Long getCursorLongObject(@NonNull Cursor pCursor,@NonNull String pName)
    {
        int lIndex=pCursor.getColumnIndex(pName);
        if(pCursor.isNull(lIndex)){
            return null;
        }
        return pCursor.getLong(lIndex);

    }

    protected String getCursorString(@NonNull Cursor pCursor,@NonNull String pName)
    {
        int lIndex=pCursor.getColumnIndex(pName);
        return pCursor.getString(lIndex);
    }
}
