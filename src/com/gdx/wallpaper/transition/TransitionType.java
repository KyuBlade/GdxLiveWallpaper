package com.gdx.wallpaper.transition;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.transition.fragment.model.CrossFadeTransitionModel;
import com.gdx.wallpaper.transition.fragment.model.NullTransitionModel;
import com.gdx.wallpaper.transition.fragment.model.TransitionModel;
import com.gdx.wallpaper.transition.renderer.AbstractTransitionRenderer;
import com.gdx.wallpaper.transition.renderer.CrossFadeTransitionRenderer;
import com.gdx.wallpaper.transition.renderer.NullTransitionRenderer;
import com.gdx.wallpaper.transition.renderer.preview.CrossFadeTransitionPreviewRenderer;
import com.gdx.wallpaper.transition.renderer.preview.NullTransitionPreviewRenderer;
import com.gdx.wallpaper.transition.renderer.preview.TransitionPreviewRenderer;
import com.gdx.wallpaper.transition.type.CrossFadeTransition;
import com.gdx.wallpaper.transition.type.NullTransition;

public enum TransitionType {
    NONE(R.string.transition_type_none, NullTransition.class, NullTransitionRenderer.class,
         NullTransitionModel.class,
         NullTransitionPreviewRenderer.class, "null.frag"),
    CROSS_FADE(R.string.transition_type_fade_cross, CrossFadeTransition.class,
               CrossFadeTransitionRenderer.class, CrossFadeTransitionModel.class,
               CrossFadeTransitionPreviewRenderer.class, "crossFade.frag");

    private int nameRes;
    private Class<? extends Transition> transitionClass;
    private Class<? extends AbstractTransitionRenderer> rendererClass;
    private Class<? extends TransitionModel> modelClass;
    private Class<? extends TransitionPreviewRenderer> previewRendererClass;
    private String shader;

    TransitionType(int nameRes, Class<? extends Transition> type,
                   Class<? extends AbstractTransitionRenderer> rendererClass,
                   Class<? extends TransitionModel> modelClass,
                   Class<? extends TransitionPreviewRenderer> previewRendererClass, String shader) {
        this.transitionClass = type;
        this.nameRes = nameRes;
        this.modelClass = modelClass;
        this.rendererClass = rendererClass;
        this.previewRendererClass = previewRendererClass;
        this.shader = shader;
    }

    public Class<? extends Transition> getTransitionClass() {
        return transitionClass;
    }

    public int getNameRes() {
        return nameRes;
    }

    public Class<? extends TransitionModel> getModelClass() {
        return modelClass;
    }

    public Class<? extends AbstractTransitionRenderer> getRendererClass() {
        return rendererClass;
    }

    public Class<? extends TransitionPreviewRenderer> getPreviewRendererClass() {
        return previewRendererClass;
    }

    public String getShader() {
        return shader;
    }
}