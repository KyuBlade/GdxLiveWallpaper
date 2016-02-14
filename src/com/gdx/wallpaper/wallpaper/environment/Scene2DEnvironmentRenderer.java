package com.gdx.wallpaper.wallpaper.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import aurelienribon.tweenengine.TweenManager;

public class Scene2DEnvironmentRenderer extends EnvironmentRenderer {

    protected final Stage stage;
    protected final Skin skin;

    public Scene2DEnvironmentRenderer(ImageManager imageManager,
                                      TweenManager tweenManager, Transition transition, Skin skin,
                                      Batch batch) {
        super(imageManager, tweenManager, transition, batch);

        this.skin = skin;
        stage = new Stage(viewport, batch);
        stage.getCamera()
                .translate(-Gdx.graphics.getWidth() * 0.5f, -Gdx.graphics.getHeight() * 0.5f, 0f);
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

        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();

        stage.dispose();
    }
}