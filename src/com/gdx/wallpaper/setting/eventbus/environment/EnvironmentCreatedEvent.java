package com.gdx.wallpaper.setting.eventbus.environment;

import com.gdx.wallpaper.environment.EnvironmentType;

public class EnvironmentCreatedEvent {

    private EnvironmentType type;

    public EnvironmentCreatedEvent(EnvironmentType type) {
        this.type = type;
    }

    public EnvironmentType getType() {
        return type;
    }
}