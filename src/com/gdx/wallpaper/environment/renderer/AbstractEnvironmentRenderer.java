package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.renderer.AbstractTransitionRenderer;
import com.gdx.wallpaper.transition.renderer.TransitionRendererManager;
import com.gdx.wallpaper.wallpaper.WallpaperHomeInfo;

public abstract class AbstractEnvironmentRenderer<T extends Environment> {

    protected final TransitionRendererManager manager;
    protected Camera camera;
    protected final Viewport viewport;
    protected final T environment;

    private ModelBatch modelBatch;

    private int currentScreen = -1;
    protected AbstractTransitionRenderer from;
    protected AbstractTransitionRenderer to;

    public AbstractEnvironmentRenderer(T environment, Camera camera, ImageManager imageManager,
                                       Transition transition,
                                       Batch batch, Skin skin) {
        this.environment = environment;

        this.camera = camera;
        viewport = new ScreenViewport(camera);

        modelBatch = new ModelBatch();

        manager =
                new TransitionRendererManager(environment, imageManager, transition,
                                              batch, skin, camera);
        for (int i = 0; i < environment.getScreenCount(); i++) {
            manager.createInstance();
        }
    }

    public final void internalRender(float delta) {
        manager.update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        render(modelBatch, delta);
        modelBatch.end();

        manager.postRender();
    }

    protected abstract void render(ModelBatch batch, float delta);

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        manager.resize(width, height);
    }

    public void offsetChange(WallpaperHomeInfo homeInfo) {
        int screenCount = environment.getScreenCount() - 1;
        float totalProgress = homeInfo.getPercentOffsets().x;
        float progress = (totalProgress * screenCount) % 1;

        int fromScreen = MathUtils.floor(totalProgress * screenCount);
        int toScreen = fromScreen + 1;

        if (currentScreen != fromScreen) {
            if (from != null) {
                from.setRendering(false);
            }
            from = manager.getInstance(fromScreen);
            from.setRendering(true);
            if (toScreen <= screenCount) {
                if (to != null) {
                    to.setRendering(false);
                }
                to = manager.getInstance(toScreen);
                to.setRendering(progress != 1f);
            }
            currentScreen = fromScreen;
        }

        progressChange(currentScreen, progress);
    }

    protected abstract void progressChange(int currentScreen, float progress);

    public void dispose() {
        manager.dispose();
    }
}