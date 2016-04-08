package com.gdx.wallpaper.transition.renderer;

import android.util.Log;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.wallpaper.wallpaper.render.QuadMesh;

public class TransitionQuadMesh extends QuadMesh {

    private static final int VERTEX_SIZE = 7;

    public TransitionQuadMesh(float x, float y, float width, float height) {
        super(x, y, width, height, VertexAttribute.Position(), VertexAttribute.TexCoords(0),
              VertexAttribute.TexCoords(1));
    }

    @Override
    protected float[] getVertices() {
        return new float[] {
                x, y, 0f,
                0f, 0f,
                0f, 0f,

                x + width, y, 0f,
                1f, 0f,
                1f, 0f,

                x + width, y + height, 0f,
                1f, 1f,
                1f, 1f,

                x, y + height, 0f,
                0f, 1f,
                0f, 1f
        };
    }

    public void setFrom(TextureRegion fromRegion) {
        if(fromRegion == null) {
            Log.i("TransitionQuadMesh", "Can't update texture region(from): region is null.");
            return;
        }

        int offset = 3;
        vertices[offset] = fromRegion.getU();
        vertices[offset + 1] = fromRegion.getV();
        updateVertices(offset, vertices, offset, 2);

        offset += VERTEX_SIZE;
        vertices[offset] = fromRegion.getU2();
        vertices[offset + 1] = fromRegion.getV();
        updateVertices(offset, vertices, offset, 2);

        offset += VERTEX_SIZE;
        vertices[offset] = fromRegion.getU2();
        vertices[offset + 1] = fromRegion.getV2();
        updateVertices(offset, vertices, offset, 2);

        offset += VERTEX_SIZE;
        vertices[offset] = fromRegion.getU();
        vertices[offset + 1] = fromRegion.getV2();
        updateVertices(offset, vertices, offset, 2);
    }

    public void setTo(TextureRegion toRegion) {
        if(toRegion == null) {
            Log.i("TransitionQuadMesh", "Can't update texture region(to): region is null.");
            return;
        }

        int offset = 5;
        vertices[offset] = toRegion.getU();
        vertices[offset + 1] = toRegion.getV();
        updateVertices(offset, vertices, offset, 2);

        offset += VERTEX_SIZE;
        vertices[offset] = toRegion.getU2();
        vertices[offset + 1] = toRegion.getV();
        updateVertices(offset, vertices, offset, 2);

        offset += VERTEX_SIZE;
        vertices[offset] = toRegion.getU2();
        vertices[offset + 1] = toRegion.getV2();
        updateVertices(offset, vertices, offset, 2);

        offset += VERTEX_SIZE;
        vertices[offset] = toRegion.getU();
        vertices[offset + 1] = toRegion.getV2();
        updateVertices(offset, vertices, offset, 2);
    }
}