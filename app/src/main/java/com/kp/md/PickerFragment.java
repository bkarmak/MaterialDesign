package com.kp.md;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kp.md.picker.MPicker;
import com.kp.md.picker.datepicker.SelectedDate;
import com.kp.md.picker.helpers.MListenerAdapter;
import com.kp.md.picker.helpers.MOptions;
import com.kp.md.picker.recurrencepicker.MRecurrencePicker;

import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickerFragment extends DialogFragment {

    DateFormat mDateFormatter, mTimeFormatter;

    MPicker mMPicker;

    Callback mCallback;

    MListenerAdapter mListener = new MListenerAdapter() {
        @Override
        public void onCancelled() {
            if (mCallback!= null) {
                mCallback.onCancelled();
            }

            dismiss();
        }

        @Override
        public void onDateTimeRecurrenceSet(MPicker mMaterialPicker,
                                            SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            MRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            if (mCallback != null) {
                mCallback.onDateTimeRecurrenceSet(selectedDate,
                        hourOfDay, minute, recurrenceOption, recurrenceRule);
            }

            dismiss();
        }

    };

    public PickerFragment() {
        // Initialize formatters
        mDateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        mTimeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        mTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    }

    // Set activity callback
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mMPicker = (MPicker) getActivity()
                .getLayoutInflater().inflate(R.layout.m_picker, container);

        // Retrieve MOptions
        Bundle arguments = getArguments();
        MOptions options = null;

        // Options can be null, in which case, default
        // options are used.
        if (arguments != null) {
            options = arguments.getParcelable("MATERIAL_OPTIONS");
        }

        mMPicker.initializePicker(options, mListener);
        return mMPicker;
    }

    // For communicating with the activity
    public interface Callback {
        void onCancelled();

        void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                     int hourOfDay, int minute,
                                     MRecurrencePicker.RecurrenceOption recurrenceOption,
                                     String recurrenceRule);
    }
}
