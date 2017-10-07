package org.elaya.mytodo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class EditStatusActivity extends AppCompatActivity {

    private long id;
    private EditText descriptionElement;
    private EditText positionElement;
    private Spinner  actionType;
    private boolean statusIsUsed;
    private CheckBox  activeElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataSource ds = DataSource.getSource();


        positionElement=(EditText)findViewById(R.id.position);
        Intent lIntent=getIntent();


        id=lIntent.getLongExtra("id",-1);

        if(lIntent.hasExtra("position")) {
            positionElement.setText(String.valueOf(lIntent.getLongExtra("position", 0)));
        } else {
            positionElement.setText("");
        }

        descriptionElement=(EditText)findViewById(R.id.description);
        descriptionElement.setText(lIntent.getStringExtra("description"));

        ArrayAdapter actionTypeAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,ActionTypes.getActionTypes(this));

        actionType=(Spinner)findViewById(R.id.actionType);
        actionType.setAdapter(actionTypeAdapter);

        long lActionType=lIntent.getLongExtra("actionType",0);

        if(lActionType >= ActionTypes.MAX){
            lActionType = 0;
        }
        actionType.setSelection((int)lActionType);

        boolean lActive=lIntent.getBooleanExtra("active",true);
        activeElement=(CheckBox)findViewById(R.id.active) ;
        activeElement.setChecked(lActive);


        statusIsUsed=ds.statusIsUsed(id);
    }

    /**
     * Set the toolbar option menu
     *
     * @param pMenu Toolbar menu
     * @return If handled
     */
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_status, pMenu);
        return super.onCreateOptionsMenu(pMenu);
    }

    private void onSavePressed()
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

            case R.id.back:
                finish();
                break;

            case R.id.save:
                onSavePressed();
                break;

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
