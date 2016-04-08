package com.gdx.wallpaper.environment.fragment.model;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.environment.fragment.shader.EnvironmentShaderPreviewApplication;
import com.gdx.wallpaper.environment.type.CubeEnvironment;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.fragment.adapter.control.SeekerEditControl;
import com.gdx.wallpaper.setting.ui.SeekerEdit;

public class CubeEnvironmentModel extends EnvironmentModel<CubeEnvironment> {

    public CubeEnvironmentModel(EnvironmentShaderPreviewApplication app,
                                final CubeEnvironment environment) {
        super(app, environment);

        add(new SeekerEditControl(R.string.shader_property_reflection, environment.getReflection(),
                                  0f, 1f, new SeekerEditControl.OnSeekerChangeAdapter() {
            @Override
            public void onStopTrackingTouch(SeekerEdit seekerEdit) {
                EnvironmentManager.getInstance().update(environment,
                                                        DatabaseHelper.EnvironmentColumns.REFLECTION,
                                                        environment.getReflection());
            }

            @Override
            public void onProgressChanged(SeekerEdit seekerEdit, float progress, boolean fromUser) {
                environment.setReflection(progress);
            }
        }));
        add(new SeekerEditControl(R.string.shader_property_perspective, environment.getPerspective(),
                                  0f, 1f, new SeekerEditControl.OnSeekerChangeAdapter() {
            @Override
            public void onStopTrackingTouch(SeekerEdit seekerEdit) {
                EnvironmentManager.getInstance().update(environment,
                                                        DatabaseHelper.EnvironmentColumns.PERSPECTIVE,
                                                        environment.getPerspective());
            }

            @Override
            public void onProgressChanged(SeekerEdit seekerEdit, float progress, boolean fromUser) {
                environment.setPerspective(progress);
            }
        }));
        add(new SeekerEditControl(R.string.shader_property_unzoom, environment.getUnzoom(),
                                  0f, 1f, new SeekerEditControl.OnSeekerChangeAdapter() {
            @Override
            public void onStopTrackingTouch(SeekerEdit seekerEdit) {
                EnvironmentManager.getInstance().update(environment,
                                                        DatabaseHelper.EnvironmentColumns.UNZOOM,
                                                        environment.getUnzoom());
            }

            @Override
            public void onProgressChanged(SeekerEdit seekerEdit, float progress, boolean fromUser) {
                environment.setUnzoom(progress);
            }
        }));
        add(new SeekerEditControl(R.string.shader_property_floating, environment.getFloating(),
                                  0f, 1f, new SeekerEditControl.OnSeekerChangeAdapter() {
            @Override
            public void onStopTrackingTouch(SeekerEdit seekerEdit) {
                EnvironmentManager.getInstance().update(environment,
                                                        DatabaseHelper.EnvironmentColumns.FLOATING,
                                                        environment.getFloating());
            }

            @Override
            public void onProgressChanged(SeekerEdit seekerEdit, float progress, boolean fromUser) {
                environment.setFloating(progress);
            }
        }));
    }
}