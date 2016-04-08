package com.gdx.wallpaper.environment;

import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class EnvironmentManager {

    private final EnvironmentFactory factory;

    private EnvironmentManager() {
        factory = new EnvironmentFactory();
    }

    public static EnvironmentManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void insert(Environment environment) {
        factory.insert(environment);
    }

    public void update(Environment environment, UpdateOperation<Environment> updateOperation) {
        factory.update(environment, updateOperation);
    }

    public void update(long id, UpdateOperation<Environment> updateOperation) {
        factory.update(id, updateOperation);
    }

    public void update(Environment environment, String columnName, Object value) {
        factory.update(environment, columnName, value);
    }

    public void remove(Environment environment) {
        factory.delete(environment);
    }

    public void remove(long id) {
        factory.delete(id);
    }

    public Environment[] getAll() {
        return factory.getAll();
    }

    public Environment get(long id) {
        return factory.get(id);
    }

    private static class SingletonHolder {

        private static final EnvironmentManager INSTANCE = new EnvironmentManager();
    }
}