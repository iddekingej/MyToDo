package org.elaya.mytodo.tools;


import android.support.annotation.NonNull;

import org.elaya.mytodo.settings.Settings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by jeroen on 10/15/17.
 */

public class DateHandler {

    public static DateTime getDateFromText(@NonNull String pTextDate)
    {
        if(!pTextDate.isEmpty()){
            return DateTime.parse(pTextDate,DateTimeFormat.forPattern(Settings.getDateFormat()));

        }
        return null;
    }

    public static String getDateFromLong(long pLongDate)
    {
        DateTimeFormatter lFormatter= DateTimeFormat.forPattern(Settings.getDateFormat());
        return lFormatter.print(pLongDate);
    }

    public static String getTextFromDate(@NonNull DateTime pDate)
    {
        return pDate.toString(DateTimeFormat.forPattern(Settings.getDateFormat()));
    }


}
