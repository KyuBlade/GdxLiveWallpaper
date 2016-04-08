package com.gdx.wallpaper.transition.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Apply transition to surfaces of this manager in order.
 */
public class TransitionRendererManager {

    protected final Environment environment;
    private final ImageManager imageManager;
    private final Batch batch;
    private final Skin skin;
    private final Camera camera;
    private final Transition transition;

    private Array<AbstractTransitionRenderer> instances;

    public TransitionRendererManager(Environment environment, ImageManager imageManager,
                                     Transition transition, Batch batch, Skin skin, Camera camera) {
        this.environment = environment;
        this.imageManager = imageManager;
        this.transition = transition;
        this.batch = batch;
        this.skin = skin;
        this.camera = camera;

        instances = new Array<>();
    }

    public void update(float delta) {
        for (AbstractTransitionRenderer instance : instances) {
            instance.render(delta);
        }
    }

    public void postRender() {
        batch.setColor(Color.WHITE);
        batch.begin();
        float size = Gdx.graphics.getWidth() / 5;
        for (int i = 0; i < instances.size; i++) {
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
            batch.draw(instances.get(i).getTexture(), i * size, Gdx.graphics.getHeight() / 5, size,
                       size);
        }
        batch.end();
    }

    public void resize(int width, int height) {
        for (AbstractTransitionRenderer instance : instances) {
            instance.resize(width, height);
        }
    }

    public void dispose() {
        for (AbstractTransitionRenderer instance : instances) {
            instance.dispose();
        }
    }

    /**
     * Add an instance to be managed by this manager.
     */
    public void createInstance() {
        instances
                .add(newTransitionRenderer());
    }

    public AbstractTransitionRenderer getInstance(int index) {
        return instances.get(index);
    }

    private AbstractTransitionRenderer newTransitionRenderer() {
        try {
            Constructor constructor = transition.getType().getRendererClass()
                    .getConstructor(ImageManager.class, Batch.class, Skin.class, Camera.class,
                                    transition.getClass());
            return (AbstractTransitionRenderer) constructor
                    .newInstance(imageManager, batch, skin, camera, transition);
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
}