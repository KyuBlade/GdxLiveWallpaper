package com.gdx.wallpaper.setting.eventbus.transition;

public class TransitionRemovedEvent {

    private final long transitionId;

    public TransitionRemovedEvent(long transitionId) {
        this.transitionId = transitionId;
    }

    public long getTransitionId() {
        return transitionId;
    }
}
