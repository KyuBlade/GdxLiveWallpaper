package com.gdx.wallpaper.wallpaper.environment.component;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

public class SurfaceHorizontalGroup extends Group {

    public SurfaceHorizontalGroup() {

    }

    @Override
    protected void childrenChanged() {
        SnapshotArray<Actor> children = getChildren();
        float width = 0f;
        float height = 0f;
        for (int i = 0; i < children.size; i++) {
            Actor child = children.get(i);
            child.setX(width);
            height = Math.max(height, child.getHeight());
        }

        setWidth(width);
        setHeight(height);
    }
}