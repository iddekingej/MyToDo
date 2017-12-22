package org.elaya.mytodo.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.elaya.mytodo.project.ProjectItem;
import org.elaya.mytodo.settings.Settings;
import org.elaya.mytodo.tools.BaseEditActivity;
import org.elaya.mytodo.tools.DatePickerFragment;
import org.elaya.mytodo.status.StatusItem;
import org.elaya.mytodo.status.StatusSpinnerAdapter;
import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.DateHandler;
import org.elaya.mytodo.tools.Helpers;
import org.joda.time.DateTime;



public class EditToDoActivity extends BaseEditActivity {

    public static final String P_ID="_id";
    public static final String P_ID_PROJECT="id_project";
    public static final String P_ID_STATUS="id_status";
    public static final String P_TITLE="title";
    public static final String P_COMMENT="comment";
    public static final String P_STARTDATE="startDate";
    public static final String P_ENDDATE="endDate";

    private boolean isNew=false;
    private long id;
    private EditText title;
    private EditText startDateElement;
    private EditText endDateElement;
    private EditText comment;
    private Spinner  statusElement;
    private Spinner  projectElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent lIntent=getIntent();
        isNew = !lIntent.hasExtra(P_ID);
        id=lIntent.getLongExtra(P_ID,-1);
        long lIdProject=lIntent.getLongExtra(P_ID_PROJECT,-1);
        Long lIdStatus;
        if(isNew){
            lIdStatus = Settings.getIdDefaultStatus();
        } else {
            lIdStatus = lIntent.getLongExtra(P_ID_STATUS, -1);
        }

        title= findViewById(R.id.title);
        title.setText(lIntent.getStringExtra(P_TITLE));

        comment= findViewById(R.id.comment);
        comment.setText(lIntent.getStringExtra(P_COMMENT));

        startDateElement= findViewById(R.id.startDate);
        if(lIntent.hasExtra(P_STARTDATE)){
            startDateElement.setText(DateHandler.getDateTextFromLong(lIntent.getLongExtra(P_STARTDATE,-1)));
        }

        endDateElement= findViewById(R.id.endDate);
        if(lIntent.hasExtra(P_ENDDATE)){
            endDateElement.setText(DateHandler.getDateTextFromLong(lIntent.getLongExtra(P_ENDDATE,-1)));
        }

        statusElement= findViewById(R.id.status);
        StatusSpinnerAdapter spinnerAdapter=new StatusSpinnerAdapter(this,ds.getActiveStatusCursor(lIdStatus));
        statusElement.setAdapter(spinnerAdapter);

        projectElement=findViewById(R.id.project);
        ArrayAdapter<ProjectItem> lProjectAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        int lCurrentPos=ds.fillProjectAdapter(lProjectAdapter,lIdProject);
        projectElement.setAdapter(lProjectAdapter);
        projectElement.setSelection(lCurrentPos);

        int lNum=spinnerAdapter.getCount();
        if(lIdStatus != null) {
            for (int lCnt = 0; lCnt < lNum; lCnt++) {
                long lId = spinnerAdapter.getItemId(lCnt);
                if (lId == lIdStatus) {
                    statusElement.setSelection(lCnt);
                    break;
                }
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

    @NonNull
    @Override
    protected String getHelpName(){ return "edit_todo";}


    protected void onDeleteClicked()
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

    public void  startDatePicker(@SuppressWarnings("UnusedParameters") View pView)
    {
        DatePickerFragment lFragment=DatePickerFragment.newInstance(startDateElement);
        lFragment.show(getFragmentManager(), "datePicker");
    }

    public void  endDatePicker(@SuppressWarnings("UnusedParameters") View pView)
    {
        DatePickerFragment lFragment=DatePickerFragment.newInstance(endDateElement);
        lFragment.show(getFragmentManager(), "datePicker");
    }


    protected void onSaveClicked()
    {
        DateTime lStartDate;
        DateTime lEndDate;

        try {
            lStartDate = DateHandler.getDateFromText(startDateElement.getText().toString());
        }catch(IllegalArgumentException lE){
            Helpers.warning(this,R.string.warning_start_date_invalid);
            return;
        }

        try {
            lEndDate = DateHandler.getDateFromText(endDateElement.getText().toString());
        } catch(IllegalArgumentException lE){
            Helpers.warning(this,R.string.warning_end_date_invalid);
            return;
        }

        if(lEndDate != null && lStartDate!= null && lEndDate.isBefore(lStartDate)){
            Helpers.warning(this,R.string.err_end_before_start_date);
            return;
        }
        if(title.getText().length()==0){
            Helpers.warning(this,R.string.err_title_empty);
            return;
        }
        ProjectItem lProjectItem=(ProjectItem)projectElement.getSelectedItem();

        Intent lIntent = new Intent();
        lIntent.putExtra(P_ID,id);
        lIntent.putExtra(P_ID_PROJECT,lProjectItem.getId());
        StatusItem lItem=(StatusItem)statusElement.getSelectedView().getTag();
        lIntent.putExtra(P_ID_STATUS,lItem.getId());
        lIntent.putExtra(P_TITLE,title.getText().toString());
        lIntent.putExtra(P_COMMENT,comment.getText().toString());
        if(lStartDate != null){
            lIntent.putExtra(P_STARTDATE,lStartDate.getMillis());
        }
        if(lEndDate != null){
            lIntent.putExtra(P_ENDDATE,lEndDate.getMillis());
        }
        setResult(RESULT_OK,lIntent);
        finish();
    }

}
