package com.gdx.wallpaper.wallpaper.render;

import android.content.Context;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.environment.renderer.AbstractEnvironmentRenderer;
import com.gdx.wallpaper.environment.renderer.CubeEnvironmentRenderer;
import com.gdx.wallpaper.environment.type.CubeEnvironment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;
import com.gdx.wallpaper.wallpaper.WallpaperGestureDetector;
import com.gdx.wallpaper.wallpaper.WallpaperHomeInfo;

import java.lang.reflect.Constructor;

public class WallpaperRenderer implements Renderer {

    private final ImageManager imageManager;
    private final AssetManager assetManager;

    private final Viewport uiViewport;
    private final Stage uiStage;

    private AbstractEnvironmentRenderer envRenderer;

    private final Cell<Label> errorMessageCell;

    private Label messageLabel;

    private final SpriteBatch batch;
    private final WallpaperHomeInfo homeInfo;

    private boolean error;

    private ShapeRenderer debugRenderer;

    private float accumulator;

    public WallpaperRenderer(Playlist playlist) {
        assetManager = new AssetManager();
        uiViewport = new ScreenViewport();
        batch = new SpriteBatch(100);

        uiStage = new Stage(uiViewport, batch);

        imageManager = new ImageManager();
        imageManager.setPlaylist(playlist);

        debugRenderer = new ShapeRenderer();

        homeInfo = new WallpaperHomeInfo();

        AssetDescriptor<Skin> skinAssetDescriptor = new AssetDescriptor<>("skin/default.json",
                                                                          Skin.class);
        assetManager.load(skinAssetDescriptor);
        assetManager.finishLoading();

        Skin skin = assetManager.get(skinAssetDescriptor);

        Table layout = new Table(skin);
        layout.setFillParent(true);
        layout.top().left();
        errorMessageCell = layout.add().fillX().expand().center();
        uiStage.addActor(layout);
//        uiStage.setDebugAll(true);

        int errorStrRes = -1;
        if (playlist == null) {
            errorStrRes = R.string.playlist_wallpaper_unset;
        } else {
            Collection collection = CollectionManager.getInstance().get(playlist.getCollectionId());
            if (collection == null) {
                errorStrRes = R.string.collection_wallpaper_unset;
            } else if (collection.size() == 0) {
                errorStrRes = R.string.collection_wallpaper_empty;
            } else {
                Environment
                        environment =
                        EnvironmentManager.getInstance().get(playlist.getEnvironmentId());
                if (environment == null) {
                    errorStrRes = R.string.environment_wallpaper_empty;
                }
            }
        }

        if (errorStrRes != -1) {
            error = true;
            Context context = ((AndroidLiveWallpaper) Gdx.app).getContext();
            messageLabel = new Label(context.getString(errorStrRes), skin);
            messageLabel.setWrap(true);
            messageLabel.setFontScale(0.25f * Gdx.graphics.getDensity());
            messageLabel.setAlignment(Align.center);
            errorMessageCell.setActor(messageLabel);
        } else {
            Environment environment = EnvironmentManager.getInstance().get(
                    playlist.getEnvironmentId());
            Transition transition = TransitionManager.getInstance().get(playlist.getTransitionId());
            Class<? extends AbstractEnvironmentRenderer> envClazz =
                    environment.getType().getRendererClass();
            try {
                Constructor<? extends AbstractEnvironmentRenderer>
                        constructor =
                        envClazz.getConstructor(environment.getClass(), ImageManager.class,
                                                Transition.class, Batch.class, Skin.class);
//                envRenderer = constructor
//                        .newInstance(environment, imageManager, transition, batch, skin);
                envRenderer =
                        new CubeEnvironmentRenderer(new CubeEnvironment(20, "", 5), imageManager,
                                                    transition, batch,
                                                    skin);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            Gdx.input.setInputProcessor(new WallpaperGestureDetector());
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void update(float delta) {
        imageManager.update();

        if (envRenderer != null) {
            envRenderer.internalRender(delta);
        }

        uiStage.getViewport().apply();
        uiStage.act(delta);
        uiStage.draw();

        accumulator += delta;
        if (accumulator >= 1f) {
            accumulator = 0f;
            Log.i("RenderFrame", "FPS : " + Gdx.graphics.getFramesPerSecond());
        }
    }

    @Override
    public void resize(int width, int height) {
        Log.i("Resize", "Size : " + width + "x" + height);
        uiViewport.update(width, height, true);

        homeInfo.setScreenSize(width, height);
        if (envRenderer != null) {
            envRenderer.resize(width, height);
        }
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        if (envRenderer != null) {
            envRenderer.dispose();
        }
        if (imageManager != null) {
            imageManager.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
        if (assetManager != null) {
            assetManager.dispose();
        }
        if (uiStage != null) {
            uiStage.dispose();
        }
        debugRenderer.dispose();
    }

    @Override
    public void offsetChange(float xOffset, float yOffset,
                             float xOffsetStep, float yOffsetStep,
                             int xPixelOffset, int yPixelOffset) {
        if (error) {
            return;
        }

        if (homeInfo != null) {
            homeInfo.setPercentOffsets(xOffset, yOffset);
            homeInfo.setPixelOffsets(xPixelOffset, yPixelOffset);
            homeInfo.setStepOffsets(xOffsetStep, yOffsetStep);
        }

        if (envRenderer != null) {
            envRenderer.offsetChange(homeInfo);
        }
    }
}
