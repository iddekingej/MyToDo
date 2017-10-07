package org.elaya.mytodo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Date;

/**
 * Date picker popup
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText dateElement;



    static DatePickerFragment newInstance(EditText pDateElement)
    {
        DatePickerFragment lFragment=new DatePickerFragment();
        lFragment.setDateElement(pDateElement);
        return lFragment;
    }

    private void setDateElement(EditText pDateElement)
    {
        dateElement=pDateElement;
    }


    public Dialog onCreateDialog(Bundle pSaveInstanceState)
    {

        Date lDate= DateHandler.getDateFromText(dateElement.getText().toString());
        if(lDate==null){
            lDate=new Date();
        }
        return new DatePickerDialog(getActivity(),this,lDate.getYear()+1900,lDate.getMonth(),lDate.getDate());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int pYear, int pMonth, int pDay) {
        Date lDate=new Date();
        lDate.setDate(pDay);
        lDate.setMonth(pMonth);
        lDate.setYear(pYear-1900);
        dateElement.setText(DateHandler.getTextFromDate(lDate));
    }
}
