package com.gdx.wallpaper.environment.model;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class CubeModelInstance extends ModelInstance {

    public CubeModelInstance() {
        super(new CubeModel().model);
    }

    private void setTexture(String materialId, Texture texture) {
        if (texture != null) {
            TextureRegion region = new TextureRegion(texture);
            region.flip(false, true);
            getMaterial(materialId).set(TextureAttribute.createDiffuse(region));
        } else {
            getMaterial(materialId).remove(TextureAttribute.Diffuse);
        }
    }

    public void setTextureFront(Texture texture) {
        setTexture("front", texture);
    }

    public void setTextureRight(Texture texture) {
        setTexture("right", texture);
    }

    public void setTextureBack(Texture texture) {
        setTexture("back", texture);
    }

    public void setTextureLeft(Texture texture) {
        setTexture("left", texture);
    }

    static class CubeModel {

        private Model model;

        public CubeModel() {
            ModelBuilder builder = new ModelBuilder();

            int attr =
                    VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal |
                            VertexAttributes.Usage.TextureCoordinates;
            builder.begin();
            builder.part("back", GL20.GL_TRIANGLES, attr,
                         new Material("back"))
                    .rect(1f, 0f, 0f,
                          0f, 0f, 0f,
                          0f, 1f, 0f,
                          1f, 1f, 0f,
                          0f, 0f, -1f);
            builder.part("front", GL20.GL_TRIANGLES, attr,
                         new Material("front"))
                    .rect(
                            0f, 0f, 1f,
                            1f, 0f, 1f,
                            1f, 1f, 1f,
                            0f, 1f, 1f,
                            0f, 0f, 1f);
            builder.part("bottom", GL20.GL_TRIANGLES, attr,
                         new Material("bottom"))
                    .rect(0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 1f, 0f, -1f, 0f);
            builder.part("top", GL20.GL_TRIANGLES, attr,
                         new Material("top"))
                    .rect(0f, 1f, 0f, 0f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, 1f, 0f);
            builder.part("left", GL20.GL_TRIANGLES, attr,
                         new Material("left"))
                    .rect(0f, 0f, 0f,
                          0f, 0f, 1f,
                          0f, 1f, 1f,
                          0f, 1f, 0f,
                          -1f, 0f, 0f);
            builder.part("right", GL20.GL_TRIANGLES, attr,
                         new Material("right"))
                    .rect(1f, 0f, 1f,
                          1f, 0f, 0f,
                          1f, 1f, 0f,
                          1f, 1f, 1f,
                          1f, 0f, 0f);
            model = builder.end();
        }
    }
}