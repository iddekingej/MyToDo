package org.elaya.mytodo.filter;

import android.support.annotation.NonNull;

/**
 * Interface used for the filter selection list in the to do overview
 */

public interface FilterSelection {

    /**
     *
     * @return SQL condition for the filter
     */
    @NonNull String getCondition();
    boolean hasNot();
}
