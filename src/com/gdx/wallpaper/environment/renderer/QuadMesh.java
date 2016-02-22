package com.gdx.wallpaper.environment.renderer;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class QuadMesh extends Mesh {

    private float x;
    private float y;
    private float width;
    private float height;
    private final float[] vertices;

    public QuadMesh(float x, float y, float width, float height) {
        super(true, 4, 6, VertexAttribute.Position(), VertexAttribute.ColorUnpacked());

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vertices = new float[] {
                x, y + height, 0f,
                1f, 0f, 0f, 1f,// top left

                x + width, y + height, 0f,
                0f, 1f, 0f, 1f,// top right

                x + width, y, 0f,
                0f, 0f, 1f, 1f,// bottom right

                x, y, 0f,
                1f, 1f, 1f, 1f// bottom left
        };

        setVertices(vertices);
        setIndices(new short[] { 0, 1, 2, 2, 3, 0 });
    }

    public void draw(ShaderProgram shader) {
        render(shader, GL20.GL_TRIANGLES);
    }
}