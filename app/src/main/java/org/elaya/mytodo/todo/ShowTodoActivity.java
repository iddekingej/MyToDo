package org.elaya.mytodo.todo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.DateHandler;

public class ShowTodoActivity extends BaseActivity {

    private  static final int REQ_EDIT =100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView lTitleElement= findViewById(R.id.title);
        TextView lStatusElement= findViewById(R.id.status);
        TextView lStartDateElement= findViewById(R.id.startDate);
        TextView lEndDateElement= findViewById(R.id.endDate);
        TextView lCommentElement= findViewById(R.id.comment);

        Intent lIntent=getIntent();
        lTitleElement.setText(lIntent.getStringExtra("title"));
        lStatusElement.setText(ds.getStatusTextById(lIntent.getLongExtra("id_status",-1)));
        if(lIntent.hasExtra("startDate")) {
            lStartDateElement.setText(DateHandler.getDateTextFromLong(lIntent.getLongExtra("startDate",-1)));
        }
        if(lIntent.hasExtra("endDate")){
            lEndDateElement.setText(DateHandler.getDateTextFromLong(lIntent.getLongExtra("endDate",-1)));
        }
        lCommentElement.setText(lIntent.getStringExtra("comment"));
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_show_todo;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_show_todo;
    }

    private void editTodo()
    {
        Intent lIntent = new Intent(this,EditToDoActivity.class);
        if(null != getIntent().getExtras()) {
            lIntent.putExtras(getIntent().getExtras());
        }
        startActivityForResult(lIntent, REQ_EDIT);
    }


    /**
     * When an existing to do is edited this method saves the
     * the data in the database
     *
     * @param pData Data returned from the To Do edit form
     */

    private void updateTodo(@NonNull Intent pData)
    {
        long lId=pData.getLongExtra(EditToDoActivity.P_ID,-1);
        long lIdProject=pData.getLongExtra(EditToDoActivity.P_ID_PROJECT,-1);
        long lIdStatus=pData.getLongExtra(EditToDoActivity.P_ID_STATUS,-1);
        String lTitle=pData.getStringExtra(EditToDoActivity.P_TITLE);
        String lComment=pData.getStringExtra(EditToDoActivity.P_COMMENT);
        Long lStartDate=null;
        if(pData.hasExtra(EditToDoActivity.P_STARTDATE)){
            lStartDate=pData.getLongExtra(EditToDoActivity.P_STARTDATE,-1);
        }
        Long lEndDate=null;
        if(pData.hasExtra(EditToDoActivity.P_ENDDATE)){
            lEndDate=pData.getLongExtra(EditToDoActivity.P_ENDDATE,-1);
        }
        ds.updateToDo(lId,lIdProject,lIdStatus,lTitle,lComment,lStartDate,lEndDate);
    }


    protected void onActivityResult(final int pRequestCode,
                                    final int pResultCode, @NonNull final Intent pIntent) {
        if(RESULT_OK == pResultCode && REQ_EDIT == pRequestCode) {
                updateTodo(pIntent);
                setResult(RESULT_OK);
                finish();
        } else if(TodoActivity.RES_DELETE_TODO == pResultCode) {
            Intent lIntent = new Intent();
            if(pIntent.getExtras() != null) {
                lIntent.putExtras(pIntent.getExtras());
            }
            setResult(TodoActivity.RES_DELETE_TODO, lIntent);
            finish();
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        if(pItem.getItemId()==R.id.edit){
            editTodo();
        } else {
            return super.onOptionsItemSelected(pItem);
        }
        return true;
    }
}
