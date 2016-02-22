package com.gdx.wallpaper.environment.renderer;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.wallpaper.WallpaperHomeInfo;

import aurelienribon.tweenengine.TweenManager;

public class EnvironmentRenderer {

    //    protected final TransitionRendererManager manager;
    private OrthographicCamera camera;
    protected final Viewport viewport;
    protected final Environment environment;

    protected ShaderProgram shader;
    private QuadMesh quad;

    public EnvironmentRenderer(Environment environment, ImageManager imageManager,
                               TweenManager tweenManager, Transition transition, Batch batch,
                               String shader) {
        this.environment = environment;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        this.viewport = new ScreenViewport(camera);

        this.shader =
                new ShaderProgram(Gdx.files.internal("shaders/environment.vertex.glsl"),
                                  Gdx.files.internal("shaders/" + shader));
        Log.i("Shader", this.shader.isCompiled() ? "Compiled sucessfully" : this.shader.getLog());

        quad = new QuadMesh(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        manager =
//                new TransitionRendererManager(environment, imageManager, tweenManager, transition,
//                                              batch);
    }

    public void render(float delta) {
//        manager.update(delta);
        shader.begin();
        shader.setUniformMatrix("u_projTrans", viewport.getCamera().combined);
        quad.draw(shader);
        shader.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
//        manager.resize(width, height);
    }

    public void offsetChange(WallpaperHomeInfo homeInfo) {
    }

    public void dispose() {
    }
}