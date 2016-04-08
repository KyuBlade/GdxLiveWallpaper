package com.gdx.wallpaper.transition;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.DatabaseHelper.TransitionColumns;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class TransitionFactory {

    private final TransitionCache cache;
    private ContentValues contentValues;

    protected TransitionFactory() {
        cache = new TransitionCache();
        contentValues = new ContentValues();
    }

    protected void insert(Transition transition) {
        contentValues.clear();
        transition.provideInsertInternal(contentValues);

        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            long rowId = database.insertOrThrow(DatabaseHelper.TRANSITION_TABLE, null,
                                                contentValues);
            transition.setId(rowId);
            database.setTransactionSuccessful();

            cache.put(transition);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void update(Transition transition, UpdateOperation<Transition> updateOperation) {
        contentValues.clear();
        updateOperation.provide(transition, contentValues);

        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            database.update(DatabaseHelper.TRANSITION_TABLE, contentValues,
                            DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(transition.getId()) });
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void update(long id, UpdateOperation<Transition> updateOperation) {
        update(get(id), updateOperation);
    }

    public void update(Transition transition, String columnName, Object value) {
        contentValues.clear();
        addAbsToContentValues(contentValues, columnName, value);
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            database.update(DatabaseHelper.TRANSITION_TABLE, contentValues,
                            DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(transition.getId()) });
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void delete(Transition transition) {
        delete(transition.getId());
    }

    protected void delete(long id) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            database.delete(DatabaseHelper.TRANSITION_TABLE,
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
                    .query(DatabaseHelper.TRANSITION_TABLE,
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

    protected Transition[] getAll() {
        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .rawQuery("SELECT * FROM " + DatabaseHelper.TRANSITION_TABLE, null);

            Transition[] transitions = new Transition[cursor.getCount()];
            try {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(
                            DatabaseHelper.CommonColumns.ID));
                    Transition transition = cache.getValue(id);
                    if (transition == null) {
                        int type = cursor
                                .getInt(cursor.getColumnIndexOrThrow(TransitionColumns.TYPE));
                        TransitionType enumType = TransitionType.values()[type];
                        try {
                            transition = enumType.getTransitionClass().newInstance();
                            transition.buildInternal(cursor);

                            cache.put(transition);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                    transitions[cursor.getPosition()] = transition;
                }

                return transitions;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Transition[0];
    }

    protected Transition get(long transitionId) {
        Transition transition = cache.getValue(transitionId);
        if (transition != null) {
            return transition;
        }

        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor result = database
                    .rawQuery(
                            "SELECT * FROM " + DatabaseHelper.TRANSITION_TABLE +
                                    " WHERE " + DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(transitionId) });

            try {
                for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                    int type = result
                            .getInt(result.getColumnIndexOrThrow(TransitionColumns.TYPE));
                    TransitionType enumType = TransitionType.values()[type];

                    try {
                        transition = enumType.getTransitionClass().newInstance();
                        transition.buildInternal(result);
                        cache.put(transition);

                        return transition;
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addAbsToContentValues(ContentValues values, String columnName, Object value) {
        if (value instanceof Integer) {
            values.put(columnName, (int) value);
        } else if (value instanceof Float) {
            values.put(columnName, (float) value);
        } else if (value instanceof String) {
            values.put(columnName, (String) value);
        } else if (value instanceof Double) {
            values.put(columnName, (double) value);
        } else if (value instanceof Long) {
            values.put(columnName, (long) value);
        }
    }
}