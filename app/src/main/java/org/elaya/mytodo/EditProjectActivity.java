package org.elaya.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EditProjectActivity extends AppCompatActivity {

    private TextView projectName;
    private long     id;

    /***
     * Set op de Activity:
     * -Setup toolbar
     * -Setup form elements
     * @param savedInstanceState Saved instance state
     */
    @Override
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
     * When the back button in the toolbar is pressed, the activity is finished
     *
     */

    private void onPressCancel()
    {
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * When the save button is pressed all form data is send back to the calling  Activity
     * In ProjectActivity the data is saved
     *
     * @param pAddTodo When true After save (in ProjectActivity) the To Do activity is started
     */
    private void onPressSave(boolean pAddTodo)
    {
        Intent lIntent = new Intent();
        String lText=projectName.getText().toString();
        lIntent.putExtra("_id",id);
        lIntent.putExtra("projectName",lText);
        lIntent.putExtra("addTodo",pAddTodo);
        setResult(RESULT_OK,lIntent);
        finish();
    }

    public void saveGotoTodo(View pView)
    {
        onPressSave(true);
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
            case R.id.back:
                onPressCancel();
                break;

            case R.id.save:
                onPressSave(false);
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
