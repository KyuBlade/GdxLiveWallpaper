package com.gdx.wallpaper.collection.fragment.picker;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CursorAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.fragment.gallery.GalleryFragment;
import com.gdx.wallpaper.util.PermissionUtils;

public abstract class GalleryPickerFragment
        extends GalleryFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private int emptyText;

    protected GalleryPickerFragment(int emptyText) {
        this.emptyText = emptyText;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (PermissionUtils.invokeReadExternalStoragePermission(this)) {
            setEmptyText(emptyText);
            getLoaderManager().initLoader(0, null, this);
        } else {
            setEmptyText(R.string.permission_required);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.READ_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLoaderManager().initLoader(0, null, this);
            } else {
                setEmptyText(R.string.permission_required);
            }
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.picker_menu, menu);

        mode.setTitle(R.string.select_items);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.picker_menu_select_all:
                int count = adapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (!gridView.isItemChecked(i)) {
                        gridView.setItemChecked(i, true);
                    }
                }

                return true;

            case R.id.picker_menu_done:
                onDone();
                mode.finish();

                return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((CursorAdapter) adapter).changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((CursorAdapter) adapter).changeCursor(null);
    }

    protected abstract void onDone();
}
