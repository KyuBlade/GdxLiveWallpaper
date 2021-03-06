package com.gdx.wallpaper.environment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class EnvironmentFactory {

    private final EnvironmentCache cache;
    private ContentValues contentValues;

    protected EnvironmentFactory() {
        cache = new EnvironmentCache();
        contentValues = new ContentValues();
    }

    protected void insert(Environment environment) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            contentValues.put(DatabaseHelper.EnvironmentColumns.NAME, environment.getName());
            contentValues
                    .put(DatabaseHelper.EnvironmentColumns.TYPE, environment.getType().ordinal());
            contentValues.put(DatabaseHelper.EnvironmentColumns.SCREEN_COUNT,
                              environment.getScreenCount());
            long rowId = database.insertOrThrow(DatabaseHelper.ENVIRONMENT_TABLE, null,
                                                contentValues);
            environment.setId(rowId);
            contentValues.clear();
            database.setTransactionSuccessful();

            cache.put(environment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void update(Environment environment, UpdateOperation<Environment> updateOperation) {
        updateOperation.provide(environment, contentValues);

        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            database.update(DatabaseHelper.ENVIRONMENT_TABLE, contentValues,
                            DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(environment.getId()) });
            contentValues.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void update(long id, UpdateOperation<Environment> updateOperation) {
        update(get(id), updateOperation);
    }

    protected void delete(Environment environment) {
        delete(environment.getId());
    }

    protected void delete(long id) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            database.delete(DatabaseHelper.ENVIRONMENT_TABLE,
                            DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(id) });
            database.setTransactionSuccessful();

            cache.remove(id);
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
                    .query(DatabaseHelper.ENVIRONMENT_TABLE,
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

    protected Environment[] getAll() {
        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .rawQuery("SELECT * FROM " + DatabaseHelper.ENVIRONMENT_TABLE, null);

            Environment[] environments = new Environment[cursor.getCount()];
            try {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                            DatabaseHelper.CommonColumns.ID));
                    Environment environment = cache.getValue(id);
                    if (environment == null) {
                        environment = build(cursor);
                        cache.put(environment);
                    }
                    environments[cursor.getPosition()] = environment;
                }

                return environments;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Environment[0];
    }

    protected Environment get(long environmentId) {
        Environment environment = cache.getValue(environmentId);
        if (environment != null) {
            return environment;
        }

        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .rawQuery(
                            "SELECT * FROM " + DatabaseHelper.ENVIRONMENT_TABLE +
                                    " WHERE " + DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(environmentId) });

            try {
                if (cursor.moveToFirst()) {
                    environment = build(cursor);
                    cache.put(environment);

                    return environment;
                }
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

    private Environment build(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.CommonColumns.ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(
                DatabaseHelper.EnvironmentColumns.NAME));
        int type = cursor
                .getInt(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.EnvironmentColumns.TYPE));
        EnvironmentType enumType = EnvironmentType.values()[type];
        int screenCount = cursor.getInt(cursor.getColumnIndexOrThrow(
                DatabaseHelper.EnvironmentColumns.SCREEN_COUNT));

        return new Environment(id, name, enumType, screenCount);
    }
}