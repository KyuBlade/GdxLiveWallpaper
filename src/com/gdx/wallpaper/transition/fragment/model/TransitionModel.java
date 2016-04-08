package com.gdx.wallpaper.transition.fragment.model;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.fragment.adapter.control.DurationPickerControl;
import com.gdx.wallpaper.setting.ui.Model;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;
import com.gdx.wallpaper.transition.fragment.shader.TransitionShaderPreviewApplication;

public class TransitionModel<T extends Transition> extends Model {

    protected final T transition;

    public TransitionModel(final TransitionShaderPreviewApplication app, final T transition) {
        super(app);

        this.transition = transition;

        add(new DurationPickerControl(R.string.transition_transition_duration,
                                      transition.getTransitionDuration(),
                                      new DurationPickerControl.OnDoneListener() {
                                          @Override
                                          public void onDone(long timestamp) {
                                              transition.setTransitionDuration(timestamp);
                                              TransitionManager.getInstance().update(transition,
                                                                                     DatabaseHelper.TransitionColumns.TRANSITION_DURATION,
                                                                                     timestamp);
                                              app.setTransitionTime(timestamp);
                                          }
                                      }));
        add(new DurationPickerControl(R.string.transition_pause_duration,
                                      transition.getPauseDuration(),
                                      new DurationPickerControl.OnDoneListener() {
                                          @Override
                                          public void onDone(long timestamp) {
                                              transition.setPauseDuration(timestamp);
                                              TransitionManager.getInstance().update(transition,
                                                                                     DatabaseHelper.TransitionColumns.PAUSE_DURATION,
                                                                                     timestamp);
                                              app.setPauseTime(timestamp);
                                          }
                                      }));
    }
}