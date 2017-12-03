package org.elaya.mytodo.filter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import org.elaya.mytodo.R;
import org.elaya.mytodo.adapters.StatusCheckListAdapter;
import org.elaya.mytodo.status.StatusItem;
import org.elaya.mytodo.tools.BaseEditActivity;
import org.elaya.mytodo.tools.Helpers;

import java.util.LinkedList;

public class EditFilterActivity extends BaseEditActivity {

    private EditText filterNameElement;
    private RadioGroup dateCondElement;
    private ListView statusListElement;
    private StatusCheckListAdapter statusAdapter;
    private long id;
    private boolean isNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterNameElement=findViewById(R.id.filterName);
        Intent lIntent=getIntent();
        isNew=!lIntent.hasExtra("id");
        id=lIntent.getLongExtra("id",-1);
        filterNameElement.setText(lIntent.getStringExtra("name"));
        dateCondElement= findViewById(R.id.dateCond);

        long lDateFilter=lIntent.getLongExtra("date_filter",-1);

        if(lDateFilter== DateFilter.DF_AFTER_START){
            dateCondElement.check(R.id.dateCondAfterStartDate);
        } else if(lDateFilter == DateFilter.DF_AFTER_END){
            dateCondElement.check(R.id.dateCondAfterEndDate);
        } else {
            dateCondElement.check(R.id.dateCondNotInclude);
        }
        statusListElement= findViewById(R.id.statusList);
        statusAdapter=new StatusCheckListAdapter(this,ds.getStatusCursor(),ds.getStatusSetFilter(id));
        statusListElement.setAdapter(statusAdapter);

        Helpers.setListViewHeightToContent(statusListElement);
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_edit_filter;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_edit_filter;
    }

    @Override
    protected void onSaveClicked() {

        String lFilterName=filterNameElement.getText().toString();

        if(lFilterName.isEmpty()){
            Helpers.warning(this,R.string.err_filter_name);
            return;
        }
        int lDateCond=dateCondElement.getCheckedRadioButtonId();

        long lDateFilter= DateFilter.DF_NONE;
        if(lDateCond == R.id.dateCondAfterStartDate){
            lDateFilter = DateFilter.DF_AFTER_START;
        }  else if(lDateCond == R.id.dateCondAfterEndDate){
            lDateFilter = DateFilter.DF_AFTER_END;
        }
        LinkedList<Long> lStatusList=new LinkedList<>();
        View lView;
        for(int lItem=0;lItem<statusAdapter.getCount();lItem++){
            lView=statusListElement.getChildAt(lItem);
            CheckBox lStatusElement=lView.findViewById(R.id.statusCheck);
            if(lStatusElement.isChecked()) {
                StatusItem lStatus = (StatusItem)lView.getTag();
                lStatusList.add(lStatus.getId());
            }
        }
        if(isNew){
            ds.addFilter(filterNameElement.getText().toString(),lDateFilter,lStatusList);
        } else {
            ds.editFilter(id,filterNameElement.getText().toString(),lDateFilter,lStatusList);
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDeleteClicked() {
        Helpers.confirmDelete(this,R.string.title_delete_filter,new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface pDialog,int pId){
                if(!isNew) {
                    ds.deleteFilter(id);
                    setResult(RESULT_OK);
                } else {
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        });
    }
}
