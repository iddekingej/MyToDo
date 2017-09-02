package org.elaya.mytodo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

public class EditToDoActivity extends AppCompatActivity {

    boolean isNew=false;
    long id;
    long idProject;
    long idStatus;
    EditText title;
    EditText comment;
    Spinner  statusElement;
    DataSource ds;
    StatusSpinnerAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent lIntent=getIntent();
        ds= DataSource.getSource();

        isNew = !lIntent.hasExtra("_id");
        id=lIntent.getLongExtra("_id",-1);
        idProject=lIntent.getLongExtra("id_project",-1);
        idStatus=lIntent.getLongExtra("id_status",-1);
        title=(EditText)findViewById(R.id.title);
        title.setText(lIntent.getStringExtra("title"));
        comment=(EditText)findViewById(R.id.comment);
        comment.setText(lIntent.getStringExtra("comment"));


        statusElement=(Spinner)findViewById(R.id.status);
        spinnerAdapter=new StatusSpinnerAdapter(this,ds.getStatusCursor());
        statusElement.setAdapter(spinnerAdapter);
        int lNum=spinnerAdapter.getCount();
        for(int lCnt=0;lCnt<lNum;lCnt++) {
            long lId = spinnerAdapter.getItemId(lCnt);
            if(lId==idStatus){
                statusElement.setSelection(lCnt);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_todo, menu);
        if(isNew) {
            MenuItem lDeleteMenu = menu.findItem(R.id.delete);
            invalidateOptionsMenu();
            lDeleteMenu.setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.back:
                onCancelClicked();
                break;
            case R.id.delete:
                onDeleteTodoClicked();
                break;
            case R.id.save:
                onSaveClicked();
                break;
            default:
                return onOptionsItemSelected(pItem);
        }
        return true;
    }

    public void onDeleteTodoClicked()
    {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(this);
        lBuilder.setTitle(R.string.failure_title);
        lBuilder.setMessage(R.string.delete_todo_question);
        lBuilder.setPositiveButton(R.string.delete_todo,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface pDialog,int pId){
                        doDelete();
                    }
                }
        );
        lBuilder.setNegativeButton(R.string.keep_todo,null);
        AlertDialog lDialog = lBuilder.create();
        lDialog.show();
    }

    public void doDelete()
    {
        Intent lIntent = new Intent();
        lIntent.putExtra("_id",id);
        setResult(TodoActivity.RES_DELETE_TODO,lIntent);
        finish();
    }
    public void onCancelClicked()
    {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onSaveClicked()
    {
        Intent lIntent = new Intent();
        lIntent.putExtra("_id",id);
        lIntent.putExtra("id_project",idProject);
        StatusItem lItem=(StatusItem)statusElement.getSelectedView().getTag();
        lIntent.putExtra("id_status",lItem.getId());
        lIntent.putExtra("title",title.getText().toString());
        lIntent.putExtra("comment",comment.getText().toString());
        setResult(RESULT_OK,lIntent);
        finish();
    }
}
