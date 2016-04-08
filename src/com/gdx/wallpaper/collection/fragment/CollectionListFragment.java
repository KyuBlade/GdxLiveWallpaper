package com.gdx.wallpaper.collection.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.Pageable;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionEditEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionRemovedEvent;
import com.gdx.wallpaper.setting.ui.dialog.collection.CollectionRemoveDialog;
import com.squareup.otto.Subscribe;

public class CollectionListFragment extends ListFragment implements Pageable {

    public static final String TAG = "CollectionListFragment";
    public static final String COLLECTION_ID = "CollectionId";

    private static final String SELECT_MODE = "SelectMode";

    private CollectionAdapter adapter;
    private boolean selectMode;

    public static CollectionListFragment newInstance(boolean selectMode) {
        Bundle args = new Bundle();
        args.putBoolean(SELECT_MODE, selectMode);

        CollectionListFragment fragment = new CollectionListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            selectMode = getArguments().getBoolean(SELECT_MODE);
        }

        adapter = new CollectionAdapter(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEmptyText(getString(R.string.collection_empty));
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        registerForContextMenu(getListView());
        BusProvider.getInstance().register(this);

        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.show();
        activity.supportInvalidateOptionsMenu();

    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterForContextMenu(getListView());
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.settings_floating_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_menu_add:
                BusProvider.getInstance().post(new CollectionEditEvent(-1));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        if (!getUserVisibleHint()) {
            return false;
        }

        switch (item.getItemId()) {
            case R.id.floating_menu_edit:
                AdapterView.AdapterContextMenuInfo
                        listItem =
                        (AdapterView.AdapterContextMenuInfo) item
                                .getMenuInfo();
                Collection collection = adapter.getItem(listItem.position);
                BusProvider.getInstance().post(new CollectionEditEvent(collection.getId()));

                return true;

            case R.id.floating_menu_remove:
                listItem = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                collection = adapter.getItem(listItem.position);

                CollectionRemoveDialog.newInstance(collection.getId()).show(getFragmentManager(),
                                                                            CollectionRemoveDialog.TAG);

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (!selectMode) {
            return;
        }

        Intent result = new Intent();
        result.putExtra(COLLECTION_ID, id);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
    }

    @Override
    public int getTitle() {
        return R.string.collection_page_name;
    }

    @Subscribe
    public void addCollection(CollectionCreatedEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void removeCollection(CollectionRemovedEvent event) {
        adapter.notifyDataSetChanged();
    }
}