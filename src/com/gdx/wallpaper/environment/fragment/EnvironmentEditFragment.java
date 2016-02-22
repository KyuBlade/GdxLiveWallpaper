package com.gdx.wallpaper.environment.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentChangedEvent;
import com.gdx.wallpaper.setting.ui.dialog.environment.EnvironmentNameEditDialog;
import com.gdx.wallpaper.setting.ui.dialog.environment.EnvironmentScreenCountChoiceDialog;
import com.gdx.wallpaper.setting.ui.dialog.environment.EnvironmentTypeChoiceDialog;
import com.squareup.otto.Subscribe;

public class EnvironmentEditFragment extends ListFragment {

    public static final String TAG = "CollectionEditFragment";

    public static final String ENVIRONMENT_ID = "EnvironmentId";

    private EnvironmentEditAdapter adapter;
    private Environment environment;

    public EnvironmentEditFragment() {
    }

    public static EnvironmentEditFragment newInstance(long environmentId) {
        Bundle args = new Bundle();
        args.putLong(ENVIRONMENT_ID, environmentId);

        EnvironmentEditFragment fragment = new EnvironmentEditFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long environmentId = getArguments().getLong(ENVIRONMENT_ID, -1);
        if (environmentId != -1) {
            environment = EnvironmentManager.getInstance().get(environmentId);
        }

        adapter = new EnvironmentEditAdapter(getActivity(), environment);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, final View v, int position, long id) {
        switch (position) {
            case EnvironmentEditAdapter.NAME:
                EnvironmentNameEditDialog
                        .newInstance(environment.getId(), environment.getName()).show(
                        getFragmentManager(), EnvironmentNameEditDialog.TAG);
                break;

            case EnvironmentEditAdapter.TYPE:
                EnvironmentTypeChoiceDialog.newInstance(environment.getId(), environment.getType())
                        .show(getFragmentManager(), EnvironmentTypeChoiceDialog.TAG);

                break;

            case EnvironmentEditAdapter.SCREEN_COUNT:
                EnvironmentScreenCountChoiceDialog
                        .newInstance(environment.getId(), environment.getScreenCount())
                        .show(getFragmentManager(), EnvironmentScreenCountChoiceDialog.TAG);

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
    public void environmentChanged(EnvironmentChangedEvent event) {
        adapter.notifyDataSetChanged();
    }
}