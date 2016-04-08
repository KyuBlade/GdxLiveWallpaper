package com.gdx.wallpaper.transition.fragment.shader;

import com.gdx.wallpaper.setting.fragment.AbstractPreviewRenderer;
import com.gdx.wallpaper.setting.fragment.AbstractShaderPreviewApplication;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.renderer.preview.TransitionPreviewRenderer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TransitionShaderPreviewApplication extends AbstractShaderPreviewApplication {

    private Transition transition;

    public TransitionShaderPreviewApplication(Transition transition) {
        this.transition = transition;
        setTransitionTime(transition.getTransitionDuration());
        setPauseTime(transition.getPauseDuration());
    }

    @Override
    protected AbstractPreviewRenderer initRenderer()
            throws IllegalAccessException, InstantiationException {
        Class<? extends TransitionPreviewRenderer>
                clazz =
                transition.getType().getPreviewRendererClass();
        try {
            Constructor<? extends TransitionPreviewRenderer> constructor =
                    clazz.getConstructor(transition.getClass());
            return constructor.newInstance(transition);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}