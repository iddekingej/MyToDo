package org.elaya.mytodo.filter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.tools.Helpers;

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
        lFilterListElement.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> pParent,@NonNull final View pView, int pPosition, long pId)
            {
                return longClickFilter(pView);
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

    /**
     * When the user long clicks a filter, a popup menu appears
     * The user can select edit of delete filter
     *
     * @param pView   ListView item that is selected
     * @return        true->event is handled
     */
    private boolean longClickFilter(@NonNull final View pView)
    {

            PopupMenu lPopup=new PopupMenu(this,pView);
            lPopup.getMenuInflater().inflate(R.menu.menu_long_filter,lPopup.getMenu());
            lPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

                public boolean onMenuItemClick(@NonNull MenuItem pItem){

                    switch(pItem.getItemId()) {
                        case R.id.editProject:
                            editFilter(pView);
                            break;
                        case R.id.deleteProject:
                            deleteFilter(pView);
                            break;
                        default:
                            return false;
                    }
                    return true;
                }

            });
            lPopup.show();
            return true;

    }


    private void deleteFilter(final View pView)
    {
        Helpers.confirmDelete(this,R.string.title_delete_filter,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface pDialog, int pId) {
                        FilterItem lFilter = (FilterItem) pView.getTag();
                        ds.deleteFilter(lFilter.getId());
                        refreshList();
                    }
                }
            );
    }

    private void refreshList()
    {

        adapter.getCursor().close();
        adapter.swapCursor(ds.getFilterCursor());
        adapter.notifyDataSetChanged();
    }


}
