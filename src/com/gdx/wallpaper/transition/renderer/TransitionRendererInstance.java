package com.gdx.wallpaper.transition.renderer;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.image.ManagedImage;
import com.gdx.wallpaper.wallpaper.environment.holder.AbstractSurfaceHolder;
import com.gdx.wallpaper.wallpaper.eventbus.BusProvider;
import com.gdx.wallpaper.wallpaper.eventbus.ImageLoadedEvent;
import com.gdx.wallpaper.wallpaper.eventbus.WallpaperChangeEvent;
import com.squareup.otto.Subscribe;

import javax.microedition.khronos.opengles.GL10;

public class TransitionRendererInstance {

    private final ImageManager imageManager;
    protected final AbstractSurfaceHolder surface;
    private final AbstractTransitionRenderer renderer;

    private final ImageLoadedSubscriber imageLoadedSubscriber;
    private final WallpaperChangeSubscriber wallpaperChangeSubscriber;

    protected boolean transitioning;
    private long currentCycleTime;
    private long elapsedCycleTime;

    private boolean startTransition;
    private boolean cycling;

    protected final Batch batch;
    private FrameBuffer frameBuffer;
    protected ManagedImage currentImage;
    protected ManagedImage nextImage;

    protected TransitionRendererInstance(ImageManager imageManager,
                                         AbstractTransitionRenderer renderer,
                                         AbstractSurfaceHolder surfaceHolder, Batch batch) {
        this.imageManager = imageManager;
        this.renderer = renderer;
        this.surface = surfaceHolder;
        this.batch = batch;

        renderer.instance = this;

        newFrameBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
    protected void free(ManagedImage image) {
        if (image == null) {
            return;
        }

        imageManager.free(image);
    }

    /**
     * Called when a texture loading finished.
     */
    protected void onLoadingDone() {
        Log.i("ImageLoader", "Buffering done for " + this.hashCode() + " (" +
                nextImage.getAssetDescriptor().fileName + ")");
        if (startTransition) {
            startTransition = false;
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
            currentCycleTime = 0L;

            if (renderer.transition.isDisplayCyclingProgress()) {
                elapsedCycleTime = 0L;
                surface.updateProgress(0f);
            }

            transitioning = true;
            renderer.onTransitionBegin();
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
        transitioning = false;

        free(currentImage);
        currentImage = nextImage;
        nextImage = null;
        renderer.onTransitionFinish();
        cycling = true;
    }

    public void render(float delta) {
        int cycleTime = renderer.transition.getCycleTime().getCurrent();
        if (cycling) { // Cycling
            if (renderer.transition.isDisplayCyclingProgress()) {
                elapsedCycleTime += delta * 1000L;
                surface.updateProgress(
                        (float) elapsedCycleTime /
                                renderer.transition.getCycleTime().getCurrent());
            }

            if ((currentCycleTime += delta * 1000L) >= cycleTime) {
                endCycling();
            }
        } else {
            renderSurface();
        }
    }

    private void renderSurface() {
        // Create FBO and set it to the surface
        frameBuffer.begin();
        batch.begin();
        batch.setBlendFunction(-1, -1);
        Gdx.gl20.glBlendFuncSeparate(GL10.GL_SRC_ALPHA,
                                     GL10.GL_ONE_MINUS_SRC_ALPHA, GL10.GL_ONE, GL10.GL_ONE);
        if (nextImage != null && nextImage.isLoaded()) {
            batch.setColor(nextImage.getColor());
            nextImage.draw(batch);
        }
        if (currentImage != null && currentImage.isLoaded()) {
            batch.setColor(currentImage.getColor());
            currentImage.draw(batch);
        }

        batch.end();
        frameBuffer.end();
        batch.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        surface.updateTexture(frameBuffer.getColorBufferTexture());
    }

    protected void resize(int width, int height) {
        Log.i("Screen", "Size : " + Gdx.graphics.getWidth() + " / " + Gdx.graphics.getHeight());
//        surface.resize(width, height);
//        newFrameBuffer(width, height);
//        renderSurface();
    }

    public AbstractSurfaceHolder getSurface() {
        return surface;
    }

    public void dispose() {
        free(currentImage);
        free(nextImage);

        surface.remove();

        BusProvider.getInstance().unregister(imageLoadedSubscriber);
        BusProvider.getInstance().unregister(wallpaperChangeSubscriber);
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