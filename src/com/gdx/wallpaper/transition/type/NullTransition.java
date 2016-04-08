package com.gdx.wallpaper.transition.type;

import android.content.ContentValues;
import android.database.Cursor;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionType;

public class NullTransition extends Transition {

    public NullTransition() {
        super(TransitionType.NONE);

        transitionDuration = 0L;
    }

    @Override
    public void build(Cursor cursor) {

    }

    @Override
    protected void provideInsert(ContentValues values) {

    }

    @Override
    protected void updateShader(ShaderProgram shader) {

    }

    @Override
    protected void toString(StringBuilder builder) {

    }
}