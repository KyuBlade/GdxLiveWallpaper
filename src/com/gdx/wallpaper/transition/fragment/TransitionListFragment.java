package com.gdx.wallpaper.transition.fragment;

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
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.Pageable;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionEditEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionRemovedEvent;
import com.gdx.wallpaper.setting.ui.dialog.transition.TransitionTypeChoiceDialog;
import com.gdx.wallpaper.setting.ui.dialog.transition.TransitionRemoveDialog;
import com.gdx.wallpaper.transition.Transition;
import com.squareup.otto.Subscribe;

public class TransitionListFragment extends ListFragment implements Pageable {

    public static final String TAG = "TransitionListFragment";
    public static final String TRANSITION_ID = "TransitionId";

    private static final String SELECT_MODE = "SelectMode";

    private TransitionAdapter adapter;
    private boolean selectMode;

    public static TransitionListFragment newInstance(boolean selectMode) {
        Bundle args = new Bundle();
        args.putBoolean(SELECT_MODE, selectMode);

        TransitionListFragment fragment = new TransitionListFragment();
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

        adapter = new TransitionAdapter(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEmptyText(getString(R.string.transition_empty));
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
                TransitionTypeChoiceDialog
                        .newInstance().show(
                        getFragmentManager(), TransitionTypeChoiceDialog.TAG);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (!getUserVisibleHint()) {
            return false;
        }

        switch (item.getItemId()) {
            case R.id.floating_menu_edit:
                AdapterView.AdapterContextMenuInfo
                        listItem =
                        (AdapterView.AdapterContextMenuInfo) item
                                .getMenuInfo();
                TransitionAdapter adapter = (TransitionAdapter) getListAdapter();
                Transition transition = adapter.getItem(listItem.position);
                BusProvider.getInstance().post(new TransitionEditEvent(transition));

                return true;

            case R.id.floating_menu_remove:
                listItem = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                adapter = (TransitionAdapter) getListAdapter();
                transition = adapter.getItem(listItem.position);

                TransitionRemoveDialog.newInstance(transition.getId()).show(getFragmentManager(),
                                                                            TransitionRemoveDialog.TAG);

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
        result.putExtra(TRANSITION_ID, id);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
    }

    @Override
    public int getTitle() {
        return R.string.transition_page_name;
    }

    @Subscribe
    public void transitionAdded(TransitionCreatedEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void transitionChanged(TransitionChangedEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void removeTransition(TransitionRemovedEvent event) {
        adapter.notifyDataSetChanged();
    }
}