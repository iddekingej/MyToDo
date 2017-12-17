package org.elaya.mytodo.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.elaya.mytodo.filter.FilterActivity;
import org.elaya.mytodo.filter.FilterManager;
import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.R;
import org.elaya.mytodo.settings.DateSettingsEditor;
import org.elaya.mytodo.status.StatusActivity;
import org.elaya.mytodo.todo.TodoActivity;
import org.elaya.mytodo.tools.Helpers;
import org.elaya.mytodo.settings.Settings;

public class ProjectActivity extends BaseActivity {

    private static final int ACT_NEW_PROJECT=100;
    private static final int ACT_EDIT_PROJECT=101;
    private static final int ACT_EDIT_TODO=102;
    private static final int ACT_EDIT_STATUS=103;

    private ProjectListAdapter adapter;
    private LinearLayout projectHeaderElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle(R.string.title_main);
        super.onCreate(savedInstanceState);
        FilterManager.setCurrentFilter(FilterManager.makeAllSelection(this));
        projectHeaderElement=findViewById(R.id.projectHeader);

        ListView lProjectList= findViewById(R.id.projectList);
        lProjectList.setOnItemClickListener(new ListView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> pParent,@NonNull View pView,int pPosition,long pId){
                openProject(pView);
            }
        });
        lProjectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> pParent,@NonNull final View pView, int pPosition, long pId)
            {
                    return longClickProject(pView);
            }
        });
        adapter=new ProjectListAdapter(this,ds.getProjectCursor());
        lProjectList.setAdapter(adapter);
        lProjectList.setEmptyView(findViewById(R.id.noProject));
        Settings.make(this);
        showHeader();
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_project;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_main;
    }

    @NonNull
    @Override
    protected String getHelpName(){ return "project";}

    private void showHeader()
    {
        projectHeaderElement.setVisibility((adapter.getCount()>0)?View.VISIBLE:View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        switch (pItem.getItemId()) {

            case R.id.action_status:
                showEditStatus();
                break;

            case R.id.add_project:
                openNewProject();
                break;

            case R.id.action_settings:
                openSettings();
                break;

            case R.id.todo_filters:
                showFilters();
                break;

            default:
                return super.onOptionsItemSelected(pItem);
        }

        return true;
    }


    private void showFilters()
    {
        Intent lIntent=new Intent(this, FilterActivity.class);
        startActivity(lIntent);
    }

    /**
     * Display Activity for editing status list.
     */
    private void showEditStatus()
    {
        Intent lIntent= new Intent(this,StatusActivity.class);
        startActivityForResult(lIntent,ACT_EDIT_STATUS);
    }

    /**
     * When the user long clicks a project, a popup menu appears
     * The user can select edit of delete project
     *
     * @param pView   ListView item that is selected
     * @return        true->event is handled
     */
    private boolean longClickProject(@NonNull View pView)
    {
        final ProjectItem lProject=(ProjectItem)pView.getTag();
        if(lProject != null) {
            PopupMenu lPopup=new PopupMenu(this,pView);
            lPopup.getMenuInflater().inflate(R.menu.menu_project,lPopup.getMenu());
            lPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                public boolean onMenuItemClick(@NonNull MenuItem pItem){

                    switch(pItem.getItemId()) {
                        case R.id.editProject:
                            editProject(lProject);
                            break;
                        case R.id.deleteProject:
                            deleteProject(lProject);
                    }
                    return true;
                }

            });
            lPopup.show();
            return true;
        }
        return false;
    }

    /**
     * The user has clicked 'delete project' icon. The project is going to be deleted.
     *
     * @param pProject Project to be deleted
     */
    private void deleteProject(@NonNull final ProjectItem pProject)
    {
        if(ds.projectHasTodo(pProject)){
            Helpers.warning(this,R.string.cant_delete_has_todo);
        } else {
            Helpers.confirmDelete(this, R.string.ask_delete_status,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface pDialog, int pId) {
                            ds.deleteProject(pProject.getId());
                            refreshList();
                        }
                    }
            );
        }
    }

    /**
     * Displays the edit project form
     * @param pProject Project data that's going o be edited.
     */
    private void editProject(@NonNull ProjectItem pProject)
    {
        Intent lIntent= new Intent(this,EditProjectActivity.class);
        lIntent.putExtra("_id",pProject.getId());
        lIntent.putExtra("projectName",pProject.getProjectName());
        startActivityForResult(lIntent,ACT_EDIT_PROJECT);
    }

    /**
     * Refreshes the project list.
     */
    private void refreshList()
    {
        adapter.getCursor().close();
        adapter.swapCursor(ds.getProjectCursor());
        adapter.notifyDataSetChanged();
        showHeader();
    }

    /**
     * Saves data in database after user entered net project.
     * @param pData Data returned from the project form
     */
    private void newProject(@NonNull Intent pData)
    {
        long lIdProject=ds.addProject(pData.getStringExtra("projectName"));
        refreshList();
        if(pData.getBooleanExtra("addTodo",false)){
            openProjectById(lIdProject);
        }
    }

    /**
     * Saves data in databases after user edited the project
     * @param pData Data returned from the project form
     */
    private void editProject(@NonNull Intent pData){
        ds.editProject(pData.getLongExtra("_id",-1),pData.getStringExtra("projectName"));
    }

    /**
     * Called when some activities returns
     *
     * @param pRequestCode Type of request
     * @param pResultCode  RESULT_OK if the user didn't press cancel
     * @param pData        Data returned from other activity
     */
    protected void onActivityResult(int pRequestCode,int pResultCode,@NonNull Intent pData)
    {
        if(pResultCode == RESULT_OK){
            switch(pRequestCode)
            {
                case ACT_NEW_PROJECT:
                    if(pData.getExtras() != null) {
                        newProject(pData);
                    }

                    break;

                case ACT_EDIT_PROJECT:
                    editProject(pData);
                    refreshList();
                    break;

            }
        } else {
            // After Status or TO DO page the process list must be refreshed because
            // the number of to do's for each status can be changed

            if(pRequestCode==ACT_EDIT_STATUS || pRequestCode==ACT_EDIT_TODO){
                refreshList();
            }
        }


    }


//----( Open other activities )-------------------------------------------

    /**
     * Open dialog for entering a new project
     */
    private void openNewProject()
    {
        Intent lIntent= new Intent(this,EditProjectActivity.class);
        startActivityForResult(lIntent,ACT_NEW_PROJECT);
    }

    /**
     * When selecting a project in the ListView the To Do activity is started.
     * This activity displays all to do's belonging to a project.
     *
     * @param pView  View from ListView that was selected
     */
    private void openProject(@NonNull View pView)
    {
        ProjectItem lProject=(ProjectItem)pView.getTag();
        openProjectById(lProject.getId());
    }

    /**
    * When selecting a project in the ListView the To Do activity is started.
     * This activity displays all to do's belonging to a project.
    *
    * @param pId Id of project to open
    */

    private void openProjectById(long pId)
    {
        Intent lIntent = new Intent(this,TodoActivity.class);
        lIntent.putExtra("_id",pId);
        startActivityForResult(lIntent,ACT_EDIT_TODO);

    }

    private void openSettings()
    {
        Intent lIntent= new Intent(this,DateSettingsEditor.class);
        startActivity(lIntent);
    }
}
