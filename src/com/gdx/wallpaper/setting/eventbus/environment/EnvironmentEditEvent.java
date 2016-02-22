package com.gdx.wallpaper.setting.eventbus.environment;

public class EnvironmentEditEvent {

    private long environmentId;

    public EnvironmentEditEvent(long environmentId) {
        this.environmentId = environmentId;
    }

    public long getEnvironmentId() {
        return environmentId;
    }
}