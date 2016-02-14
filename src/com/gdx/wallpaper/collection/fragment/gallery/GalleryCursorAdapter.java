package com.gdx.wallpaper.collection.fragment.gallery;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class GalleryCursorAdapter extends CursorAdapter {

    private String nameColumn;
    private GalleryAdapterBinder binder;

    public GalleryCursorAdapter(Context context, String nameColumn) {
        super(context, null, 0);

        binder = new GalleryAdapterBinder(context);
        this.nameColumn = nameColumn;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return binder.newViewInternal(context, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = cursor.getString(
                cursor.getColumnIndex(nameColumn));
        int imageId = cursor
                .getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        String path = ContentUris
                .withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageId)
                .toString();

        binder.bindViewInternal(view, name, path);
    }
}
