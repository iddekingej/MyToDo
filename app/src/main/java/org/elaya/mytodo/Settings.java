package org.elaya.mytodo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class for reading and writing persistent app settings
 */

class Settings {


    private static SharedPreferences settingsFile;

    static void make(Context pContext)
    {
        settingsFile = pContext.getSharedPreferences("main", Context.MODE_PRIVATE);
    }


    private Settings(Context pContext)
    {

    }

    public static String getDateFormatType()
    {
        return settingsFile.getString("dateFormat","dmy");
    }

    public static String getDateFormat()
    {
        String lFormat=getDateFormatType();
        if("MDY".equals(lFormat)){
            return "MM-dd-yyyy";
        }
        if("YMD".equals(lFormat)){
            return "yyy-MM-dd";
        }
        return "dd-MM-yyyy";
    }

    public static void setDateFormatType(String pFormat)
    {
        SharedPreferences.Editor lEditor=settingsFile.edit();

        lEditor.putString("dateFormat",pFormat);
        lEditor.apply();
    }


}
