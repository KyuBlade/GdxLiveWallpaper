package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.gdx.wallpaper.environment.holder.AbstractSurfaceHolder;
import com.gdx.wallpaper.environment.holder.MaterialSurfaceHolder;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import aurelienribon.tweenengine.TweenManager;

public class CubeEnvironmentRenderer extends Scene3DEnvironmentRenderer {

    private Model model;
    private ModelInstance modelInstance;

    public CubeEnvironmentRenderer(com.gdx.wallpaper.environment.Environment environment,
                                   ImageManager imageManager, TweenManager tweenManager,
                                   Transition transition, Batch batch) {
        super(environment, imageManager, tweenManager, transition, batch);

        ModelBuilder builder = new ModelBuilder();
        int attr =
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal |
                        VertexAttributes.Usage.TextureCoordinates;
        builder.begin();
        builder.part("top", GL20.GL_TRIANGLES, attr,
                     new Material("top"))
                .rect(-1f, -1f, -1f,
                      -1f, 1f, -1f,
                      1f, 1f, -1f,
                      1f, -1f, -1f,
                      0, 0, -1);
        builder.part("bottom", GL20.GL_TRIANGLES, attr, new Material("bottom"))
                .rect(-1f, 1f, 1f,
                      -1f, -1f, 1f,
                      1f, -1f, 1f,
                      1f, 1f, 1f,
                      0, 0, 1);
        builder.part("left", GL20.GL_TRIANGLES, attr,
                     new Material("left"))
                .rect(1f, -1f, 1f,
                      -1f, -1f, 1f,
                      -1f, -1f, -1f,
                      1f, -1f, -1f,
                      0, -1, 0);
        builder.part("front", GL20.GL_TRIANGLES, attr,
                     new Material("front"))
                .rect(-1f, 1f, 1f,
                      1f, 1f, 1f,
                      1f, 1f, -1f,
                      -1f, 1f, -1f,
                      0, 1, 0);
        builder.part("back", GL20.GL_TRIANGLES, attr,
                     new Material("back"))
                .rect(-1f, -1f, 1f,
                      -1f, 1f, 1f,
                      -1f, 1f, -1f,
                      -1f, -1f, -1f,
                      -1, 0, 0);
        builder.part("right", GL20.GL_TRIANGLES, attr,
                     new Material("right"))
                .rect(1f, 1f, 1f,
                      1f, -1f, 1f,
                      1f, -1f, -1f,
                      1f, 1f, -1f,
                      1, 0, 0);
        model = builder.end();
        modelInstance = new ModelInstance(model);
//        modelInstance.transform.rotate(Vector3.Z, -135);

//        createSurface("left");
        createSurface("front");
//        createSurface("right");
//        createSurface("back");
    }

    private void createSurface(String materialId) {
        Material material = modelInstance.getMaterial(materialId);

        AbstractSurfaceHolder surface = new MaterialSurfaceHolder(material);
        manager.registerSurface(surface);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

//        modelInstance.transform.rotate(Vector3.Z, -50 * Gdx.graphics.getDeltaTime());
        modelBatch.begin(viewport.getCamera());
        modelBatch.render(modelInstance, env);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        super.dispose();

        model.dispose();
    }
}
