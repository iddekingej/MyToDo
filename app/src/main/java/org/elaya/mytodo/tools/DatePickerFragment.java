package org.elaya.mytodo.tools;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.EditText;
import org.joda.time.DateTime;



/**
 * Date picker popup
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText dateElement;



    @NonNull
    public static DatePickerFragment newInstance(@NonNull EditText pDateElement)
    {
        DatePickerFragment lFragment=new DatePickerFragment();
        lFragment.setDateElement(pDateElement);
        return lFragment;
    }

    private void setDateElement(EditText pDateElement)
    {
        dateElement=pDateElement;
    }


    @NonNull
    public Dialog onCreateDialog(@NonNull Bundle pSaveInstanceState)
    {

        DateTime lDate;
        try {
            lDate = DateHandler.getDateFromText(dateElement.getText().toString());
        }catch(IllegalArgumentException lE) {
                lDate = null;
        }
        if(lDate==null){
            lDate=DateTime.now();
        }
        return new DatePickerDialog(getActivity(),this,lDate.getYear(),lDate.getMonthOfYear()-1,lDate.getDayOfMonth());
    }

    @Override
    public void onDateSet(DatePicker datePicker, int pYear, int pMonth, int pDay) {
        DateTime lDate=new DateTime(pYear,pMonth+1,pDay,0,0);
        dateElement.setText(DateHandler.getTextFromDate(lDate));
    }
}
