package com.gdx.wallpaper.setting.eventbus.environment;

public class EnvironmentRemoveEvent {

    private long id;

    public EnvironmentRemoveEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
