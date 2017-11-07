package org.elaya.mytodo.tools;


import android.support.annotation.NonNull;

import org.elaya.mytodo.settings.Settings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Utility class for handling dates.
 */

public class DateHandler {
    /**
     * All methods are static so hide constructor.
     */
    private DateHandler()
    {

    }

    public static DateTime getDateFromText(@NonNull String pTextDate)
    {
        if(!pTextDate.isEmpty()){
                return DateTime.parse(pTextDate, DateTimeFormat.forPattern(Settings.getDateFormat()));
        }
        return null;
    }

    public static DateTime getDateFromLong(long pLongDate)
    {
        return new DateTime(pLongDate);
    }

    public static String getDateTextFromLong(long pLongDate)
    {
        DateTimeFormatter lFormatter= DateTimeFormat.forPattern(Settings.getDateFormat());
        return lFormatter.print(pLongDate);
    }

    public static String getTextFromDate(@NonNull DateTime pDate)
    {
        return pDate.toString(DateTimeFormat.forPattern(Settings.getDateFormat()));
    }


}
