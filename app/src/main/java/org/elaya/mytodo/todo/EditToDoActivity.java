package org.elaya.mytodo.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.tools.DatePickerFragment;
import org.elaya.mytodo.status.StatusItem;
import org.elaya.mytodo.adapters.StatusSpinnerAdapter;
import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.DateHandler;
import org.elaya.mytodo.tools.Helpers;
import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;


public class EditToDoActivity extends BaseActivity {

    private boolean isNew=false;
    private long id;
    private long idProject;
    private EditText title;
    private EditText startDateElement;
    private EditText endDateElement;
    private EditText comment;
    private Spinner  statusElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent lIntent=getIntent();
        isNew = !lIntent.hasExtra("_id");
        id=lIntent.getLongExtra("_id",-1);
        idProject=lIntent.getLongExtra("id_project",-1);
        long idStatus=lIntent.getLongExtra("id_status",-1);

        title=(EditText)findViewById(R.id.title);
        title.setText(lIntent.getStringExtra("title"));

        comment=(EditText)findViewById(R.id.comment);
        comment.setText(lIntent.getStringExtra("comment"));

        startDateElement=(EditText)findViewById(R.id.startDate);
        if(lIntent.hasExtra("startDate")){
            startDateElement.setText(DateHandler.getDateFromLong(lIntent.getLongExtra("startDate",-1)));
        }

        endDateElement=(EditText)findViewById(R.id.endDate);
        if(lIntent.hasExtra("endDate")){
            endDateElement.setText(DateHandler.getDateFromLong(lIntent.getLongExtra("endDate",-1)));
        }

        statusElement=(Spinner)findViewById(R.id.status);
        StatusSpinnerAdapter spinnerAdapter=new StatusSpinnerAdapter(this,ds.getActiveStatusCursor(idStatus));
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
    public boolean onCreateOptionsMenu(@NonNull Menu pMenu) {

        super.onCreateOptionsMenu(pMenu);
        if(isNew) {
            MenuItem lDeleteMenu = pMenu.findItem(R.id.delete);
            invalidateOptionsMenu();
            lDeleteMenu.setVisible(false);
        }
        return true;
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_edit_todo;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_edit_todo;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
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

    private void onDeleteTodoClicked()
    {
        Helpers.confirmDelete(this,R.string.delete_todo_question,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface pDialog,int pId){
                        doDelete();
                    }
                }
        );
    }

    private void doDelete()
    {
        Intent lIntent = new Intent();
        lIntent.putExtra("_id",id);
        setResult(TodoActivity.RES_DELETE_TODO,lIntent);
        finish();
    }
    private void onCancelClicked()
    {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void  startDatePicker(View pView)
    {
        DatePickerFragment lFragment=DatePickerFragment.newInstance(startDateElement);
        lFragment.show(getFragmentManager(), "datePicker");
    }

    public void  endDatePicker(View pView)
    {
        DatePickerFragment lFragment=DatePickerFragment.newInstance(endDateElement);
        lFragment.show(getFragmentManager(), "datePicker");
    }


    private void onSaveClicked()
    {
        DateTime lStartDate;
        DateTime lEndDate;

        try {
            lStartDate = DateHandler.getDateFromText(startDateElement.getText().toString());
        }catch(IllegalFieldValueException lE){
            Helpers.warning(this,R.string.warning_start_date_invalid);
            return;
        }

        try {
            lEndDate = DateHandler.getDateFromText(endDateElement.getText().toString());
        } catch(IllegalFieldValueException lE){
            Helpers.warning(this,R.string.warning_end_date_invalid);
            return;
        }

        if(lEndDate != null && lStartDate!= null && lEndDate.isBefore(lStartDate)){
            Helpers.warning(this,R.string.err_end_before_start_date);
            return;
        }
        Intent lIntent = new Intent();
        lIntent.putExtra("_id",id);
        lIntent.putExtra("id_project",idProject);
        StatusItem lItem=(StatusItem)statusElement.getSelectedView().getTag();
        lIntent.putExtra("id_status",lItem.getId());
        lIntent.putExtra("title",title.getText().toString());
        lIntent.putExtra("comment",comment.getText().toString());
        if(lStartDate != null){
            lIntent.putExtra("startDate",lStartDate.getMillis());
        }
        if(lEndDate != null){
            lIntent.putExtra("endDate",lEndDate.getMillis());
        }
        setResult(RESULT_OK,lIntent);
        finish();
    }

}
