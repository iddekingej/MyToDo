package org.elaya.mytodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity {
    private static final int ACT_NEW_TODO=100;
    private static final int ACT_SHOW_TODO=101;
    public static  final int RES_DELETE_TODO=RESULT_FIRST_USER+1000;
    private long id;
    private DataSource ds;
    private TodoListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        TextView projectName=(TextView)findViewById(R.id.projectName);
        ListView todoList=(ListView)findViewById(R.id.todoList);

        ds=DataSource.getSource();

        Intent lIntent=getIntent();
        id=lIntent.getLongExtra("_id",0);
        ProjectItem projectItem=ds.getProjectById(id);
        projectName.setText(projectItem.getProjectName());
        adapter=new TodoListAdapter(this,ds.getTodoCursor(id));
        todoList.setAdapter(adapter);
        todoList.setOnItemClickListener(new ListView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> pParent, @NonNull View pView, int pPosition, long pId){
                openShowToDo(pView);
            }
        });
        todoList.setEmptyView(findViewById(R.id.noItemsMessage));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.back:
                finish();
                break;

            case R.id.add_todo:
                newToDo();
                break;

            default:
                return super.onOptionsItemSelected(pItem);
        }

        return true;
    }
    private void warning(String pWarning) {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(this);
        lBuilder.setTitle(R.string.failure_title);
        lBuilder.setMessage(pWarning);
        lBuilder.setPositiveButton("Ok", null);
        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

    private void newToDo()
    {
        Intent lIntent= new Intent(this,EditToDoActivity.class);
        lIntent.putExtra("id_project",id);
        startActivityForResult(lIntent,ACT_NEW_TODO);
    }

    private void openShowToDo(@NonNull View pItem){
        TodoItem lItem=(TodoItem)pItem.getTag();
        Intent lIntent= new Intent(this,ShowTodo.class);
        lIntent.putExtra("_id",lItem.getId());
        lIntent.putExtra("id_project",id);
        lIntent.putExtra("id_status",lItem.getIdStatus());
        lIntent.putExtra("title",lItem.getTitle());
        lIntent.putExtra("comment",lItem.getComment());
        if(lItem.startDate() != null){
            lIntent.putExtra("startDate",lItem.startDate());
        }
        if(lItem.endDate() != null){
            lIntent.putExtra("endDate",lItem.endDate());
        }
        startActivityForResult(lIntent,ACT_SHOW_TODO);
    }

    private void addTodo(@NonNull  Intent pData)
    {
        long lIdProject=pData.getLongExtra("id_project",-1);
        long lIdStatus=pData.getLongExtra("id_status",-1);
        String lTitle=pData.getStringExtra("title");
        String lComment=pData.getStringExtra("comment");
        Long lStartDate=null;
        if(pData.hasExtra("startDate")){
            lStartDate=pData.getLongExtra("startDate",-1);
        }
        Long lEndDate=null;
        if(pData.hasExtra("endDate")){
            lEndDate=pData.getLongExtra("endDate",-1);
        }
        try {
            ds.addTodo(lIdProject,lIdStatus, lTitle, lComment,lStartDate,lEndDate);
        }catch(Exception e){
            warning(e.getMessage());
        }
        refreshList();
    }


    private void deleteTodo(@NonNull Intent pData)
    {
        long lId=pData.getLongExtra("_id",-1);
        ds.deleteToDo(lId);
        refreshList();
    }
    protected void onActivityResult(int pRequestCode,int pResultCode,@NonNull Intent pData) {
        try {
            switch (pResultCode) {

                case RES_DELETE_TODO:
                    deleteTodo(pData);
                    break;

                case RESULT_OK:
                    switch (pRequestCode) {
                        case ACT_NEW_TODO:
                            addTodo(pData);
                            break;

                        case ACT_SHOW_TODO:
                            refreshList();
                            break;
                    }

                    break;
            }
        }catch(Exception e){
            warning(e.getMessage());
        }
    }

    private void refreshList()
    {
        adapter.getCursor().close();
        adapter.swapCursor(ds.getTodoCursor(id));
        adapter.notifyDataSetChanged();
    }
}
