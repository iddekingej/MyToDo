package org.elaya.mytodo.filter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.BaseActivity;

public class FilterActivity extends BaseActivity{

    private static final int ACT_ADD_FILTER =101;
    private static final int ACT_EDIT_FILTER=102;


    private FilterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new FilterAdapter(this,ds.getFilterCursor());
        ListView lFilterListElement=findViewById(R.id.filterList);
        lFilterListElement.setAdapter(adapter);
        lFilterListElement.setEmptyView(findViewById(R.id.noFilterFound));
        lFilterListElement.setOnItemClickListener(new ListView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> pParent, @NonNull View pView, int pPosition, long pId){
                editFilter(pView);
            }
        });
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_filter;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_filter;
    }

    @NonNull
    @Override
    protected String getHelpName()
    {
        return "filter";
    }

    private void addFilter()
    {
        Intent lIntent=new Intent(this,EditFilterActivity.class);
        startActivityForResult(lIntent, ACT_ADD_FILTER);
    }

    private void editFilter(@NonNull View pView)
    {
        Intent lIntent=new Intent(this,EditFilterActivity.class);
        FilterItem lFilter=(FilterItem)pView.getTag();
        lIntent.putExtra(EditFilterActivity.P_ID,lFilter.getId());
        lIntent.putExtra(EditFilterActivity.P_NAME,lFilter.getName());
        lIntent.putExtra(EditFilterActivity.P_DATE_FILTER,lFilter.getDateFilter());
        startActivityForResult(lIntent,ACT_EDIT_FILTER);
    }

    protected void onActivityResult(int pRequestCode,int pResultCode, Intent pData) {
        if (pResultCode == RESULT_OK) {
            refreshList();
        } else {
            super.onActivityResult(pRequestCode,pResultCode,pData);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        if(pItem.getItemId()==R.id.add){
            addFilter();
        } else {
            super.onOptionsItemSelected(pItem);
        }
        return true;
    }

    private void refreshList()
    {
        adapter.getCursor().close();
        adapter.swapCursor(ds.getFilterCursor());
        adapter.notifyDataSetChanged();
    }


}
