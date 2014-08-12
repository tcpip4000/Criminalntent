package net.ertechnology.criminalintent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by fulano on 8/6/14.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_DATE = "net.ertechnology.criminalintent.date";
    private Date mDate;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        //
        // Due to bug: https://code.google.com/p/android/issues/detail?id=34833,
        // have to create the dialog with "null" handler (second parameter) and then define setButton
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), null, year, month, day);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    // Due to no existence of getDatePicker in API below 11, have to use reflection to get the datePicker object from the dialog
                    // Solution for API 11 and up: DatePicker datePicker = ((DatePickerDialog)dialog).getDatePicker();
                    DatePicker datePicker = null;
                    try {
                        Field mDatePickerField = dialog.getClass().getDeclaredField("mDatePicker");
                        mDatePickerField.setAccessible(true);
                        datePicker = (DatePicker) mDatePickerField.get(dialog);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    // end of reflection

                    mDate =  new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()).getTime();
                    getArguments().putSerializable(EXTRA_DATE, mDate);
                    sendResult(Activity.RESULT_OK);
                }
            }
        });
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

   /*@Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
       return new AlertDialog.Builder(getActivity()).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok, null).create();
   }*/
}
