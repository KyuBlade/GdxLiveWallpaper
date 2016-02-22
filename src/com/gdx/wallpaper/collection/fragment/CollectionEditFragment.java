package com.gdx.wallpaper.collection.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionChangedEvent;
import com.gdx.wallpaper.setting.ui.dialog.collection.CollectionNameEditDialog;
import com.squareup.otto.Subscribe;

public class CollectionEditFragment extends ListFragment {

    public static final String TAG = "CollectionEditFragment";

    private static final String COLLECTION_ID = "CollectionId";

    public static final int NAME = 0;
    public static final int MANAGE = 1;

    private CollectionEditAdapter adapter;
    private Collection collection;

    public CollectionEditFragment() {
    }

    public static CollectionEditFragment newInstance(long collectionId) {
        Bundle args = new Bundle();
        args.putLong(COLLECTION_ID, collectionId);

        CollectionEditFragment fragment = new CollectionEditFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long collectionId = getArguments().getLong(COLLECTION_ID, -1);
        collection = CollectionManager.getInstance().get(collectionId);

        adapter = new CollectionEditAdapter(getActivity(), collection);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, final View v, int position, long id) {
        switch (position) {
            case NAME:
                CollectionNameEditDialog
                        .newInstance(collection.getId(), collection.getName()).show(
                        getFragmentManager(), CollectionNameEditDialog.TAG);
                break;

            case MANAGE:
                getFragmentManager().beginTransaction().replace(R.id.content_container,
                                                                CollectionEntryListFragment
                                                                        .newInstance(
                                                                                collection.getId()),
                                                                CollectionEntryListFragment.TAG)
                        .addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void changeCollection(CollectionChangedEvent event) {
        adapter.notifyDataSetChanged();
    }
}