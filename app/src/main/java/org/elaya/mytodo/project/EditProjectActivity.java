package org.elaya.mytodo.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.Helpers;

public class EditProjectActivity extends BaseActivity {

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

        projectName=(TextView) findViewById(R.id.projectName);
        Intent lIntent=getIntent();
        id=lIntent.getLongExtra("_id",-1);
        projectName.setText(lIntent.getStringExtra("projectName"));
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_edit_project;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_project_edit;
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