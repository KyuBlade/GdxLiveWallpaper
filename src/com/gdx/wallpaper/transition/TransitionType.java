package com.gdx.wallpaper.transition;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.transition.fragment.FadeTransitionEditFragment;
import com.gdx.wallpaper.transition.fragment.NullTransitionEditFragment;
import com.gdx.wallpaper.transition.fragment.TransitionEditFragment;
import com.gdx.wallpaper.transition.renderer.AbstractTransitionRenderer;
import com.gdx.wallpaper.transition.renderer.FadeTransitionRenderer;
import com.gdx.wallpaper.transition.renderer.NullTransitionRenderer;
import com.gdx.wallpaper.transition.type.FadeTransition;
import com.gdx.wallpaper.transition.type.NullTransition;

public enum TransitionType {
    NONE(1, NullTransition.class, R.string.transition_none, NullTransitionEditFragment.class,
         NullTransitionRenderer.class),
    FADE(2, FadeTransition.class, R.string.transition_fade, FadeTransitionEditFragment.class,
         FadeTransitionRenderer.class);

    private int id;
    private int nameRes;
    private Class<? extends Transition> transitionClass;
    protected Class<? extends TransitionEditFragment> editFragmentClass;
    protected Class<? extends AbstractTransitionRenderer> rendererClass;

    TransitionType(int id, Class<? extends Transition> type, int nameRes,
                   Class<? extends TransitionEditFragment> editFragmentClass,
                   Class<? extends AbstractTransitionRenderer> rendererClass) {
        this.id = id;
        this.transitionClass = type;
        this.nameRes = nameRes;
        this.editFragmentClass = editFragmentClass;
        this.rendererClass = rendererClass;
    }

    public int getId() {
        return id;
    }

    public Class<? extends Transition> getTransitionClass() {
        return transitionClass;
    }

    public int getNameRes() {
        return nameRes;
    }

    public Class<? extends TransitionEditFragment> getEditFragmentClass() {
        return editFragmentClass;
    }

    public Class<? extends AbstractTransitionRenderer> getRendererClass() {
        return rendererClass;
    }

    public static TransitionType getFor(int id) {
        for (TransitionType type : values()) {
            if (type.id == id) {
                return type;
            }
        }

        return null;
    }
}