package org.elaya.mytodo.status;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.elaya.mytodo.tools.BaseActivity;
import org.elaya.mytodo.R;
import org.elaya.mytodo.tools.ActionTypes;
import org.elaya.mytodo.tools.BaseEditActivity;
import org.elaya.mytodo.tools.Helpers;

public class EditStatusActivity extends BaseEditActivity {

    private long id;
    private EditText descriptionElement;
    private EditText positionElement;
    private Spinner  actionType;
    private boolean statusIsUsed;
    private CheckBox  activeElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        positionElement= findViewById(R.id.position);
        Intent lIntent=getIntent();


        id=lIntent.getLongExtra("id",-1);

        if(lIntent.hasExtra("position")) {
            positionElement.setText(String.valueOf(lIntent.getLongExtra("position", 0)));
        } else {
            positionElement.setText("");
        }

        descriptionElement= findViewById(R.id.description);
        descriptionElement.setText(lIntent.getStringExtra("description"));

        ArrayAdapter actionTypeAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, ActionTypes.getActionTypes(this));

        actionType= findViewById(R.id.actionType);
        actionType.setAdapter(actionTypeAdapter);

        long lActionType=lIntent.getLongExtra("actionType",0);

        if(lActionType >= ActionTypes.MAX){
            lActionType = 0;
        }
        actionType.setSelection((int)lActionType);

        boolean lActive=lIntent.getBooleanExtra("active",true);
        activeElement= findViewById(R.id.active);
        activeElement.setChecked(lActive);


        statusIsUsed=ds.statusIsUsed(id);
    }


    @Override
    protected int getContentResource() {
        return R.layout.activity_edit_status;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.menu_edit_status;
    }

    protected void onSaveClicked()
    {
        String lDescription=descriptionElement.getText().toString();
        Editable lPositionText=positionElement.getText();
        if("".equals(lDescription)){
            Helpers.warning(this,R.string.warning_status_description_mandatory);
            return;
        }
        long lPosition;
        try {
            lPosition = Long.valueOf(lPositionText.toString());
        }catch(NumberFormatException e){
            Helpers.warning(this,R.string.warning_position_number_mandatory);
            return;
        }
        Intent lIntent = new Intent();
        lIntent.putExtra("id",id);
        lIntent.putExtra("position",lPosition);
        lIntent.putExtra("actionType",(long)actionType.getSelectedItemPosition());
        lIntent.putExtra("description",lDescription);
        lIntent.putExtra("active",activeElement.isChecked());
        setResult(RESULT_OK,lIntent);
        finish();
    }

    /**
     * When the delete button is pressed, the form returns with RESULT_DELETE_STATUS
     * and the ID of the status. In the  @see StatusActivity class
     */
    private void onDeletePressed()
    {
        if(statusIsUsed){
            Helpers.warning(this,R.string.warning_cant_delete_status_used);
        } else {

            Helpers.confirmDelete(this,R.string.ask_delete_status,
                    new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface pDialog,int pId){
                            Intent lIntent = new Intent();
                            lIntent.putExtra("id", id);
                            setResult(StatusActivity.RESULT_DELETE_STATUS,lIntent);
                            finish();
                        }
                    }
            );
        }
    }

    /**
     * Handles tool menu/icon  events .
     *
     * In this method the following menu actions are handled:
     * R.id.back  - exit form without doing anything
     * R.id.save  - save entered data.
     * R.id.help  - display help
     * R.id.delete - delete the edited status
     * @param pItem  MenuItem that is clicked
     * @return       True if handled
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem pItem) {
        switch (pItem.getItemId()) {

            case R.id.help:
                Helpers.openHelp(this,"edit_status");
                break;

            case R.id.delete:
                onDeletePressed();
                break;

            default:
                return super.onOptionsItemSelected(pItem);
        }
        return true;
    }
}
