package com.gdx.wallpaper.transition.fragment;

import android.os.Bundle;
import android.view.View;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.IntValue;
import com.gdx.wallpaper.setting.database.operation.TransitionCycleTimeUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.transition.type.NullTransition;
import com.gdx.wallpaper.setting.ui.SeekerEdit;

public class NullTransitionEditFragment extends TransitionEditFragment<NullTransition> {

    private SeekerEdit cycleTimeEdit;

    public NullTransitionEditFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cycleTimeEdit = (SeekerEdit) view.findViewById(R.id.cycleTimeEdit);

        IntValue _cycleTime = transition.getCycleTime();
        cycleTimeEdit.setMaxValue(_cycleTime.getMax());
        cycleTimeEdit.setValue(_cycleTime.getCurrent());
        cycleTimeEdit.setChangeListener(new SeekerEdit.ChangeListener() {
            @Override
            public void onChange(int newValue) {
                transition.getCycleTime().setCurrent(newValue);
                BusProvider.getInstance().post(new TransitionChangedEvent(transition.getId(),
                                                                          new TransitionCycleTimeUpdateOperation()));
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.manage_null_transition;
    }
}