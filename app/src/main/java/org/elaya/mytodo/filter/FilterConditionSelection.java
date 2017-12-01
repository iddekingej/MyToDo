package org.elaya.mytodo.filter;

import android.support.annotation.NonNull;

/**
 * Created by jeroen on 11/26/17.
 */

public class FilterConditionSelection implements FilterSelection {
    private final String description;
    private final String condition;

    public FilterConditionSelection(String pDescription, @NonNull String pCondition)
    {
        description=pDescription;
        condition=pCondition;
    }

    @Override
    @NonNull
    public String getCondition() {
        return condition;
    }

    public String toString()
    {
        return description;
    }
}
