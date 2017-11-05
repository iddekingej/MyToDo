package org.elaya.mytodo.todo;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.elaya.mytodo.project.DateFilter;
import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.project.ProjectItem;
import org.elaya.mytodo.status.StatusItem;
import org.elaya.mytodo.adapters.StatusCheckListAdapter;
import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.FilterTypes;



public class TodoFilterActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private ListView statusListElement;
    private RadioButton  buildInRadioElement;
    private RadioButton  customRadioElement;
    private LinearLayout buildInFilterSectionElement;
    private LinearLayout customFilterSectionElement;
    private Spinner      buildInFilterElement;
    private ProjectItem  projectItem;
    private RadioGroup   dateCondElement;
    private long projectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        projectId=getIntent().getLongExtra("projectId",-1);
        projectItem=ds.getProjectById(projectId);

        statusListElement= findViewById(R.id.statusList);
        StatusCheckListAdapter adapter=new StatusCheckListAdapter(this,ds.getStatusCursor(),ds.getStatusSet(projectId));
        statusListElement.setAdapter(adapter);

        buildInRadioElement = findViewById(R.id.buildInRadio);
        buildInRadioElement.setOnCheckedChangeListener(this);

        customRadioElement  = findViewById(R.id.customRadio);
        customRadioElement.setOnCheckedChangeListener(this);

        buildInFilterElement= findViewById(R.id.buildInFilter);
        FilterTypes.setSpinner(this,buildInFilterElement);

        buildInFilterElement.setSelection((int)projectItem.getFilterType());

        buildInFilterSectionElement = findViewById(R.id.buildInFilterSection);
        customFilterSectionElement= findViewById(R.id.customFilterSection);

        dateCondElement= findViewById(R.id.dateCond);

        if(projectItem.getFilterType() != FilterTypes.FT_CUSTOM) {
            buildInRadioElement.setChecked(true);
            customFilterSectionElement.setVisibility(View.GONE);
        } else {
            customRadioElement.setChecked(true);
            buildInFilterSectionElement.setVisibility(View.GONE);
        }

        long lDateFilter=projectItem.getDateFilter();
        if(lDateFilter==DateFilter.DF_AFTER_START){
            dateCondElement.check(R.id.dateCondAfterStartDate);
        } else if(lDateFilter == DateFilter.DF_AFTER_END){
            dateCondElement.check(R.id.dateCondAfterEndDate);
        }
    }


    @Override
    protected int getContentResource() {
        return R.layout.activity_todo_filter;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.todo_filter;
    }

    @Override
    public void onCheckedChanged(CompoundButton pCompoundButton, boolean pIsChecked) {
        if(pIsChecked) {
            if (buildInRadioElement.getId() == pCompoundButton.getId()) {
                customRadioElement.setChecked(false);
                customFilterSectionElement.setVisibility(View.GONE);
                buildInFilterSectionElement.setVisibility(View.VISIBLE);

            }

            if(customRadioElement.getId() == pCompoundButton.getId()){
                buildInRadioElement.setChecked(false);
                buildInFilterSectionElement.setVisibility(View.GONE);
                customFilterSectionElement.setVisibility(View.VISIBLE);
            }
        }
    }

    private void saveFilter()
    {
        View lListItem;
        CheckBox lStatusCheck;
        if(customRadioElement.isChecked()) {
            ds.removeStatusFilter(projectId);
            ds.setProjectFilterType(projectItem.getId(), FilterTypes.FT_CUSTOM);

            int lDateCond=dateCondElement.getCheckedRadioButtonId();

            int lDateFilter= DateFilter.DF_NONE;
            if(lDateCond == R.id.dateCondAfterStartDate){
                lDateFilter = DateFilter.DF_AFTER_START;
            }  else if(lDateCond == R.id.dateCondAfterEndDate){
                lDateFilter = DateFilter.DF_AFTER_END;
            }

            ds.setProjectDateFilter(projectItem.getId(),lDateFilter);

            for (int lCnt = 0; lCnt < statusListElement.getCount(); lCnt++) {
                lListItem = statusListElement.getChildAt(lCnt);
                lStatusCheck = lListItem.findViewById(R.id.statusCheck);
                if (lStatusCheck.isChecked()) {
                    StatusItem lStatus = (StatusItem) lListItem.getTag();
                    ds.addStatusFilter(projectId, lStatus.getId());
                }
            }
        } else {
            ds.setProjectFilterType(projectId,(long)buildInFilterElement.getSelectedItemPosition());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.back:
                finish();
                break;

            case R.id.save:
                saveFilter();
                setResult(RESULT_OK);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(pItem);
        }

        return true;
    }
}
