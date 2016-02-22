package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import aurelienribon.tweenengine.TweenManager;

public class Scene3DEnvironmentRenderer extends EnvironmentRenderer {

    protected com.badlogic.gdx.graphics.g3d.Environment env;
    protected ModelBatch modelBatch;

    public Scene3DEnvironmentRenderer(Environment environment, ImageManager imageManager,
                                      TweenManager tweenManager,
                                      Transition transition, Batch batch) {
        super(environment, imageManager, tweenManager, transition, batch,
              new ScreenViewport(new OrthographicCamera()));

        Camera camera = viewport.getCamera();
        camera.translate(0f, 5f, 0f);
        camera.lookAt(Vector3.Zero);
        camera.update();

        modelBatch = new ModelBatch();
        env = new com.badlogic.gdx.graphics.g3d.Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(1, 1);
    }

    @Override
    public void dispose() {
        super.dispose();

        modelBatch.dispose();
    }
}