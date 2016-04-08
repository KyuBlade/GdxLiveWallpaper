package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.gdx.wallpaper.wallpaper.render.QuadMesh;

public class EnvironmentQuadMesh extends QuadMesh {

    public EnvironmentQuadMesh(float x, float y, float width, float height) {
        super(x, y, width, height, VertexAttribute.Position(), VertexAttribute.TexCoords(0));
    }

    @Override
    protected float[] getVertices() {
        return new float[] {
                x, y, 0f,
                0f, 0f,

                x + width, y, 0f,
                1f, 0f,

                x + width, y + height, 0f,
                1f, 1f,

                x, y + height, 0f,
                0f, 1f
        };
    }
}