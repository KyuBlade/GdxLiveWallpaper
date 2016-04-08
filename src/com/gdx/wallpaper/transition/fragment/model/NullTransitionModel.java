package com.gdx.wallpaper.transition.fragment.model;

import com.gdx.wallpaper.transition.fragment.shader.TransitionShaderPreviewApplication;
import com.gdx.wallpaper.transition.type.NullTransition;

public class NullTransitionModel extends TransitionModel<NullTransition> {

    public NullTransitionModel(
            TransitionShaderPreviewApplication app, NullTransition transition) {
        super(app, transition);
    }
}