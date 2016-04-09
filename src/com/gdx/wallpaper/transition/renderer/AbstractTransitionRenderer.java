package com.gdx.wallpaper.transition.renderer;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.image.ManagedImage;
import com.gdx.wallpaper.setting.ui.ProgressBarIndicator;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.util.Utils;
import com.gdx.wallpaper.wallpaper.eventbus.BusProvider;
import com.gdx.wallpaper.wallpaper.eventbus.ImageLoadedEvent;
import com.gdx.wallpaper.wallpaper.eventbus.WallpaperChangeEvent;
import com.squareup.otto.Subscribe;

public abstract class AbstractTransitionRenderer<T extends Transition> {

    private final ImageManager imageManager;
    private final Batch batch;
    private final Camera camera;
    protected T transition;

    private final ImageLoadedSubscriber imageLoadedSubscriber;
    private final WallpaperChangeSubscriber wallpaperChangeSubscriber;

    protected boolean transitioning;
    private long currentCycleTime;

    private boolean startTransition;
    private boolean cycling;

    private ShaderProgram shader;
    private FrameBuffer frameBuffer;
    private TransitionQuadMesh quad;
    protected ManagedImage currentImage;
    protected ManagedImage nextImage;
    protected float transitionProgress;
    protected boolean rendering;

    private ProgressBarIndicator indicator;

    protected AbstractTransitionRenderer(ImageManager imageManager, Batch batch, Skin skin,
                                         Camera camera, T transition) {
        this.imageManager = imageManager;
        this.batch = batch;
        this.camera = camera;
        this.transition = transition;

        indicator = new ProgressBarIndicator(skin);
        indicator.setSize(Gdx.graphics.getWidth(), 25f);
        indicator.setPosition(0f, Gdx.graphics.getHeight() - indicator.getHeight() -
                Utils.getStatusBarHeight());
        indicator.setMaxProgress(1f);

        newFrameBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shader =
                new ShaderProgram(Gdx.files.internal("shaders/transitions/transition.vert"),
                                  Gdx.files
                                          .internal("shaders/transitions/" +
                                                            transition.getType().getShader()));
        if (!shader.isCompiled()) {
            Log.i("Shader", shader.getLog());
        }

        shader.begin();
        shader.setUniformi("from", 1);
        shader.setUniformi("to", 2);
        shader.end();

        quad = new TransitionQuadMesh(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        imageLoadedSubscriber = new ImageLoadedSubscriber();
        BusProvider.getInstance().register(imageLoadedSubscriber);

        wallpaperChangeSubscriber = new WallpaperChangeSubscriber();
        BusProvider.getInstance().register(wallpaperChangeSubscriber);

        loadImage(false);
        beginTransition();
    }

    private void newFrameBuffer(int width, int height) {
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA4444, width, height, false);
    }

    /**
     * Load the next or previous image into memory. (Async)
     *
     * @param backward false if we want to load the next image, false if we want to load the previous one.
     */
    protected final void loadImage(boolean backward) {
        nextImage = imageManager.obtain(backward);
    }

    /**
     * Free a texture.
     *
     * @param image texture to remove
     */
    protected final void free(ManagedImage image) {
        if (image == null) {
            return;
        }

        imageManager.free(image);
    }

    /**
     * Called when a texture loading finished.
     */
    protected final void onLoadingDone() {
        quad.setTo(nextImage.getRegion());

        if (startTransition) {
            beginTransition();
        }
    }

    /**
     * Start the transition.
     */
    protected final void beginTransition() {
        if (nextImage != null && !nextImage.isLoaded()) {
            startTransition = true;
        } else {
            Log.i("TransitionRenderer", "Begin transition");
            currentCycleTime = 0L;

            indicator.setProgress(0L);

            startTransition = false;
            transitioning = true;

            onTransitionBegin();
        }
    }

    /**
     * End cycling state and load next image(s) and start transitionRenderer when loaded.
     */
    protected final void endCycling() {
        endCycling(false);
    }

    /**
     * End cycling state and load next image(s) and start transitionRenderer when loaded.
     *
     * @param backward order in which to load the next image
     */
    protected final void endCycling(boolean backward) {
        cycling = false;

        loadImage(backward);
        beginTransition();
    }

    /**
     * End the transition.
     */
    protected final void endTransition() {
        Log.i("TransitionRenderer", "Finish transition");
        transitioning = false;

        free(currentImage);
        currentImage = nextImage;
        nextImage = null;

        quad.setFrom(currentImage.getRegion());

        onTransitionFinish();
        cycling = true;
    }

    public final void render(float delta) {
        long pauseDuration = transition.getPauseDuration();
        if (cycling) { // Cycling
            currentCycleTime += delta * 1000L;
            if (transition.isDisplayCyclingProgress()) {
                indicator.setProgress((float) currentCycleTime /
                                              transition.getPauseDuration());
            }
            if (currentCycleTime >= pauseDuration) {
                endCycling();
            }
        }

        update(delta);

        if (rendering) {
            renderSurface();
        }
    }

    protected abstract void update(float delta);

    private void renderSurface() {
        // Create FBO and set it to the surface
        frameBuffer.begin();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (currentImage != null) {
            currentImage.bind(1);
        }
        if (nextImage != null) {
            nextImage.bind(2);
        }

        shader.begin();
        transition.validate(shader);
        shader.setUniformMatrix("u_projTrans", camera.combined);
        shader.setUniformf("progress", transitionProgress);
        quad.draw(shader);
        shader.end();

        batch.begin();
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        indicator.draw(batch, 1f);

        batch.end();
        frameBuffer.end();
    }

    protected void resize(int width, int height) {
//        quad.setSize(width, height); // Fix flipping
        indicator.setSize(width, 25f);
        indicator.setPosition(0f, height - indicator.getHeight() -
                Utils.getStatusBarHeight());
        newFrameBuffer(width, height);
    }

    public void dispose() {
        free(currentImage);
        free(nextImage);

        shader.dispose();
        quad.dispose();

        BusProvider.getInstance().unregister(imageLoadedSubscriber);
        BusProvider.getInstance().unregister(wallpaperChangeSubscriber);
    }

    protected abstract void onTransitionBegin();

    protected abstract void onTransitionFinish();

    public Texture getTexture() {
        return frameBuffer.getColorBufferTexture();
    }

    public void setRendering(boolean rendering) {
        this.rendering = rendering;
    }

    /**
     * Called when an texture is loaded.
     */
    class ImageLoadedSubscriber {

        @Subscribe
        public void onImageLoaded(ImageLoadedEvent event) {
            ManagedImage image = event.getImage();
            if (image.equals(nextImage)) {
                onLoadingDone();
            }
        }
    }

    class WallpaperChangeSubscriber {

        @Subscribe
        public void onWallpaperChange(WallpaperChangeEvent event) {

            Log.i("Change", "Force change");

            boolean backward = event.isBackward();
            if (cycling) {
                endCycling(backward);
            }
        }
    }
}