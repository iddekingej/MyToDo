package org.elaya.mytodo.filter;

import android.support.annotation.NonNull;

/**
 * Filter with a fixed condition
 */

public class FilterConditionSelection implements FilterSelection {
    private final String description;
    private final String condition;
    private final boolean hasNot;

    public FilterConditionSelection(String pDescription, @NonNull String pCondition,boolean pHasNot)
    {
        description=pDescription;
        condition=pCondition;
        hasNot=pHasNot;
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

    public boolean hasNot()
    {
        return hasNot;
    }
}
