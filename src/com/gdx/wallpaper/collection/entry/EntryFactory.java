package com.gdx.wallpaper.collection.entry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.badlogic.gdx.utils.Scaling;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.setting.database.DatabaseHelper;

public class EntryFactory {

    static class SingletonHolder {

        private static final EntryFactory INSTANCE = new EntryFactory();
    }

    private final SQLiteStatement deleteStatement;

    private ContentValues contentValues;

    public EntryFactory() {
        contentValues = new ContentValues();

        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        StringBuilder builder = new StringBuilder("DELETE FROM ");
        builder.append(DatabaseHelper.ENTRY_TABLE).append(" WHERE ")
                .append(DatabaseHelper.CommonColumns.ID).append(" = ?");
        deleteStatement = database.compileStatement(builder.toString());
    }

    public static EntryFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void insert(Entry entry) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            contentValues
                    .put(DatabaseHelper.EntryColumns.COLLECTION_ID, entry.getCollectionId());
            contentValues
                    .put(DatabaseHelper.EntryColumns.LANDSCAPE_OFFSET_X,
                         entry.getLandscapeOffsetX());
            contentValues
                    .put(DatabaseHelper.EntryColumns.LANDSCAPE_OFFSET_Y,
                         entry.getLandscapeOffsetY());
            contentValues
                    .put(DatabaseHelper.EntryColumns.LANDSCAPE_ZOOM, entry.getLandscapeZoom());
            contentValues.put(DatabaseHelper.EntryColumns.LANDSCAPE_ROTATION,
                              entry.getLandscapeRotation());
            contentValues
                    .put(DatabaseHelper.EntryColumns.PORTRAIT_OFFSET_X,
                         entry.getPortraitOffsetX());
            contentValues
                    .put(DatabaseHelper.EntryColumns.PORTRAIT_OFFSET_Y,
                         entry.getPortraitOffsetY());
            contentValues
                    .put(DatabaseHelper.EntryColumns.PORTRAIT_ZOOM, entry.getPortraitZoom());
            contentValues
                    .put(DatabaseHelper.EntryColumns.PORTRAIT_ROTATION,
                         entry.getPortraitRotation());
            contentValues
                    .put(DatabaseHelper.EntryColumns.SCALE_TYPE, entry.getScalingType().name());
            contentValues.put(DatabaseHelper.EntryColumns.IMAGE_PATH, entry.getImagePath());
            long rowId = database.insertOrThrow(DatabaseHelper.ENTRY_TABLE, null, contentValues);
            entry.setId(rowId);
            contentValues.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void insert(Entry[] entries) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            for (Entry entry : entries) {
                contentValues
                        .put(DatabaseHelper.EntryColumns.COLLECTION_ID, entry.getCollectionId());
                contentValues
                        .put(DatabaseHelper.EntryColumns.LANDSCAPE_OFFSET_X,
                             entry.getLandscapeOffsetX());
                contentValues
                        .put(DatabaseHelper.EntryColumns.LANDSCAPE_OFFSET_Y,
                             entry.getLandscapeOffsetY());
                contentValues
                        .put(DatabaseHelper.EntryColumns.LANDSCAPE_ZOOM, entry.getLandscapeZoom());
                contentValues.put(DatabaseHelper.EntryColumns.LANDSCAPE_ROTATION,
                                  entry.getLandscapeRotation());
                contentValues
                        .put(DatabaseHelper.EntryColumns.PORTRAIT_OFFSET_X,
                             entry.getPortraitOffsetX());
                contentValues
                        .put(DatabaseHelper.EntryColumns.PORTRAIT_OFFSET_Y,
                             entry.getPortraitOffsetY());
                contentValues
                        .put(DatabaseHelper.EntryColumns.PORTRAIT_ZOOM, entry.getPortraitZoom());
                contentValues
                        .put(DatabaseHelper.EntryColumns.PORTRAIT_ROTATION,
                             entry.getPortraitRotation());
                contentValues
                        .put(DatabaseHelper.EntryColumns.SCALE_TYPE, entry.getScalingType().name());
                contentValues.put(DatabaseHelper.EntryColumns.IMAGE_PATH, entry.getImagePath());
                long rowId = database
                        .insertOrThrow(DatabaseHelper.ENTRY_TABLE, null, contentValues);
                entry.setId(rowId);
            }
            contentValues.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void update(Entry entry) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            contentValues
                    .put(DatabaseHelper.EntryColumns.COLLECTION_ID, entry.getCollectionId());
            contentValues
                    .put(DatabaseHelper.EntryColumns.LANDSCAPE_OFFSET_X,
                         entry.getLandscapeOffsetX());
            contentValues
                    .put(DatabaseHelper.EntryColumns.LANDSCAPE_OFFSET_Y,
                         entry.getLandscapeOffsetY());
            contentValues
                    .put(DatabaseHelper.EntryColumns.LANDSCAPE_ZOOM, entry.getLandscapeZoom());
            contentValues.put(DatabaseHelper.EntryColumns.LANDSCAPE_ROTATION,
                              entry.getLandscapeRotation());
            contentValues
                    .put(DatabaseHelper.EntryColumns.PORTRAIT_OFFSET_X,
                         entry.getPortraitOffsetX());
            contentValues
                    .put(DatabaseHelper.EntryColumns.PORTRAIT_OFFSET_Y,
                         entry.getPortraitOffsetY());
            contentValues
                    .put(DatabaseHelper.EntryColumns.PORTRAIT_ZOOM, entry.getPortraitZoom());
            contentValues
                    .put(DatabaseHelper.EntryColumns.PORTRAIT_ROTATION,
                         entry.getPortraitRotation());
            contentValues
                    .put(DatabaseHelper.EntryColumns.SCALE_TYPE, entry.getScalingType().name());
            contentValues.put(DatabaseHelper.EntryColumns.IMAGE_PATH, entry.getImagePath());
            database.update(DatabaseHelper.ENTRY_TABLE, contentValues,
                            DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(entry.getId()) });
            contentValues.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void delete(long collectionId) throws Exception {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        if (!database.inTransaction()) {
            database.beginTransaction();
        }
        database.delete(DatabaseHelper.ENTRY_TABLE,
                        DatabaseHelper.EntryColumns.COLLECTION_ID + " = ?",
                        new String[] { String.valueOf(collectionId) });
        if (!database.inTransaction()) {
            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }

    public void delete(Collection collection, long[] entryIds) {
        for (int i = 0; i < entryIds.length; i++) {
            long entryId = entryIds[i];
            deleteStatement.bindLong(1, entryId);
            deleteStatement.executeUpdateDelete();

            collection.removeEntries(entryIds);
        }
    }

    public Entry[] getAll(long collectionId) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseHelper.ENTRY_TABLE + " WHERE " + DatabaseHelper.EntryColumns.COLLECTION_ID + " = ?",
                new String[] { String.valueOf(collectionId) });
        try {
            Entry[] entries = new Entry[cursor.getCount()];
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                entries[cursor.getPosition()] = build(cursor);
            }

            return entries;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return null;
    }

    public Entry get(long entryId) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + DatabaseHelper.ENTRY_TABLE + " WHERE " + DatabaseHelper.CommonColumns.ID + " = ?",
                new String[] { String.valueOf(entryId) });
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                return build(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return null;
    }

    private Entry build(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.CommonColumns.ID));
        long collectionId = cursor
                .getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.COLLECTION_ID));
        int landscapeOffsetX = cursor.getInt(
                cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.LANDSCAPE_OFFSET_X));
        int landscapeOffsetY = cursor.getInt(
                cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.LANDSCAPE_OFFSET_Y));
        float landscapeZoom = cursor
                .getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.LANDSCAPE_ZOOM));
        float landscapeRotation = cursor.getFloat(
                cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.LANDSCAPE_ROTATION));
        int portraitOffsetX = cursor.getInt(
                cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.PORTRAIT_OFFSET_X));
        int portraitOffsetY = cursor.getInt(
                cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.PORTRAIT_OFFSET_Y));
        float portraitZoom = cursor
                .getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.PORTRAIT_ZOOM));
        float portraitRotation = cursor.getFloat(
                cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.PORTRAIT_ROTATION));
        String scaleTypeString = cursor.getString(
                cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.SCALE_TYPE));
        Scaling scaleType = Scaling.valueOf(scaleTypeString);
        String imagePath = cursor
                .getString(cursor.getColumnIndexOrThrow(DatabaseHelper.EntryColumns.IMAGE_PATH));

        return new Entry(id, collectionId, landscapeOffsetX, landscapeOffsetY, landscapeZoom,
                         landscapeRotation, portraitOffsetX, portraitOffsetY, portraitZoom,
                         portraitRotation, scaleType, imagePath);
    }
}
