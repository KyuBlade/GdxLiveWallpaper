package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import aurelienribon.tweenengine.TweenManager;

public class SlideEnvironmentRenderer extends EnvironmentRenderer {

    private float progress;
    private Texture wallpaper1;
    private Texture wallpaper2;

    public SlideEnvironmentRenderer(Environment environment,
                                    ImageManager imageManager,
                                    TweenManager tweenManager,
                                    Transition transition,
                                    Batch batch) {
        super(environment, imageManager, tweenManager, transition, batch, "slide.glsl");

        wallpaper1 = new Texture("wallpaper1.jpg");
        wallpaper2 = new Texture("wallpaper2.jpg");

        wallpaper1.bind(0);
        wallpaper2.bind(1);
        shader.begin();
        shader.setUniformi("from", 0);
        shader.setUniformi("to", 1);
        shader.setUniformf("translateX", -1f);
//        shader.setUniformf("persp", 0.7f);
//        shader.setUniformf("unzoom", 0.3f);
//        shader.setUniformf("reflection", 0.4f);
//        shader.setUniformf("floating", 3f);
        shader.end();
    }

    @Override
    public void render(float delta) {
        if (progress >= 2f) {
            progress = 0f;
        }

        shader.begin();
        progress += delta / 2;
        if (progress <= 1f) {
            shader.setUniformf("progress", progress);
        }

        super.render(delta);
        shader.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        shader.begin();
        shader.setUniformf("resolution", new Vector2(width, height));
        shader.end();
    }
}
