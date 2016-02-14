package com.gdx.wallpaper.wallpaper.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.gdx.wallpaper.image.ImageManager;
import com.gdx.wallpaper.transition.Transition;

import aurelienribon.tweenengine.TweenManager;

public class CubeEnvironmentRenderer extends Scene3DEnvironmentRenderer {

    Environment env;
    Model model;
    ModelInstance modelInstance;
    ModelBatch modelBatch;

    public CubeEnvironmentRenderer(ImageManager imageManager, TweenManager tweenManager,
                                   Transition transition, Batch batch) {
        super(imageManager, tweenManager, transition, batch);

        modelBatch = new ModelBatch();

        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        ModelBuilder builder = new ModelBuilder();
        int attr =
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal |
                        VertexAttributes.Usage.TextureCoordinates;
        builder.begin();
        builder.part("top", GL20.GL_TRIANGLES, attr, new Material())
                .rect(-1f, -1f, -1f,
                      -1f, 1f, -1f,
                      1f, 1f, -1f,
                      1f, -1f, -1f,
                      0, 0, -1);
        builder.part("bottom", GL20.GL_TRIANGLES, attr, new Material())
                .rect(-1f, 1f, 1f,
                      -1f, -1f, 1f,
                      1f, -1f, 1f,
                      1f, 1f, 1f,
                      0, 0, 1);
        builder.part("left", GL20.GL_TRIANGLES, attr,
                     new Material())
                .rect(1f, -1f, 1f,
                      -1f, -1f, 1f,
                      -1f, -1f, -1f,
                      1f, -1f, -1f,
                      0, -1, 0);
        builder.part("front", GL20.GL_TRIANGLES, attr,
                     new Material())
                .rect(-1f, 1f, 1f,
                      1f, 1f, 1f,
                      1f, 1f, -1f,
                      -1f, 1f, -1f,
                      0, 1, 0);
        builder.part("back", GL20.GL_TRIANGLES, attr,
                     new Material())
                .rect(-1f, -1f, 1f,
                      -1f, 1f, 1f,
                      -1f, 1f, -1f,
                      -1f, -1f, -1f,
                      -1, 0, 0);
        builder.part("right", GL20.GL_TRIANGLES, attr,
                     new Material())
                .rect(1f, 1f, 1f,
                      1f, -1f, 1f,
                      1f, -1f, -1f,
                      1f, 1f, -1f,
                      1, 0, 0);
        model = builder.end();
        modelInstance = new ModelInstance(model);
    }

    @Override
    public void render(float delta) {
        modelInstance.transform.rotate(Vector3.Z, -50 * Gdx.graphics.getDeltaTime());
        modelBatch.begin(viewport.getCamera());
        modelBatch.render(modelInstance, env);
        modelBatch.end();
    }
}
