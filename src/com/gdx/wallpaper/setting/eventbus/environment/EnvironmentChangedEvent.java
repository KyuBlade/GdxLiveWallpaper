package com.gdx.wallpaper.setting.eventbus.environment;

import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class EnvironmentChangedEvent {

    private final long environmentId;
    private final UpdateOperation<Environment> updateOperation;

    public EnvironmentChangedEvent(long environmentId, UpdateOperation<Environment> updateOperation) {
        this.environmentId = environmentId;
        this.updateOperation = updateOperation;
    }

    public long getEnvironmentId() {
        return environmentId;
    }

    public UpdateOperation<Environment> getUpdateOperation() {
        return updateOperation;
    }
}
