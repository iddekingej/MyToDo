package org.elaya.mytodo.settings;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.RadioGroup;
import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.BaseEditActivity;


public class SettingsEditor extends BaseEditActivity {

    private RadioGroup dateFormatElement;
    private RadioGroup separatorElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateFormatElement = findViewById(R.id.dateFormat);
        separatorElement  = findViewById(R.id.dateSep);
        int lFormat=R.id.date_dmy;
        String lFormatSettings= Settings.getDateFormatType();
        if("MDY".equals(lFormatSettings)){
            lFormat=R.id.date_mdy;
        } else if("YMD".equals(lFormatSettings)){
            lFormat=R.id.date_ymd;
        }
        dateFormatElement.check(lFormat);

        String lSeparatorSettings= Settings.getSeparator();

        int lSeparator=R.id.sep_minus;
        if("/".equals(lSeparatorSettings)){
            lSeparator = R.id.sep_slash;
        } else if(".".equals(lSeparatorSettings)){
            lSeparator = R.id.sep_dot;
        }

        separatorElement.check(lSeparator);
    }
    @Override
    @NonNull
    protected String getHelpName(){
        return "settings";
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_settings;
    }

    @Override
    protected void onDeleteClicked() {
        //onSettings doesn't require a delete
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_settings;
    }

    protected void onSaveClicked()
    {
        int lId=dateFormatElement.getCheckedRadioButtonId();
        String lFormatType="DMY";
        if(lId==R.id.date_mdy){
            lFormatType="MDY";
        } else if(lId==R.id.date_ymd){
            lFormatType="YMD";
        }
        Settings.setDateFormatType(lFormatType);
        lId=separatorElement.getCheckedRadioButtonId();
        String lSep="-";
        if(lId==R.id.sep_slash){
            lSep="/";
        } else if(lId==R.id.sep_dot){
            lSep=".";
        }
        Settings.setSeparator(lSep);
        finish();
    }

}
