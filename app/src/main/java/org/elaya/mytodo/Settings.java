package org.elaya.mytodo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Class for reading and writing persistent app settings
 */

class Settings {


    private static SharedPreferences settingsFile;
    private static String dateFormat=null;

    static void make(@NonNull Context pContext)
    {
        settingsFile = pContext.getSharedPreferences("main", Context.MODE_PRIVATE);
    }


    private Settings()
    {

    }


    public static String getDateFormatType()
    {
        return settingsFile.getString("dateFormat","dmy");
    }

    public static String getDateFormat()
    {
        String lSep=getSeparator();
        String lFormat=getDateFormatType();
        if(null==dateFormat) {
            if ("MDY".equals(lFormat)) {
                dateFormat="MM" + lSep + "dd" + lSep + "yyyy";
            } else if ("YMD".equals(lFormat)) {
                dateFormat="yyy"+lSep+"MM"+lSep+"dd";
            } else {
                dateFormat="dd" + lSep + "MM" + lSep + "yyyy";
            }
        }
        return dateFormat;
    }

    public static void setDateFormatType(@NonNull String pFormat)
    {
        SharedPreferences.Editor lEditor=settingsFile.edit();
        dateFormat=null;
        lEditor.putString("dateFormat",pFormat);
        lEditor.apply();
    }

    public static String getSeparator()
    {
        return settingsFile.getString("separator","-");
    }

    public static void setSeparator(@NonNull String pSeparator)
    {
        dateFormat=null;
        SharedPreferences.Editor lEditor=settingsFile.edit();
        lEditor.putString("separator",pSeparator);
        lEditor.apply();
    }


}
