package com.gdx.wallpaper.setting.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

public abstract class AbstractShaderPreviewApplication implements ApplicationListener {

    private long pauseTime = 1000L;
    private float transitionTime = 1000L;

    private long currentPauseTime;
    private long currentTransitionTime;

    private AssetManager assetManager;
    private AssetDescriptor<Texture> textureADescriptor;
    private AssetDescriptor<Texture> textureBDescriptor;
    private Texture textureA;
    private Texture textureB;

    private ProgressBar radialProgress;

    private AbstractPreviewRenderer renderer;

    enum State {
        PAUSE, TRANSITION
    }

    private State state = State.PAUSE;

    public AbstractShaderPreviewApplication() {
    }

    @Override
    public void create() {
        assetManager = new AssetManager();

        try {
            renderer = initRenderer();
        } catch (Exception e) {
            Log.e("Shader Preview", "Unable to create shader preview renderer", e);
            return;
        }

        TextureLoader.TextureParameter params = new TextureLoader.TextureParameter();
        params.loadedCallback = new AssetLoaderParameters.LoadedCallback() {
            @Override
            public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
                AbstractPreviewRenderer renderer = AbstractShaderPreviewApplication.this.renderer;
                textureA = assetManager.get(textureADescriptor);
                if (renderer != null) {
                    renderer.textureA = textureA;

                }
            }
        };
        textureADescriptor = new AssetDescriptor<>("placeholder_a.jpg", Texture.class, params);

        params = new TextureLoader.TextureParameter();
        params.loadedCallback = new AssetLoaderParameters.LoadedCallback() {
            @Override
            public void finishedLoading(AssetManager assetManager, String fileName, Class type) {
                AbstractPreviewRenderer renderer = AbstractShaderPreviewApplication.this.renderer;
                textureB = assetManager.get(textureBDescriptor);
                if (renderer != null) {
                    renderer.textureB = textureB;
                }
            }
        };
        textureBDescriptor = new AssetDescriptor<>("placeholder_b.jpg", Texture.class, params);
        assetManager.load(textureADescriptor);
        assetManager.load(textureBDescriptor);
    }

    @Override
    public void render() {
        if (!assetManager.update()) {
            if (radialProgress.getVisibility() != View.GONE) {
                radialProgress.post(new Runnable() {
                    @Override
                    public void run() {
                        radialProgress.setVisibility(View.GONE);
                    }
                });
            }
            return;
        }

        if (state.equals(State.PAUSE)) {
            currentPauseTime += Gdx.graphics.getDeltaTime() * 1000L;
            if (currentPauseTime >= pauseTime) {
                state = State.TRANSITION;
                currentPauseTime = 0L;
            }
        }

        if (state.equals(State.TRANSITION)) {
            currentTransitionTime += Gdx.graphics.getDeltaTime() * 1000L;
            float progress = currentTransitionTime / transitionTime;
            if (currentTransitionTime >= transitionTime) {
                currentTransitionTime = 0L;
                state = State.PAUSE;
                progress = 0f;
                renderer.swapTextures();
            }
            renderer.setProgress(progress);
        }

        if (renderer != null) {
            renderer.render(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        renderer.dispose();
    }

    protected abstract AbstractPreviewRenderer initRenderer()
            throws IllegalAccessException, InstantiationException;

    protected void setRadialProgress(ProgressBar radialProgress) {
        this.radialProgress = radialProgress;
    }

    public float getTransitionTime() {
        return transitionTime;
    }

    public void setTransitionTime(long transitionTime) {
        this.transitionTime = transitionTime;
    }

    public float getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(long pauseTime) {
        this.pauseTime = pauseTime;
    }
}