package com.gdx.wallpaper.playlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;

public class PlaylistFactory {

    private PlaylistCache cache;
    private ContentValues contentValues;

    protected PlaylistFactory() {
        cache = new PlaylistCache();
        contentValues = new ContentValues();
    }

    protected void insert(Playlist playlist) {
        if (playlist == null) {
            throw new NullPointerException("Playlist should not be null");
        }

        contentValues.put(DatabaseHelper.PlaylistColumns.NAME, playlist.getName());
        contentValues.put(DatabaseHelper.PlaylistColumns.TRANSITION_ID, playlist.getTransitionId());
        contentValues.put(DatabaseHelper.PlaylistColumns.COLLECTION_ID, playlist.getCollectionId());
        contentValues.put(DatabaseHelper.PlaylistColumns.SCROLLABLE, playlist.isScrollable());
        contentValues.put(DatabaseHelper.PlaylistColumns.SCROLL_TYPE, playlist.getScrollType().ordinal());
        contentValues.put(DatabaseHelper.PlaylistColumns.ACTIVE, playlist.isActive());

        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            long rowId = database
                    .insertOrThrow(DatabaseHelper.PLAYLIST_TABLE, null, contentValues);
            playlist.setId(rowId);

            contentValues.clear();
            database.setTransactionSuccessful();

            cache.put(playlist);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void update(Playlist playlist, UpdateOperation<Playlist> updateOperation) {
        if (playlist == null) {
            throw new NullPointerException("Playlist should not be null");
        }
        if (updateOperation == null) {
            throw new NullPointerException("Update operation should not be null");
        }

        updateOperation.provide(playlist, contentValues);

        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            database.update(DatabaseHelper.PLAYLIST_TABLE, contentValues,
                            DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(playlist.getId()) });
            contentValues.clear();
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    protected void update(long id, UpdateOperation<Playlist> updateOperation) {
        update(get(id), updateOperation);
    }

    protected void delete(Playlist playlist) {
        if (playlist == null) {
            throw new NullPointerException("Playlist should not be null");
        }

        delete(playlist.getId());
    }

    protected void delete(long id) {
        SQLiteDatabase database = DatabaseHelper.getInstance().getWritableDatabase();
        try {
            database.beginTransaction();
            database
                    .delete(DatabaseHelper.PLAYLIST_TABLE, DatabaseHelper.CommonColumns.ID + " = ?",
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
                    .query(DatabaseHelper.PLAYLIST_TABLE,
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

    public Playlist[] getAll() {
        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .rawQuery("SELECT * FROM " + DatabaseHelper.PLAYLIST_TABLE, null);
            Playlist[] playlists = new Playlist[cursor.getCount()];
            try {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    long id = cursor
                            .getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.CommonColumns.ID));

                    Playlist playlist = cache.getValue(id);
                    if (playlist == null) {
                        playlist = build(cursor);
                        cache.put(playlist);
                    }
                    playlists[cursor.getPosition()] = playlist;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }

            return playlists;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Playlist[0];
    }

    protected Playlist get(long id) {
        Playlist playlist = cache.getValue(id);
        if (playlist != null) {
            return playlist;
        }

        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .rawQuery(
                            "SELECT * FROM " + DatabaseHelper.PLAYLIST_TABLE +
                                    " WHERE " + DatabaseHelper.CommonColumns.ID + " = ?",
                            new String[] { String.valueOf(id) });

            try {
                cursor.moveToFirst();
                if (!cursor.isAfterLast()) {
                    playlist = build(cursor);
                    cache.put(playlist);
                }

                return playlist;
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

    protected Playlist getActive() {
        Playlist playlist = cache.getActive();
        if (playlist != null) {
            return playlist;
        }

        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .rawQuery(
                            "SELECT * FROM " + DatabaseHelper.PLAYLIST_TABLE +
                                    " WHERE " + DatabaseHelper.PlaylistColumns.ACTIVE + " = ?",
                            new String[] { "1" });

            try {
                if (cursor.moveToFirst()) {
                    playlist = build(cursor);
                    cache.put(playlist);
                }

                return playlist;
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

    protected boolean isActive(long id) {
        Playlist playlist = cache.getActive();
        if (playlist != null) {
            return playlist.isActive();
        }

        SQLiteDatabase database = DatabaseHelper.getInstance().getReadableDatabase();
        try {
            Cursor cursor = database
                    .query(DatabaseHelper.PLAYLIST_TABLE, new String[] {
                                   DatabaseHelper.PlaylistColumns.ACTIVE
                           }, DatabaseHelper.CommonColumns.ID + " = ?",
                           new String[] { String.valueOf(id) }, null, null, null);

            try {
                cursor.moveToFirst();

                return cursor.getShort(0) != 0;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Playlist build(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.CommonColumns.ID));
        String name = cursor.getString(
                cursor.getColumnIndexOrThrow(DatabaseHelper.PlaylistColumns.NAME));
        long transitionId = cursor.getLong(cursor.getColumnIndexOrThrow(
                DatabaseHelper.PlaylistColumns.TRANSITION_ID));
        long collectionId = cursor
                .getLong(
                        cursor.getColumnIndexOrThrow(DatabaseHelper.PlaylistColumns.COLLECTION_ID));
        boolean scrollable = cursor
                .getInt(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.PlaylistColumns.SCROLLABLE)) != 0;
        int scrollTypeIndex = cursor.getInt(cursor.getColumnIndexOrThrow(
                DatabaseHelper.PlaylistColumns.SCROLL_TYPE));
        ScrollType[] scrollTypes = ScrollType.values();
        if (scrollTypeIndex > scrollTypes.length - 1 || scrollTypeIndex < 0) {
            scrollTypeIndex = ScrollType.NONE.ordinal();
        }
        ScrollType scrollType = scrollTypes[scrollTypeIndex];
        boolean active = cursor
                .getInt(cursor.getColumnIndexOrThrow(
                        DatabaseHelper.PlaylistColumns.ACTIVE)) != 0;

        return new Playlist(id, name, transitionId, collectionId, scrollable, scrollType, active);
    }
}