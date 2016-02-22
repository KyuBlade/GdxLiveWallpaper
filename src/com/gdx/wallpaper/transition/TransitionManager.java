package com.gdx.wallpaper.transition;

import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class TransitionManager {

    private final TransitionFactory factory;

    private TransitionManager() {
        factory = new TransitionFactory();
    }

    public static TransitionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void insert(Transition transition) {
        factory.insert(transition);
    }

    public void update(Transition transition, UpdateOperation<Transition> updateOperation) {
        factory.update(transition, updateOperation);
    }

    public void update(long id, UpdateOperation<Transition> updateOperation) {
        factory.update(id, updateOperation);
    }

    public void remove(Transition transition) {
        factory.delete(transition);
    }

    public void remove(long id) {
        factory.delete(id);
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