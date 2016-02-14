package com.gdx.wallpaper.setting.eventbus.transition;

import com.gdx.wallpaper.transition.TransitionType;

public class TransitionCreatedEvent {

    private TransitionType type;

    public TransitionCreatedEvent(TransitionType type) {
        this.type = type;
    }

    public TransitionType getType() {
        return type;
    }
}
