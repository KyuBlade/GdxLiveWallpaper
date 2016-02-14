package com.gdx.wallpaper.collection.fragment.picker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.SparseBooleanArray;
import android.widget.CursorAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.fragment.CollectionEntryListFragment;
import com.gdx.wallpaper.collection.fragment.gallery.GalleryCursorAdapter;

public class ImagePickerFragment extends GalleryPickerFragment {

    public static final String TAG = "ImagePickerFragment";

    private static final String BUCKET_ID = "BucketId";

    private static final String[] PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DATA
    };

    private static final String SELECTION = MediaStore.Images.Media.BUCKET_ID + " = ?";

    private static final String ORDER_BY = MediaStore.Images.Media.DISPLAY_NAME;

    private String bucketId;

    public ImagePickerFragment() {
        super(R.string.gallery_images_empty);
    }

    public static ImagePickerFragment newInstance(String bucketId) {
        Bundle args = new Bundle();
        args.putString(BUCKET_ID, bucketId);

        ImagePickerFragment fragment = new ImagePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bucketId = getArguments().getString(BUCKET_ID);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                PROJECTION, SELECTION,
                                new String[] { bucketId }, ORDER_BY);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        super.onLoadFinished(loader, data);
    }

    @Override
    protected CursorAdapter createAdapter() {
        return new GalleryCursorAdapter(getActivity(), MediaStore.Images.Media.DISPLAY_NAME);
    }

    @Override
    protected void onDone() {
        String[] selectedPaths = new String[gridView.getCheckedItemCount()];
        SparseBooleanArray selectedPositions = gridView.getCheckedItemPositions();
        for (int i = 0, j = 0; j < selectedPaths.length; i++) {
            boolean isChecked = selectedPositions.get(i, false);
            if (isChecked) {
                Cursor cursor = (Cursor) adapter.getItem(i);
                String path = cursor
                        .getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                selectedPaths[j] = path;
                j++;
            }
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra(CollectionEntryListFragment.RESULT, selectedPaths);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,
                                  resultIntent);
        getFragmentManager().popBackStack(CollectionEntryListFragment.TAG,
                                          FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
