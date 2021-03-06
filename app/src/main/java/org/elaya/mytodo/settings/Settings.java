package org.elaya.mytodo.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Class for reading and writing persistent app settings
 */

public class Settings {

    private static final String KEY_ID_DEFAULT_STATUS="id_default_status";
    private static SharedPreferences settingsFile;
    @Nullable
    private static String dateFormat=null;
    private static Long idDefaultStatus=null;


    public static void make(@NonNull Context pContext)
    {
        settingsFile = pContext.getSharedPreferences("main", Context.MODE_PRIVATE);
        if(settingsFile.contains(KEY_ID_DEFAULT_STATUS)) {
            idDefaultStatus = settingsFile.getLong(KEY_ID_DEFAULT_STATUS,-1);
        }
    }


    private Settings()
    {

    }


    @NonNull
    public static String getDateFormatType()
    {
        return settingsFile.getString("dateFormat","dmy");
    }

    @NonNull
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

    @NonNull
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

    @Nullable
    public static Long getIdDefaultStatus()
    {
        return idDefaultStatus;
    }

    public static void setIdDefaultStatus(long pIdDefaultStatus)
    {
        idDefaultStatus=pIdDefaultStatus;
        SharedPreferences.Editor lEditor=settingsFile.edit();
        lEditor.putLong(KEY_ID_DEFAULT_STATUS,pIdDefaultStatus);
        lEditor.apply();

    }

}
