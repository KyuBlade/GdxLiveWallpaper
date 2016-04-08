package com.gdx.wallpaper.transition.fragment.shader;

import android.os.Bundle;

import com.gdx.wallpaper.setting.fragment.AbstractPreviewFragment;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;

public class TransitionPreviewFragment
        extends AbstractPreviewFragment<TransitionShaderPreviewApplication> {

    private static final String TRANSITION_ID = "TransitionId";

    public static TransitionPreviewFragment newInstance(long transitionId) {
        Bundle args = new Bundle();
        args.putLong(TRANSITION_ID, transitionId);

        TransitionPreviewFragment fragment = new TransitionPreviewFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected TransitionShaderPreviewApplication initApplication() {
        long transId = getArguments().getLong(TRANSITION_ID);
        Transition transition = TransitionManager.getInstance().get(transId);
        return new TransitionShaderPreviewApplication(transition);
    }
}