package com.gdx.wallpaper.environment.fragment.model;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.environment.fragment.shader.EnvironmentShaderPreviewApplication;
import com.gdx.wallpaper.environment.type.SwapEnvironment;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.fragment.adapter.control.SeekerEditControl;
import com.gdx.wallpaper.setting.ui.SeekerEdit;

public class SwapEnvironmentModel extends EnvironmentModel<SwapEnvironment> {

    public SwapEnvironmentModel(EnvironmentShaderPreviewApplication app,
                                final SwapEnvironment environment) {
        super(app, environment);

        add(new SeekerEditControl(R.string.shader_property_reflection, environment.getReflection(), 0f, 1f,
                                  new SeekerEditControl.OnSeekerChangeAdapter() {
                                      @Override
                                      public void onStopTrackingTouch(SeekerEdit seekerEdit) {
                                          EnvironmentManager.getInstance().update(environment,
                                                                                  DatabaseHelper.EnvironmentColumns.REFLECTION,
                                                                                  seekerEdit
                                                                                          .getValue());
                                      }

                                      @Override
                                      public void onProgressChanged(SeekerEdit seekerEdit,
                                                                    final float progress,
                                                                    boolean fromUser) {
                                          environment.setReflection(progress);
                                      }
                                  }));

        add(new SeekerEditControl(R.string.shader_property_perspective, environment.getPerspective(), 0f, 2f,
                                  new SeekerEditControl.OnSeekerChangeAdapter() {
                                      @Override
                                      public void onStopTrackingTouch(SeekerEdit seekerEdit) {
                                          EnvironmentManager.getInstance().update(environment,
                                                                                  DatabaseHelper.EnvironmentColumns.PERSPECTIVE,
                                                                                  seekerEdit
                                                                                          .getValue());
                                      }

                                      @Override
                                      public void onProgressChanged(SeekerEdit seekerEdit,
                                                                    final float progress,
                                                                    boolean fromUser) {
                                          environment.setPerspective(progress);
                                      }
                                  }));
        add(new SeekerEditControl(R.string.shader_property_depth, environment.getDepth(), 2.6f, 5f,
                                  new SeekerEditControl.OnSeekerChangeAdapter() {
                                      @Override
                                      public void onStopTrackingTouch(SeekerEdit seekerEdit) {
                                          EnvironmentManager.getInstance().update(environment,
                                                                                  DatabaseHelper.EnvironmentColumns.DEPTH,
                                                                                  seekerEdit
                                                                                          .getValue());
                                      }

                                      @Override
                                      public void onProgressChanged(SeekerEdit seekerEdit,
                                                                    final float progress,
                                                                    boolean fromUser) {
                                          environment.setDepth(progress);
                                      }
                                  }));
    }
}