package com.gdx.wallpaper.setting.fragment.adapter.control;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.fragment.adapter.control.holder.DurationPickerControlHolder;
import com.gdx.wallpaper.setting.ui.dialog.DurationPickerDialogFragment;
import com.gdx.wallpaper.util.Utils;

public class DurationPickerControl extends EditControl<DurationPickerControlHolder>
        implements View.OnClickListener {

    private final long timestamp;
    private final OnDoneListener listener;

    private DurationPickerControlHolder viewHolder;

    public DurationPickerControl(int titleRes, long timestamp,
                                 OnDoneListener listener) {
        super(EditControlType.DURATION_PICKER, titleRes);

        this.timestamp = timestamp;
        this.listener = listener;
    }

    public static DurationPickerControlHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.edit_control, parent, false);
        ViewGroup controlContainer =
                (ViewGroup) view.findViewById(R.id.controlContainer);
        View subView = inflater.inflate(R.layout.duration_picker_control, parent, false);
        controlContainer.addView(subView);
        return new DurationPickerControlHolder(view);
    }

    @Override
    public void bind(DurationPickerControlHolder holder) {
        super.bind(holder);

        this.viewHolder = holder;
        holder.textView.setText(Utils.formatTimestamp(timestamp));
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        DurationPickerDialogFragment fragment =
                DurationPickerDialogFragment.newInstance(titleRes, timestamp);
        fragment.setListener(new OnDoneListener() {
            @Override
            public void onDone(long timestamp) {
                if (listener != null) {
                    listener.onDone(timestamp);
                    viewHolder.textView.setText(Utils.formatTimestamp(timestamp));
                }
            }
        });
        fragment.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), null);
    }

    public interface OnDoneListener {

        void onDone(long timestamp);
    }
}