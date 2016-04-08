package com.gdx.wallpaper.environment.fragment.shader;

import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.renderer.preview.EnvironmentPreviewRenderer;
import com.gdx.wallpaper.setting.fragment.AbstractPreviewRenderer;
import com.gdx.wallpaper.setting.fragment.AbstractShaderPreviewApplication;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EnvironmentShaderPreviewApplication extends AbstractShaderPreviewApplication {

    private Environment environment;

    public EnvironmentShaderPreviewApplication(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    protected AbstractPreviewRenderer initRenderer()
            throws IllegalAccessException, InstantiationException {
        Class<? extends EnvironmentPreviewRenderer>
                clazz =
                environment.getType().getPreviewRendererClass();
        try {
            Constructor<? extends EnvironmentPreviewRenderer> constructor =
                    clazz.getConstructor(environment.getClass());
            return constructor.newInstance(environment);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}