package com.gdx.wallpaper.collection.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.collection.entry.Entry;
import com.gdx.wallpaper.collection.entry.EntryFactory;
import com.gdx.wallpaper.collection.fragment.gallery.GalleryObjectAdapter;
import com.gdx.wallpaper.collection.fragment.picker.AlbumPickerFragment;
import com.gdx.wallpaper.image.fragment.CollectionEntryEditFragment;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.collection.EntriesInsertedEvent;
import com.gdx.wallpaper.setting.ui.GalleryFragment;

public class CollectionEntryListFragment extends GalleryFragment {

    public static final String TAG = "CollectionEntryListFragment";

    private static final String COLLECTION_ID = "CollectionId";

    public static final int REQUEST_CODE = 1;
    public static final String RESULT = "Result";

    private Collection collection;

    public CollectionEntryListFragment() {

    }

    public static CollectionEntryListFragment newInstance(long collectionId) {
        Bundle args = new Bundle();
        args.putLong(COLLECTION_ID, collectionId);

        CollectionEntryListFragment fragment = new CollectionEntryListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long collectionId = getArguments().getLong(COLLECTION_ID);
        collection = CollectionManager.getInstance().get(collectionId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEmptyText(R.string.collection_entries_empty);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_menu_add:
                AlbumPickerFragment fragment = AlbumPickerFragment.newInstance();
                fragment.setTargetFragment(this, REQUEST_CODE);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_container, fragment, AlbumPickerFragment.TAG)
                        .addToBackStack(TAG)
                        .commit();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected ListAdapter createAdapter() {
        return new GalleryObjectAdapter(getActivity(), collection);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    String[] selectedPaths = data.getStringArrayExtra(RESULT);
                    BusProvider.getInstance().post(new EntriesInsertedEvent(collection,
                                                                            selectedPaths));
                }
                break;
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.collection_entry_list_menu, menu);

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
            case R.id.menu_select_all:
                int count = adapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (!gridView.isItemChecked(i)) {
                        gridView.setItemChecked(i, true);
                    }
                }

                return true;

            case R.id.menu_edit:
                long[] entryIds = new long[gridView.getCheckedItemCount()];
                SparseBooleanArray checkedItemPositions = gridView.getCheckedItemPositions();
                for (int i = 0, j = 0; i < checkedItemPositions
                        .size() && j < entryIds.length; i++) {
                    if (checkedItemPositions.valueAt(i)) {
                        Entry itemData = (Entry) gridView
                                .getItemAtPosition(checkedItemPositions.keyAt(i));
                        entryIds[j] = itemData.getId();
                        j++;
                    }
                }
                Fragment fragment = CollectionEntryEditFragment
                        .newInstance(collection.getId(), entryIds);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_container, fragment, CollectionEntryEditFragment.TAG)
                        .addToBackStack(null).commit();
                mode.finish();

                return true;

            case R.id.menu_remove:
                long[] checkedIds = gridView.getCheckedItemIds();
                EntryFactory.getInstance().delete(collection, checkedIds);

                ((BaseAdapter) adapter).notifyDataSetChanged();
                mode.finish();

                return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
