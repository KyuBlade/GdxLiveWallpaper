package com.gdx.wallpaper.transition.fragment;

import android.os.Bundle;
import android.view.View;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.IntValue;
import com.gdx.wallpaper.setting.database.operation.TransitionCycleTimeUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.TransitionFadeTimeUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.transition.type.FadeTransition;
import com.gdx.wallpaper.setting.ui.SeekerEdit;

public class FadeTransitionEditFragment extends TransitionEditFragment<FadeTransition> {

    private SeekerEdit cycleTimeEdit;
    private SeekerEdit fadeTimeEdit;

    public FadeTransitionEditFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cycleTimeEdit = (SeekerEdit) view.findViewById(R.id.cycleTimeEdit);
        fadeTimeEdit = (SeekerEdit) view.findViewById(R.id.fadeTimeEdit);

        final IntValue cycleTime = transition.getCycleTime();
        cycleTimeEdit.setMaxValue(cycleTime.getMax());
        cycleTimeEdit.setValue(cycleTime.getCurrent());
        cycleTimeEdit.setChangeListener(new SeekerEdit.ChangeListener() {
            @Override
            public void onChange(int newValue) {
                cycleTime.setCurrent(newValue);
                BusProvider.getInstance().post(new TransitionChangedEvent(transition.getId(),
                                                                          new TransitionCycleTimeUpdateOperation()));
            }
        });

        final IntValue fadeTime = transition.getFadeTime();
        fadeTimeEdit.setMaxValue(fadeTime.getMax());
        fadeTimeEdit.setValue(fadeTime.getCurrent());
        fadeTimeEdit.setChangeListener(new SeekerEdit.ChangeListener() {
            @Override
            public void onChange(int newValue) {
                fadeTime.setCurrent(newValue);
                BusProvider.getInstance().post(new TransitionChangedEvent(transition.getId(),
                                                                          new TransitionFadeTimeUpdateOperation()));
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.manage_fade_transition;
    }
}