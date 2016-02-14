package com.gdx.wallpaper.collection;

import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class CollectionManager {

    private final CollectionFactory factory;

    private CollectionManager() {
        factory = new CollectionFactory();
    }

    public static CollectionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void insert(Collection collection) {
        factory.insert(collection);
    }

    public void update(Collection collection, UpdateOperation<Collection> updateOperation) {
        factory.update(collection, updateOperation);
    }

    public void remove(Collection collection) {
        factory.delete(collection);
    }

    public void remove(long id) {
        factory.delete(id);
    }

    public Collection[] getAll() {
        return factory.getAll();
    }

    public Collection get(long id) {
        return factory.get(id);
    }

    private static class SingletonHolder {

        private static final CollectionManager INSTANCE = new CollectionManager();
    }
}