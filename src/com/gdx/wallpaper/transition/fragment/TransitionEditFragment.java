package com.gdx.wallpaper.transition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;

public abstract class TransitionEditFragment<T extends Transition> extends Fragment {

    private static final String TRANSITION_ID = "TransitionId";

    protected T transition;

    public TransitionEditFragment() {
    }

    public static TransitionEditFragment newInstance(Transition transition) {
        Bundle args = new Bundle();
        args.putLong(TRANSITION_ID, transition.getId());

        TransitionEditFragment fragment = null;
        try {
            fragment = transition.getType().getEditFragmentClass().newInstance();
            fragment.setArguments(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long transitionId = getArguments().getLong(TRANSITION_ID);
        transition = (T) TransitionManager.getInstance().get(transitionId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    public abstract int getLayout();
}
