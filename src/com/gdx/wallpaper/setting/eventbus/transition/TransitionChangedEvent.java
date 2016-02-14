package com.gdx.wallpaper.setting.eventbus.transition;

import com.gdx.wallpaper.setting.database.operation.UpdateOperation;
import com.gdx.wallpaper.transition.Transition;

public class TransitionChangedEvent {

    private long transitionId;
    private UpdateOperation<Transition> updateOperation;

    public TransitionChangedEvent(long transitionId, UpdateOperation updateOperation) {
        this.transitionId = transitionId;
        this.updateOperation = updateOperation;
    }

    public long getTransitionId() {
        return transitionId;
    }

    public UpdateOperation getUpdateOperation() {
        return updateOperation;
    }
}
