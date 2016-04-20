package com.gdx.wallpaper.environment.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class QuadModelInstance extends ModelInstance {

    public QuadModelInstance() {
        super(new QuadModel().model);
    }

    public void setTexture(Texture texture) {
        TextureRegion region = new TextureRegion(texture);
        region.flip(false, true);
        materials.first().set(TextureAttribute.createDiffuse(region));
    }

    static class QuadModel {

        private Model model;

        private QuadModel() {
            ModelBuilder builder = new ModelBuilder();
            builder.begin();

            MeshPartBuilder partBuilder = builder.part("quad", GL20.GL_TRIANGLES,
                                                       VertexAttributes.Usage.Position |
                                                               VertexAttributes.Usage.Normal |
                                                               VertexAttributes.Usage.ColorUnpacked |
                                                               VertexAttributes.Usage.TextureCoordinates,
                                                       new Material(
                                                               ColorAttribute
                                                                       .createDiffuse(
                                                                               Color.WHITE)));
            partBuilder.rect(0f, 0f, 0f,
                             1f, 0f, 0f,
                             1f, 1f, 0f,
                             0f, 1f, 0,
                             0f, 1f, 0f);
            model = builder.end();
        }
    }
}