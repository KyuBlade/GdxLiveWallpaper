package com.gdx.wallpaper.collection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gdx.wallpaper.collection.entry.Entry;
import com.gdx.wallpaper.collection.entry.EntryFactory;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class CollectionFactory {

    private final CollectionCache cache;
    private final ContentValues contentValues;

    protected CollectionFactory() {
        cache = new CollectionCache();
        contentValues = new ContentValues();
    }

    protected void insert(Collection collection) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            contentValues.put(DatabaseHelper.CollectionColumns.NAME, collection.getName());
            long rowid = database.insertOrThrow(DatabaseHelper.COLLECTION_TABLE, null,
                                                contentValues);
            collection.setId(rowid);
            contentValues.clear();

            database.setTransactionSuccessful();

            cache.put(collection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void update(Collection collection, UpdateOperation<Collection> updateOperation) {
        updateOperation.provide(collection, contentValues);

        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            database.update(DatabaseHelper.COLLECTION_TABLE, contentValues,
                            DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(collection.getId()) });
            contentValues.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void delete(Collection collection) {
        delete(collection.getId());
    }

    protected void delete(long id) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(DatabaseHelper.COLLECTION_TABLE,
                            DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(id) });
            database.setTransactionSuccessful();

            cache.remove(id);
            EntryFactory.getInstance().delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected long[] getIds() {
        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .query(DatabaseHelper.COLLECTION_TABLE,
                           new String[] { DatabaseHelper.CommonColumns.ID }, null,
                           null, null, null, null);

            try {
                long[] ids = new long[cursor.getCount()];
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    ids[cursor.getPosition()] = cursor.getLong(0);
                }

                return ids;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new long[0];
    }

    protected Collection[] getAll() {
        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .rawQuery("SELECT * FROM " + DatabaseHelper.COLLECTION_TABLE, null);

            Collection[] collections = new Collection[cursor.getCount()];
            try {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                            DatabaseHelper.CommonColumns.ID));
                    Collection collection = cache.getValue(id);
                    if (collection == null) {
                        collection = build(cursor);
                    }
                    collections[cursor.getPosition()] = collection;
                }

                return collections;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Collection[0];
    }

    protected Collection get(long collectionId) {
        Collection collection = cache.getValue(collectionId);
        if (collection != null) {
            return collection;
        }

        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .rawQuery(
                            "SELECT * FROM " + DatabaseHelper.COLLECTION_TABLE +
                                    " WHERE " + DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(collectionId) });

            try {
                cursor.moveToFirst();
                if (!cursor.isAfterLast()) {
                    collection = build(cursor);
                    cache.put(collection);
                }

                return collection;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Collection build(Cursor cursor) {
        long id = cursor
                .getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.CommonColumns.ID));
        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(DatabaseHelper.CollectionColumns.NAME));

        Collection collection = new Collection(id, name);
        Entry[] entries = EntryFactory.getInstance().getAll(id);
        collection.addAll(entries);

        return collection;
    }
}