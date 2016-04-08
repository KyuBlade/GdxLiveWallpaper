package com.gdx.wallpaper.transition.fragment.model;

import com.gdx.wallpaper.transition.fragment.shader.TransitionShaderPreviewApplication;
import com.gdx.wallpaper.transition.type.CrossFadeTransition;

public class CrossFadeTransitionModel extends TransitionModel<CrossFadeTransition> {

    public CrossFadeTransitionModel(
            TransitionShaderPreviewApplication app, CrossFadeTransition transition) {
        super(app, transition);
    }
}