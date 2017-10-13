package org.elaya.mytodo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowTodo extends AppCompatActivity {

    private  static final int REQ_EDIT =100;

    private DataSource ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ds=DataSource.getSource();

        TextView lTitleElement=(TextView)findViewById(R.id.title);
        TextView lStatusElement=(TextView)findViewById(R.id.status);
        TextView lStartDateElement=(TextView)findViewById(R.id.startDate);
        TextView lEndDateElement=(TextView)findViewById(R.id.endDate);
        TextView lCommentElement=(TextView)findViewById(R.id.comment);

        Intent lIntent=getIntent();
        lTitleElement.setText(lIntent.getStringExtra("title"));
        lStatusElement.setText(ds.getStatusTextById(lIntent.getLongExtra("id_status",-1)));
        if(lIntent.hasExtra("startDate")) {
            lStartDateElement.setText(DateHandler.getDateFromLong(lIntent.getLongExtra("startDate",-1)));
        }
        if(lIntent.hasExtra("endDate")){
            lEndDateElement.setText(DateHandler.getDateFromLong(lIntent.getLongExtra("endDate",-1)));
        }
        lCommentElement.setText(lIntent.getStringExtra("comment"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_todo, pMenu);
        return super.onCreateOptionsMenu(pMenu);
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
        long lId=pData.getLongExtra("_id",-1);
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
        ds.updateToDo(lId,lIdProject,lIdStatus,lTitle,lComment,lStartDate,lEndDate);
    }


    protected void onActivityResult(final int pRequestCode,
                                    final int pResultCode, @NonNull final Intent pIntent) {
        if(RESULT_OK == pResultCode && REQ_EDIT == pRequestCode) {
                updateTodo(pIntent);
                setResult(RESULT_OK);
                finish();
        }

    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.edit:
                editTodo();
                break;

            case R.id.back:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return true;
    }
}
