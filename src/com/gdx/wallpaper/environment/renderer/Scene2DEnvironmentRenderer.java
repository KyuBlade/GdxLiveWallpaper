package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import aurelienribon.tweenengine.TweenManager;

public class Scene2DEnvironmentRenderer extends EnvironmentRenderer {

    protected final Stage stage;
    protected final Skin skin;

    public Scene2DEnvironmentRenderer(Environment environment, ImageManager imageManager,
                                      TweenManager tweenManager, Transition transition, Skin skin,
                                      Batch batch) {
        super(environment, imageManager, tweenManager, transition, batch, new ScreenViewport());

        this.skin = skin;
        stage = new Stage(viewport, batch);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();

        stage.dispose();
    }
}