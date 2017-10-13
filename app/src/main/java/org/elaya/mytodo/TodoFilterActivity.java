package org.elaya.mytodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import org.elaya.mytodo.Models.ProjectItem;
import org.elaya.mytodo.Models.StatusItem;
import org.elaya.mytodo.Adapters.StatusCheckListAdapter;


public class TodoFilterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ListView statusListElement;
    private RadioButton  buildInRadioElement;
    private RadioButton  customRadioElement;
    private LinearLayout buildInFilterSectionElement;
    private LinearLayout customFilterSectionElement;
    private Spinner      buildInFilterElement;
    private DataSource ds;
    private ProjectItem projectItem;
    private long projectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo_filter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ds=DataSource.getSource();
        projectId=getIntent().getLongExtra("projectId",-1);
        projectItem=ds.getProjectById(projectId);

        statusListElement=(ListView) findViewById(R.id.statusList);
        StatusCheckListAdapter adapter=new StatusCheckListAdapter(this,ds.getStatusCursor(),ds.getStatusSet(projectId));
        statusListElement.setAdapter(adapter);

        buildInRadioElement = (RadioButton) findViewById(R.id.buildinRadio);
        buildInRadioElement.setOnCheckedChangeListener(this);

        customRadioElement  = (RadioButton) findViewById(R.id.customeRadio);
        customRadioElement.setOnCheckedChangeListener(this);

        buildInFilterElement=(Spinner) findViewById(R.id.buildInFilter);
        FilterTypes.setSpinner(this,buildInFilterElement);

        buildInFilterElement.setSelection((int)projectItem.getFilterType());

        buildInFilterSectionElement = (LinearLayout) findViewById(R.id.buildInFilterSection);
        customFilterSectionElement=(LinearLayout) findViewById(R.id.customFilterSection);

        if(projectItem.getFilterType() != FilterTypes.FT_CUSTOM) {
            buildInRadioElement.setChecked(true);
            customFilterSectionElement.setVisibility(View.GONE);
        } else {
            customRadioElement.setChecked(true);
            buildInFilterSectionElement.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo_filter, menu);
        return true;
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
            for (int lCnt = 0; lCnt < statusListElement.getCount(); lCnt++) {
                lListItem = statusListElement.getChildAt(lCnt);
                lStatusCheck = (CheckBox) (lListItem.findViewById(R.id.statusCheck));
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
