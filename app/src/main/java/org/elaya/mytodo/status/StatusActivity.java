package org.elaya.mytodo.status;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.R;
import org.elaya.mytodo.adapters.StatusListAdapter;
import org.elaya.mytodo.tools.Helpers;

public class StatusActivity extends BaseActivity {

    private static final int RES_NEW_STATUS=100;
    private static final int RES_EDIT_STATUS=101;
    public static final int RESULT_DELETE_STATUS=RESULT_FIRST_USER+1;

    private StatusListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView statusList=(ListView) findViewById(R.id.statusList);
        adapter=new StatusListAdapter(this,ds.getStatusCursor());
        statusList.setAdapter(adapter);
        statusList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> pAdapterView, @NonNull View pView, int pPosition, long pId) {
                editStatusClicked((StatusItem)pView.getTag());
            }
        });
    }

    @Override
    protected int getContentResource() {
        return R.layout.activity_status;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_status;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.back:
                finish();
                break;

            case R.id.add:
                newStatusClicked();
                break;

            case R.id.help:
                Helpers.openHelp(this,"status");
                break;

            default:
                return super.onOptionsItemSelected(pItem);
        }
        return true;

    }

    private void editStatusClicked(@NonNull StatusItem pItem)
    {
        Intent lIntent=new Intent(this,EditStatusActivity.class);
        lIntent.putExtra("id",pItem.getId());
        lIntent.putExtra("position",pItem.getPosition());
        lIntent.putExtra("actionType",pItem.getActionType());
        lIntent.putExtra("description",pItem.getDescription());
        lIntent.putExtra("active",pItem.getActive());
        startActivityForResult(lIntent,RES_EDIT_STATUS);
    }

    private void newStatusClicked()
    {
        Intent lIntent=new Intent(this,EditStatusActivity.class);
        startActivityForResult(lIntent,RES_NEW_STATUS);
    }

    private void saveNewStatus(@NonNull Intent pIntent)
    {
        long lPosition=pIntent.getLongExtra("position",0);
        long lActionType=pIntent.getLongExtra("actionType",0);
        String lDescription=pIntent.getStringExtra("description");
        boolean lActive=pIntent.getBooleanExtra("active",true);
        ds.addStatus(lActionType,lPosition,lDescription,lActive);
        refreshList();
    }

    private void updateStatus(@NonNull Intent pIntent)
    {
        long lId=pIntent.getLongExtra("id",0);
        long lPosition=pIntent.getLongExtra("position",0);
        long lActionType=pIntent.getLongExtra("actionType",0);
        String lDescription=pIntent.getStringExtra("description");
        boolean lActive=pIntent.getBooleanExtra("active",true);
        ds.updateStatus(lId,lPosition,lActionType,lDescription,lActive);
        refreshList();
    }

    private void deleteStatus(@NonNull Intent pIntent)
    {
        long lId=pIntent.getLongExtra("id",0);
        ds.deleteStatus(lId);
        refreshList();
    }
    protected void onActivityResult(int pRequestCode,int pResultCode,@NonNull Intent pData) {
        switch(pResultCode){
            case RESULT_OK:

                switch (pRequestCode) {
                    case RES_NEW_STATUS:
                        saveNewStatus(pData);
                        break;

                    case RES_EDIT_STATUS:
                        updateStatus(pData);
                        break;
                }
                break;

            case RESULT_DELETE_STATUS:
                deleteStatus(pData);

        }
    }

    private void refreshList()
    {
        adapter.getCursor().close();
        adapter.swapCursor(ds.getStatusCursor());
        adapter.notifyDataSetChanged();
    }
}
