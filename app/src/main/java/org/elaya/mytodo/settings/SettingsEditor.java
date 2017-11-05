package org.elaya.mytodo.settings;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;

import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.BaseEditActivity;
import org.elaya.mytodo.tools.Helpers;

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
    protected int getContentResource() {
        return R.layout.activity_settings;
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

    /**
     * When one of the toolbar icons is pressed:
     * R.id.back - The back button on the toolbar
     * R.id.save - Save button
     * R.id.help - Help button
     *
     * @param pItem Menu item clicked
     * @return      True - event is handled
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.help:
                Helpers.openHelp(this, "settings");
                break;

            default:
                super.onOptionsItemSelected(pItem);

        }
        return true;
    }
}
