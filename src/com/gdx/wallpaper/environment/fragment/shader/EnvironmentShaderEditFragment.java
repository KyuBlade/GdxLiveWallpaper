package com.gdx.wallpaper.environment.fragment.shader;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.environment.fragment.model.EnvironmentModel;
import com.gdx.wallpaper.setting.fragment.AbstractPreviewFragment;
import com.gdx.wallpaper.setting.fragment.AbstractShaderEditFragment;
import com.gdx.wallpaper.setting.fragment.AbstractShaderPreviewApplication;
import com.gdx.wallpaper.setting.ui.Model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EnvironmentShaderEditFragment extends AbstractShaderEditFragment {

    private static final String ENVIRONMENT_ID = "EnvironmentId";

    private Environment environment;
    private EnvironmentShaderEditAdapter adapter;
    private Model model;

    public EnvironmentShaderEditFragment() {
    }

    public static EnvironmentShaderEditFragment newInstance(long environmentId) {
        EnvironmentShaderEditFragment fragment = new EnvironmentShaderEditFragment();
        Bundle args = new Bundle();
        args.putLong(ENVIRONMENT_ID, environmentId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long envId = getArguments().getLong(ENVIRONMENT_ID);
        environment = EnvironmentManager.getInstance().get(envId);

        previewFragment = EnvironmentPreviewFragment.newInstance(environment.getId());
        previewFragment.setTargetFragment(previewFragment, 0);
        previewFragment.setListener(new AbstractPreviewFragment.OnLoadedListener() {
            @Override
            public void onLoaded(AbstractShaderPreviewApplication app) {
                try {
                    Class<? extends EnvironmentModel> clazz = environment.getType().getModelClass();
                    Constructor<? extends Model>
                            constructor =
                            clazz.getConstructor(EnvironmentShaderPreviewApplication.class,
                                                 environment.getClass());
                    model = constructor.newInstance(app, environment);
                    adapter = new EnvironmentShaderEditAdapter(model);
                    recyclerView.setAdapter(adapter);
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}