package org.elaya.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private static final int ACT_NEW_PROJECT=100;
    private static final int ACT_EDIT_PROJECT=101;
    private static final int ACT_EDIT_TODO=102;
    private static final int ACT_EDIT_STATUS=103;
    private DataSource ds;
    private ProjectListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ds=DataSource.makeSource(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lProjectList=(ListView)findViewById(R.id.projectList);
        lProjectList.setOnItemClickListener(new ListView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> pParent,View pView,int pPosition,long pId){
                openProject(pView);
            }
        });
        lProjectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> pParent,final View pView, int pPosition, long pId)
            {
                    return longClickProject(pView);
            }
        });
        adapter=new ProjectListAdapter(this,ds.getProjectCursor(),lProjectList);
        lProjectList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {

            case R.id.action_status:
                showEditStatus();
                break;

            case R.id.add_project:
                openNewProject();
                break;

            case R.id.help:
                Helpers.openHelp(this,"projects");
                break;

            default:
                return super.onOptionsItemSelected(pItem);
        }

        return true;
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
    private boolean longClickProject(View pView)
    {
        final ProjectItem lProject=(ProjectItem)pView.getTag();
        if(lProject != null) {
            PopupMenu lPopup=new PopupMenu(this,pView);
            lPopup.getMenuInflater().inflate(R.menu.edit_project_menu,lPopup.getMenu());
            lPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                public boolean onMenuItemClick(MenuItem pItem){

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
    private void deleteProject(ProjectItem pProject)
    {
        ds.deleteProject(pProject.getId());
        refreshList();
    }

    /**
     * Displays the edit project form
     * @param pProject Project data that's going o be edited.
     */
    private void editProject(ProjectItem pProject)
    {
        Intent lIntent= new Intent(this,EditProjectActivity.class);
        lIntent.putExtra("_id",pProject.getId());
        lIntent.putExtra("projectName",pProject.getProjectName());
        startActivityForResult(lIntent,ACT_EDIT_PROJECT);
    }

    /**
     * Refrehses the project list.
     */
    private void refreshList()
    {
        adapter.getCursor().close();
        adapter.swapCursor(ds.getProjectCursor());
        adapter.notifyDataSetChanged();
    }

    /**
     * Saves data in database after user entered net project.
     * @param pData Data returned from the project form
     */
    private void newProject(Bundle pData)
    {
        ds.addProject(pData.getString("projectName"));
    }

    /**
     * Saves data in daabases after user edited the project
     * @param pData Data returned from the project form
     */
    private void editProject(Bundle pData){
        ds.editProject(pData.getLong("_id",-1),pData.getString("projectName"));
    }

    /**
     * Called when some activities returns
     *
     * @param pRequestCode Type of request
     * @param pResultCode  RESULT_OK if the user didn't press cancel
     * @param pData        Data returned from other activity
     */
    protected void onActivityResult(int pRequestCode,int pResultCode,Intent pData)
    {
        if(pResultCode == RESULT_OK){
            switch(pRequestCode)
            {
                case ACT_NEW_PROJECT:
                    newProject(pData.getExtras());
                    refreshList();
                    break;

                case ACT_EDIT_PROJECT:
                    editProject(pData.getExtras());
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




    private void openNewProject()
    {
        Intent lIntent= new Intent(this,EditProjectActivity.class);
        startActivityForResult(lIntent,ACT_NEW_PROJECT);
    }

    private void openProject(View pView)
    {
        ProjectItem lProject=(ProjectItem)pView.getTag();
        Intent lIntent = new Intent(this,TodoActivity.class);
        lIntent.putExtra("_id",lProject.getId());
        startActivityForResult(lIntent,ACT_EDIT_TODO);
    }
}
