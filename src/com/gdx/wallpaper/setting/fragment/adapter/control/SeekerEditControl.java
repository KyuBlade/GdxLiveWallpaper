package com.gdx.wallpaper.setting.fragment.adapter.control;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.fragment.adapter.control.holder.SeekerEditControlHolder;
import com.gdx.wallpaper.setting.ui.SeekerEdit;

public class SeekerEditControl extends EditControl<SeekerEditControlHolder> {

    private float value;
    private final float minValue;
    private final float maxValue;
    private final SeekerEdit.OnSeekerChangeListener listener;

    public SeekerEditControl(int titleStrId, float currentValue, float minValue, float maxValue,
                             SeekerEdit.OnSeekerChangeListener listener) {
        super(EditControlType.SEEKER, titleStrId);

        this.value = currentValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.listener = listener;
    }

    public static SeekerEditControlHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.edit_control, parent, false);
        ViewGroup controlContainer =
                (ViewGroup) view.findViewById(R.id.controlContainer);
        View subView = inflater.inflate(R.layout.seeker_edit, parent, false);
        controlContainer.addView(subView);
        return new SeekerEditControlHolder(view);
    }

    @Override
    public void bind(SeekerEditControlHolder holder) {
        super.bind(holder);

        SeekerEdit seeker = holder.seeker;
        seeker.setMinValue(minValue);
        seeker.setMaxValue(maxValue);
        seeker.setValue(value);
        seeker.setListener(listener);
    }

    public static class OnSeekerChangeAdapter implements SeekerEdit.OnSeekerChangeListener {

        @Override
        public void onStopTrackingTouch(SeekerEdit seekerEdit) {

        }

        @Override
        public void onStartTrackingTouch(SeekerEdit seekerEdit) {

        }

        @Override
        public void onProgressChanged(SeekerEdit seekerEdit, float progress, boolean fromUser) {

        }
    }
}