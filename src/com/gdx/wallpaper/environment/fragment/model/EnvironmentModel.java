package com.gdx.wallpaper.environment.fragment.model;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.fragment.shader.EnvironmentShaderPreviewApplication;
import com.gdx.wallpaper.setting.fragment.adapter.control.SeekerEditControl;
import com.gdx.wallpaper.setting.ui.Model;
import com.gdx.wallpaper.setting.ui.SeekerEdit;

public class EnvironmentModel<T extends Environment> extends Model {

    protected final T environment;

    public EnvironmentModel(EnvironmentShaderPreviewApplication app, T environment) {
        super(app);

        this.environment = environment;

        add(new SeekerEditControl(R.string.environment_transition_duration, app.getTransitionTime(),
                                  0.25f, 4f,
                                  new SeekerEditControl.OnSeekerChangeAdapter() {
                                      @Override
                                      public void onProgressChanged(SeekerEdit seekerEdit,
                                                                    final float progress,
                                                                    boolean fromUser) {

                                      }
                                  }));
        add(new SeekerEditControl(R.string.environment_pause_duration, app.getPauseTime(), 0f, 4f,
                                  new SeekerEditControl.OnSeekerChangeAdapter() {
                                      @Override
                                      public void onProgressChanged(SeekerEdit seekerEdit,
                                                                    final float progress,
                                                                    boolean fromUser) {

                                      }
                                  }));
    }
}