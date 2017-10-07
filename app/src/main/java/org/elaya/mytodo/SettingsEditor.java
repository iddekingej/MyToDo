package org.elaya.mytodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

public class SettingsEditor extends AppCompatActivity {

    private RadioGroup dateFormatElement;
    private RadioGroup separatorElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dateFormatElement = (RadioGroup)findViewById(R.id.dateFormat);
        separatorElement  = (RadioGroup)findViewById(R.id.dateSep);
        int lFormat=R.id.date_dmy;
        String lFormatSettings=Settings.getDateFormatType();
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

    /**
     * Set the toolbar option menu
     *
     * @param pMenu Toolbar menu
     * @return If handled
     */
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, pMenu);
        return super.onCreateOptionsMenu(pMenu);
    }

    private void saveSettings()
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
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.back:
                finish();
                break;

            case R.id.save:
                saveSettings();
                break;

            case R.id.help:
                Helpers.openHelp(this, "settings");
                break;

            default:
                super.onOptionsItemSelected(pItem);

        }
        return true;
    }
}
