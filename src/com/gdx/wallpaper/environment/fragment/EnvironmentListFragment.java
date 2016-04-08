package com.gdx.wallpaper.environment.fragment;

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
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.setting.Pageable;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentChangedEvent;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentEditEvent;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentRemoveEvent;
import com.gdx.wallpaper.setting.ui.dialog.environment.EnvironmentRemoveDialog;
import com.gdx.wallpaper.setting.ui.dialog.environment.EnvironmentTypeChoiceDialog;
import com.squareup.otto.Subscribe;

public class EnvironmentListFragment extends ListFragment implements Pageable {

    public static final String TAG = "EnvironmentListFragment";

    public static final String ENVIRONMENT_ID = "EnvironmentId";

    private static final String SELECT_MODE = "SelectMode";

    private EnvironmentAdapter adapter;
    private boolean selectMode;

    public EnvironmentListFragment() {
    }

    public static EnvironmentListFragment newInstance(boolean selectMode) {
        Bundle args = new Bundle();
        args.putBoolean(SELECT_MODE, selectMode);

        EnvironmentListFragment fragment = new EnvironmentListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new EnvironmentAdapter(getActivity());
        Bundle args = getArguments();
        if (args != null) {
            selectMode = getArguments().getBoolean(SELECT_MODE);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEmptyText(getString(R.string.environment_empty));
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
                EnvironmentTypeChoiceDialog.newInstance().show(getFragmentManager(), TAG);

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
                Environment environment = adapter.getItem(listItem.position);
                BusProvider.getInstance().post(new EnvironmentEditEvent(environment.getId()));

                return true;

            case R.id.floating_menu_remove:
                listItem = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                environment = adapter.getItem(listItem.position);
                EnvironmentRemoveDialog.newInstance(environment.getId()).show(getFragmentManager(),
                                                                              EnvironmentRemoveDialog.TAG);

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
        result.putExtra(ENVIRONMENT_ID, id);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, result);
    }

    @Override
    public int getTitle() {
        return R.string.environment_page_name;
    }

    @Subscribe
    public void addEnvironment(EnvironmentCreatedEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void removeEnvironment(EnvironmentRemoveEvent event) {
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void changeEnvironment(EnvironmentChangedEvent event) {
        adapter.notifyDataSetChanged();
    }
}