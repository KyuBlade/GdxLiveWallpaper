package com.gdx.wallpaper.image.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.collection.entry.Entry;
import com.gdx.wallpaper.collection.fragment.CollectionEntryEditView;

public class CollectionEntryEditFragment extends Fragment {

    public static final String TAG = "CollectionEntryEditFragment";

    private static final String COLLECTION_ID = "CollectionId";
    private static final String ENTRIES = "Entries";

    private CollectionEntryEditView view;

    private Collection collection;
    private Entry[] entries;

    public static CollectionEntryEditFragment newInstance(long collectionId, long[] entryIds) {
        Bundle args = new Bundle();
        args.putLong(COLLECTION_ID, collectionId);
        args.putLongArray(ENTRIES, entryIds);

        CollectionEntryEditFragment fragment = new CollectionEntryEditFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        collection = CollectionManager.getInstance().get(args.getLong(COLLECTION_ID, -1));

        long[] entryIds = args.getLongArray(ENTRIES);
        entries = new Entry[entryIds.length];
        for (int i = 0; i < entryIds.length; i++) {
            entries[i] = collection.get(entryIds[i]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (CollectionEntryEditView) inflater
                .inflate(R.layout.wallpaper_image_edit_view, container, false);
        view.setWallpapersQueue(entries);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void onPause() {
        super.onPause();

        ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }
}
