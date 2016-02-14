package com.gdx.wallpaper.transition.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.wallpaper.environment.holder.AbstractSurfaceHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import aurelienribon.tweenengine.TweenManager;

/**
 * Apply transition to surfaces of this manager in order.
 *
 * @param <T> Type of the surface holders
 */
public class TransitionRendererManager<T extends AbstractSurfaceHolder> {

    private final ImageManager imageManager;
    protected final TweenManager tweenManager;
    private final Batch batch;
    private final Transition transition;

    private Array<TransitionRendererInstance> instances;

    public TransitionRendererManager(ImageManager imageManager,
                                     TweenManager tweenManager,
                                     Transition transition, Batch batch) {
        this.imageManager = imageManager;
        this.tweenManager = tweenManager;
        this.batch = batch;
        this.transition = transition;

        instances = new Array<>();
    }

    public void update(float delta) {
        for (TransitionRendererInstance instance : instances) {
            instance.render(delta);
        }
    }

    public void resize(int width, int height) {
        for (TransitionRendererInstance instance : instances) {
            instance.resize(width, height);
        }
    }

    /**
     * Add a surface to be managed by this manager.
     *
     * @param surfaceHolder the surface to manage
     */
    public void registerSurface(T surfaceHolder) {

        instances
                .add(new TransitionRendererInstance(imageManager, newTransitionRenderer(),
                                                    surfaceHolder,
                                                    batch));
    }

    private AbstractTransitionRenderer newTransitionRenderer() {
        try {
            Constructor constructor = transition.getType().getRendererClass()
                    .getConstructor(TransitionRendererManager.class, transition.getClass());
            return (AbstractTransitionRenderer) constructor
                    .newInstance(this, transition);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Remove a surface from this manager.
     *
     * @param surfaceHolder surface to remove
     */
    public void unregisterSurface(T surfaceHolder) {
        for (TransitionRendererInstance instance : instances) {
            if (instance.surface.equals(surfaceHolder)) {
                instances.removeValue(instance, false);
                instance.dispose();
            }
        }
    }

    public Array<TransitionRendererInstance> getInstances() {
        return instances;
    }
}