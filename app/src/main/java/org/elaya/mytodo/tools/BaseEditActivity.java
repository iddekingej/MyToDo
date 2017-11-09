package org.elaya.mytodo.tools;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import org.elaya.mytodo.R;

/**
 * Base activity class for edit forms
 */

public abstract class BaseEditActivity extends BaseActivity {

    private void onCancelClicked()
    {
        Helpers.confirm(this,R.string.title_cancel_edit ,R.string.title_cancel_confirm,R.string.title_do_not_cancel,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface pDialog,int pId){
                        Intent lIntent = new Intent();
                        setResult(RESULT_CANCELED,lIntent);
                        finish();
                    }
                }
        );
    }

    protected abstract void onSaveClicked();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        switch (pItem.getItemId()) {
            case R.id.cancel:
                onCancelClicked();
                break;
            case R.id.save:
                onSaveClicked();
                break;
            default:
                return super.onOptionsItemSelected(pItem);
        }
        return true;
    }
}
