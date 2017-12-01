package org.elaya.mytodo.filter;

import android.support.annotation.NonNull;

/**
 * Created by jeroen on 11/26/17.
 */

public interface FilterSelection {
    @NonNull String getCondition(String[] pData);
}
