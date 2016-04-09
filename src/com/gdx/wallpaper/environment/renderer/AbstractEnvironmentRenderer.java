package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
    private OrthographicCamera camera;
    protected final Viewport viewport;
    protected final T environment;

    protected ShaderProgram shader;
    private EnvironmentQuadMesh quad;

    private float progress;
    private int currentScreen = -1;
    private AbstractTransitionRenderer from;
    private AbstractTransitionRenderer to;

    public AbstractEnvironmentRenderer(T environment, ImageManager imageManager,
                                       Transition transition,
                                       Batch batch, Skin skin) {
        this.environment = environment;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new ScreenViewport(camera);

        this.shader =
                new ShaderProgram(Gdx.files.internal("shaders/environments/environment.vert"),
                                  Gdx.files.internal(
                                          "shaders/environments/" +
                                                  environment.getType().getShader()));
        if (!shader.isCompiled()) {
            throw new IllegalStateException(shader.getLog());
        }

        shader.begin();
        shader.setUniformi("from", 1);
        shader.setUniformi("to", 2);
        shader.end();

        quad = new EnvironmentQuadMesh(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        manager =
                new TransitionRendererManager(environment, imageManager, transition,
                                              batch, skin, camera);
        for (int i = 0; i < environment.getScreenCount(); i++) {
            manager.createInstance();
        }
    }

    public void render(float delta) {
        manager.update(delta);

        if (from != null) {
            from.getTexture().bind(1);
        }
        if (to != null) {
            to.getTexture().bind(2);
        }

        shader.begin();
        environment.validate(shader);
        shader.setUniformMatrix("u_projTrans", viewport.getCamera().combined);
        quad.draw(shader);
        shader.end();

        manager.postRender();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        quad.setSize(width, height);
        manager.resize(width, height);
    }

    public void offsetChange(WallpaperHomeInfo homeInfo) {
        int screenCount = environment.getScreenCount() - 1;
        float totalProgress = homeInfo.getPercentOffsets().x;
        progress = (totalProgress * screenCount) % 1;

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

        shader.begin();
        shader.setUniformf("progress", progress);
        shader.end();
    }

    public void dispose() {
        quad.dispose();
        shader.dispose();
        manager.dispose();
    }
}