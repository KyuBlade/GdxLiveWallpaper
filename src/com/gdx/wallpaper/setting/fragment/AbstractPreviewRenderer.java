package com.gdx.wallpaper.setting.fragment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.wallpaper.setting.ShaderValidatable;
import com.gdx.wallpaper.wallpaper.render.QuadMesh;

public abstract class AbstractPreviewRenderer {

    protected QuadMesh mesh;
    private OrthographicCamera camera;
    private Viewport viewport;
    protected final ShaderProgram shader;
    protected Texture textureA;
    protected Texture textureB;
    private float progress;
    private ShaderValidatable validatable;

    public AbstractPreviewRenderer(ShaderValidatable validatable, QuadMesh mesh, String vertShader,
                                   String fragShader) {
        this.validatable = validatable;
        this.mesh = mesh;
        shader =
                new ShaderProgram(Gdx.files.internal(vertShader), Gdx.files.internal(fragShader));
        if (!shader.isCompiled()) {
            throw new IllegalStateException(shader.getLog());
        }
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new ScreenViewport(camera);
        validatable.invalidate();
    }

    protected final void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (textureA == null || textureB == null) {
            return;
        }
        textureA.bind(0);
        textureB.bind(1);
        shader.begin();
        validatable.validate(shader);
        shader.setUniformi("from", 0);
        shader.setUniformi("to", 1);
        shader.setUniformf("progress", progress);
        shader.setUniformMatrix("u_projTrans", viewport.getCamera().combined);
        mesh.draw(shader);
        shader.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        mesh.dispose();
        shader.dispose();
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public void swapTextures() {
        Texture oldTexA = textureA;
        textureA = textureB;
        textureB = oldTexA;
    }
}