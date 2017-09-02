package org.elaya.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EditProjectActivity extends AppCompatActivity {

    private TextView projectName;
    private long     id;
    @Override

    /**
     * Create activity and setup form.
     *
     * Set the toolbar and fill the form elements
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        projectName=(TextView) findViewById(R.id.projectName);
        Intent lIntent=getIntent();
        id=lIntent.getLongExtra("_id",-1);
        projectName.setText(lIntent.getStringExtra("projectName"));
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
        getMenuInflater().inflate(R.menu.menu_project_edit, pMenu);
        return super.onCreateOptionsMenu(pMenu);
    }

    /**
     * When the back button in the toolbar is pressed.
     */

    public void onPressCancel()
    {
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * When the save button is pressed.
     */
    public void onPressSave()
    {
        Intent lIntent = new Intent();
        String lText=projectName.getText().toString();
        lIntent.putExtra("_id",id);
        lIntent.putExtra("projectName",lText);
        setResult(RESULT_OK,lIntent);
        finish();
    }

    /**
     * When one of the toolbar icons is pressed:
     * R.id.back - The back button on the toolbar
     * R.id.save - Save button
     * R.id.help - Help button
     *
     * @param pItem
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.back:
                onPressCancel();
                break;

            case R.id.save:
                onPressSave();
                break;

            case R.id.help:
                Helpers.openHelp(this, "edit_project");
                break;

            default:
                super.onOptionsItemSelected(pItem);

        }
        return true;
    }
}
