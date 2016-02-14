package com.gdx.wallpaper.transition.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.setting.ui.dialog.TransitionNameEditDialog;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;
import com.squareup.otto.Subscribe;

public class TransitionGlobalEditFragment extends ListFragment {

    public static final String TAG = "TransitionGlobalEditFragment";

    public static final String TRANSITION_ID = "TransitionId";

    private TransitionEditAdapter adapter;
    protected Transition transition;

    public TransitionGlobalEditFragment() {
    }

    public static TransitionGlobalEditFragment newInstance(long transitionId) {
        Bundle args = new Bundle();
        args.putLong(TRANSITION_ID, transitionId);

        TransitionGlobalEditFragment fragment = new TransitionGlobalEditFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long transitionId = getArguments().getLong(TRANSITION_ID, -1);
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

            case TransitionEditAdapter.MANAGE:
                TransitionEditFragment _fragment = TransitionEditFragment.newInstance(transition);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_container, _fragment).addToBackStack(null)
                        .commit();

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
    public void changeTransition(TransitionChangedEvent event) {
        adapter.notifyDataSetChanged();
    }
}