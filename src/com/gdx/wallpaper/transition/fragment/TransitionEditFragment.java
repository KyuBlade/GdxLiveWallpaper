package com.gdx.wallpaper.transition.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.setting.ui.dialog.transition.TransitionNameEditDialog;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;
import com.gdx.wallpaper.transition.fragment.shader.TransitionShaderEditFragment;
import com.squareup.otto.Subscribe;

public class TransitionEditFragment extends ListFragment {

    public static final String TAG = "TransitionEditFragment";

    private static final String TRANSITION_ID = "TransitionId";

    private Transition transition;
    private TransitionEditAdapter adapter;

    public TransitionEditFragment() {
    }

    public static TransitionEditFragment newInstance(Transition transition) {
        Bundle args = new Bundle();
        args.putLong(TRANSITION_ID, transition.getId());

        TransitionEditFragment fragment = new TransitionEditFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long transitionId = getArguments().getLong(TRANSITION_ID);
        transition = TransitionManager.getInstance().get(transitionId);

        adapter = new TransitionEditAdapter(getActivity(), transition);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, final View v, int position, long id) {
        switch (position) {
            case TransitionEditAdapter.NAME:
                TransitionNameEditDialog
                        .newInstance(transition.getId(), transition.getName()).show(
                        getFragmentManager(), TransitionNameEditDialog.TAG);
                break;

            case TransitionEditAdapter.TYPE:
                break;

            case TransitionEditAdapter.MANAGE:
                getFragmentManager().beginTransaction().replace(R.id.content_container,
                                                                TransitionShaderEditFragment
                                                                        .newInstance(transition
                                                                                             .getId()))
                        .addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void transitionChanged(TransitionChangedEvent event) {
        adapter.notifyDataSetChanged();
    }
}
