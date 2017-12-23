package org.elaya.mytodo.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.BaseEditActivity;
import org.elaya.mytodo.tools.Helpers;

public class EditProjectActivity extends BaseEditActivity {

    public static String P_ID="id";
    public static String P_PROJECT_NAME="project_name";
    public static String P_ADD_TODO="add_todo";

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

        projectName= findViewById(R.id.projectName);
        Intent lIntent=getIntent();
        id=lIntent.getLongExtra(P_ID,-1);
        projectName.setText(lIntent.getStringExtra(P_PROJECT_NAME));
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_edit_project;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_edit_project;
    }

    /**
     * When the save button is pressed all form data is send back to the calling  Activity
     * In ProjectActivity the data is saved
     *
     * @param pAddTodo When true After save (in ProjectActivity) the To Do activity is started
     */
    private void saveProject(boolean pAddTodo)
    {

        Intent lIntent = new Intent();
        String lText=projectName.getText().toString();
        if(lText.isEmpty()){
            Helpers.warning(this,R.string.ERR_PROJECT_NAME_EMPTY);
            return;
        }
        lIntent.putExtra(P_ID,id);
        lIntent.putExtra(P_PROJECT_NAME,lText);
        lIntent.putExtra(P_ADD_TODO,pAddTodo);
        setResult(RESULT_OK,lIntent);
        finish();
    }

    protected void onSaveClicked()
    {
        saveProject(false);
    }

    @Override
    protected void onDeleteClicked() {
        //Todo: Implement onDeleteClicked
    }

    public void saveGotoTodo(@SuppressWarnings("UnusedParameters") View pView)
    {
        saveProject(true);
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
        if(pItem.getItemId()==R.id.help) {
            Helpers.openHelp(this, "edit_project");
        }else {
            return super.onOptionsItemSelected(pItem);

        }
        return true;
    }
}
