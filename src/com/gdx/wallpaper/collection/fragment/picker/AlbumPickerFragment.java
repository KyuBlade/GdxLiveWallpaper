package com.gdx.wallpaper.collection.fragment.picker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.fragment.CollectionEntryListFragment;
import com.gdx.wallpaper.collection.fragment.gallery.GalleryCursorAdapter;

import java.lang.reflect.Field;

public class AlbumPickerFragment extends GalleryPickerFragment {

    public static final String TAG = "AlbumPickerFragment";

    private static final String[] BUCKET_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
    };

    private static final String BUCKET_GROUP_BY = "1) GROUP BY 2,(2";
    private static final String BUCKET_ORDER_BY = MediaStore.Images.Media.DEFAULT_SORT_ORDER;

    private static final String[] CONTENT_PROJECTION = {
            MediaStore.Images.Media.DATA,
    };
    private static final String CONTENT_ORDER_BY = MediaStore.Images.Media.DISPLAY_NAME;

    public AlbumPickerFragment() {
        super(R.string.gallery_albums_empty);
    }

    public static AlbumPickerFragment newInstance() {
        Bundle args = new Bundle();

        AlbumPickerFragment fragment = new AlbumPickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                String bucketId = cursor
                        .getString(cursor.getColumnIndexOrThrow(
                                MediaStore.Images.Media.BUCKET_ID));
                ImagePickerFragment pickerFragment = ImagePickerFragment.newInstance(bucketId);
                pickerFragment.setTargetFragment(getTargetFragment(), getTargetRequestCode());
                getFragmentManager().beginTransaction().replace(R.id.content_container,
                                                                pickerFragment,
                                                                ImagePickerFragment.TAG)
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                BUCKET_PROJECTION, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);
    }

    @Override
    protected CursorAdapter createAdapter() {
        return new GalleryCursorAdapter(getActivity(),
                                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME) {
            @Override
            public void changeCursor(Cursor cursor) {
                super.changeCursor(cursor);

                if (cursor == null) {
                    return;
                }

                try {
                    Field rowIdField = CursorAdapter.class.getDeclaredField("mRowIDColumn");
                    rowIdField.setAccessible(true);

                    int columnId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID);
                    rowIdField.set(this, columnId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void onDone() {
        long[] selectedIds = gridView.getCheckedItemIds();
        int size = selectedIds.length;
        String selection;
        String[] selectionArgs = new String[size];

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            selectionArgs[i] = String.valueOf(selectedIds[i]);
            builder.append(MediaStore.Images.Media.BUCKET_ID).append(" = ?");
            if (i + 1 < size) {
                builder.append(" OR ");
            }
        }
        selection = builder.toString();

        Cursor cursor = getActivity().getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CONTENT_PROJECTION, selection,
                       selectionArgs, CONTENT_ORDER_BY);

        String[] imagePaths = new String[cursor.getCount()];
        try {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                do {
                    imagePaths[cursor.getPosition()] = cursor.getString(columnIndex);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra(CollectionEntryListFragment.RESULT, imagePaths);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,
                                  resultIntent);
        getFragmentManager().popBackStack();
    }
}
