package com.gdx.wallpaper.setting.eventbus.transition;

import com.gdx.wallpaper.transition.Transition;

public class TransitionEditEvent {

    private Transition transition;

    public TransitionEditEvent(Transition transition) {
        this.transition = transition;
    }

    public Transition getTransition() {
        return transition;
    }
}
