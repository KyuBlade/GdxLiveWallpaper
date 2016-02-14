package com.gdx.wallpaper.transition;

import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

import java.util.ArrayList;
import java.util.List;

public class TransitionManager {

    private final TransitionFactory factory;
    private final List<Long> transitionIds;

    private TransitionManager() {
        factory = new TransitionFactory();

        transitionIds = new ArrayList<>();
        long[] ids = factory.getIds();
        for (int i = 0; i < ids.length; i++) {
            transitionIds.add(ids[i]);
        }
    }

    public static TransitionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void insert(Transition transition) {
        factory.insert(transition);
        transitionIds.add(transition.getId());
    }

    public void update(Transition transition, UpdateOperation<Transition> updateOperation) {
        factory.update(transition, updateOperation);
    }

    public void update(long id, UpdateOperation<Transition> updateOperation) {
        factory.update(id, updateOperation);
    }

    public void remove(Transition transition) {
        factory.delete(transition);
        transitionIds.remove(transition.getId());
    }

    public void remove(long id) {
        factory.delete(id);
        transitionIds.remove(id);
    }

    public Transition[] getAll() {
        return factory.getAll();
    }

    public Transition get(long id) {
        return factory.get(id);
    }

    private static class SingletonHolder {

        private static final TransitionManager INSTANCE = new TransitionManager();
    }
}