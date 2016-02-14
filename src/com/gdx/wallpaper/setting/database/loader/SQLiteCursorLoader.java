package com.gdx.wallpaper.setting.database.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

public class SQLiteCursorLoader extends AsyncTaskLoader<Cursor> {

    private SQLiteDatabase database;
    private String query;
    private String[] args;

    private Cursor lastCursor = null;

    public SQLiteCursorLoader(Context context,
                              SQLiteDatabase database, String query, String[] args) {
        super(context);

        this.database = database;
        this.query = query;
        this.args = args;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = database.rawQuery(query, args);
        if (cursor != null) {
            cursor.getCount();
        }

        return cursor;
    }

    @Override
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor oldCursor = lastCursor;
        lastCursor = cursor;

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if (lastCursor != null) {
            deliverResult(lastCursor);
        }

        if (takeContentChanged() || lastCursor == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (lastCursor != null && !lastCursor.isClosed()) {
            lastCursor.close();
        }
        lastCursor = null;
    }
}
