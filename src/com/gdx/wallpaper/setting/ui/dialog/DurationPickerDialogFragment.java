package com.gdx.wallpaper.setting.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.fragment.adapter.control.DurationPickerControl;

public class DurationPickerDialogFragment extends DialogFragment {

    public static final String TAG = "DurationPickerDialogFragment";

    private static final String TIMESTAMP = "Timestamp";
    private static final String TITLE = "Title";

    private NumberPicker daysPicker;
    private NumberPicker hoursPicker;
    private NumberPicker minutesPicker;
    private NumberPicker secondsPicker;
    private NumberPicker millisecondsPicker;

    private DurationPickerControl.OnDoneListener listener;

    private long timestamp;

    public static DurationPickerDialogFragment newInstance(int titleRes, long timestamp) {
        Bundle args = new Bundle();
        args.putLong(TIMESTAMP, timestamp);
        args.putInt(TITLE, titleRes);
        DurationPickerDialogFragment fragment = new DurationPickerDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        timestamp = getArguments().getLong(TIMESTAMP);
        int titleRes = getArguments().getInt(TITLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titleRes)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onDone(calculateTimestamp());
                }
            }
        });

        View view = LayoutInflater.from(getContext()).inflate(R.layout.duration_picker_impl, null);
        long localTimestamp = timestamp;
        long msPerSec = 1000L;
        long msPerMin = msPerSec * 60L;
        long msPerHour = msPerMin * 60L;
        long msPerDay = msPerHour * 24;

        long days = localTimestamp / msPerDay;
        localTimestamp = localTimestamp - msPerDay * days;

        long hours = localTimestamp / msPerHour;
        localTimestamp = localTimestamp - msPerHour * hours;

        long minutes = localTimestamp / msPerMin;
        localTimestamp = localTimestamp - msPerMin * minutes;

        long seconds = localTimestamp / msPerSec;
        localTimestamp = localTimestamp - msPerSec * seconds;

        long milliseconds = localTimestamp;

        daysPicker = (NumberPicker) view.findViewById(R.id.daysPicker);
        daysPicker.setMaxValue(365);
        daysPicker.setValue((int) days);

        hoursPicker = (NumberPicker) view.findViewById(R.id.hoursPicker);
        hoursPicker.setMaxValue(23);
        hoursPicker.setValue((int) hours);

        minutesPicker = (NumberPicker) view.findViewById(R.id.minutesPicker);
        minutesPicker.setMaxValue(59);
        minutesPicker.setValue((int) minutes);

        secondsPicker = (NumberPicker) view.findViewById(R.id.secondsPicker);
        secondsPicker.setMaxValue(59);
        secondsPicker.setValue((int) seconds);

        millisecondsPicker = (NumberPicker) view.findViewById(R.id.millisecondsPicker);
        millisecondsPicker.setMaxValue(999);
        millisecondsPicker.setValue((int) milliseconds);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void setListener(
            DurationPickerControl.OnDoneListener listener) {
        this.listener = listener;
    }

    private long calculateTimestamp() {
        timestamp = daysPicker.getValue() * (24 * 60 * 60 * 1000L);
        timestamp += hoursPicker.getValue() * (60 * 60 * 1000L);
        timestamp += minutesPicker.getValue() * (60 * 1000L);
        timestamp += secondsPicker.getValue() * 1000L;
        timestamp += millisecondsPicker.getValue();

        return timestamp;
    }
}
