package com.gdx.wallpaper.wallpaper.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class QuadMesh extends Mesh {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float[] vertices;

    public QuadMesh(float x, float y, float width, float height, VertexAttribute... attributes) {
        super(true, 4, 6, attributes);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        vertices = getVertices();
        setVertices(vertices);
        setIndices(new short[] { 0, 1, 2, 2, 3, 0 });
    }

    protected abstract float[] getVertices();

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;

        setVertices(getVertices());
    }

    public void draw(ShaderProgram shader) {
        render(shader, GL20.GL_TRIANGLES);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[').append("x=").append(x).append(", y=").append(y).append(", width=")
                .append(width).append(", height=").append(height).append(
                ']');
        return builder.toString();
    }
}