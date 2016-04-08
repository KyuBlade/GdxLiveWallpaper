package com.gdx.wallpaper.environment.fragment.shader;

import android.os.Bundle;

import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.setting.fragment.AbstractPreviewFragment;

public class EnvironmentPreviewFragment
        extends AbstractPreviewFragment<EnvironmentShaderPreviewApplication> {

    private static final String ENVIRONMENT_ID = "EnvironmentId";

    public static EnvironmentPreviewFragment newInstance(long environmentId) {
        Bundle args = new Bundle();
        args.putLong(ENVIRONMENT_ID, environmentId);

        EnvironmentPreviewFragment fragment = new EnvironmentPreviewFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected EnvironmentShaderPreviewApplication initApplication() {
        long envId = getArguments().getLong(ENVIRONMENT_ID);
        Environment env = EnvironmentManager.getInstance().get(envId);
        return new EnvironmentShaderPreviewApplication(env);
    }
}