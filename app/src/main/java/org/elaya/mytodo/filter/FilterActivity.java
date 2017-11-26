package org.elaya.mytodo.filter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.elaya.mytodo.R;
import org.elaya.mytodo.project.DateFilter;
import org.elaya.mytodo.tools.BaseActivity;

public class FilterActivity extends BaseActivity{

    private static final int ACT_ADD_FILTER =101;
    private static final int ACT_EDIT_FILTER=102;
    FilterAdapter adapter;
    ListView      filterListElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new FilterAdapter(this,ds.getFilterCursor());
        filterListElement=findViewById(R.id.filterList);
        filterListElement.setAdapter(adapter);
        filterListElement.setEmptyView(findViewById(R.id.noFilterFound));
        filterListElement.setOnItemClickListener(new ListView.OnItemClickListener(){
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
        return "edit_filter";
    }

    private void addFilter()
    {
        Intent lIntent=new Intent(this,EditFilterActivity.class);
        startActivityForResult(lIntent, ACT_ADD_FILTER);
    }

    public void editFilter(@NonNull View pView)
    {
        Intent lIntent=new Intent(this,EditFilterActivity.class);
        FilterItem lFilter=(FilterItem)pView.getTag();
        lIntent.putExtra("id",lFilter.getId());
        lIntent.putExtra("name",lFilter.getName());
        lIntent.putExtra("date_filter",lFilter.getDateFilter());
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
