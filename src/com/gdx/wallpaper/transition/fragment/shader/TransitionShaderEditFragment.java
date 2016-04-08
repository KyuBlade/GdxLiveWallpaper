package com.gdx.wallpaper.transition.fragment.shader;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gdx.wallpaper.setting.fragment.AbstractPreviewFragment;
import com.gdx.wallpaper.setting.fragment.AbstractShaderEditFragment;
import com.gdx.wallpaper.setting.fragment.AbstractShaderPreviewApplication;
import com.gdx.wallpaper.setting.ui.Model;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;
import com.gdx.wallpaper.transition.fragment.model.TransitionModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TransitionShaderEditFragment extends AbstractShaderEditFragment {

    private static final String TRANSITION_ID = "TransitionId";

    private Transition transition;
    private TransitionShaderEditAdapter adapter;
    private Model model;

    public TransitionShaderEditFragment() {
    }

    public static TransitionShaderEditFragment newInstance(long environmentId) {
        TransitionShaderEditFragment fragment = new TransitionShaderEditFragment();
        Bundle args = new Bundle();
        args.putLong(TRANSITION_ID, environmentId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long transitionId = getArguments().getLong(TRANSITION_ID);
        transition = TransitionManager.getInstance().get(transitionId);

        previewFragment = TransitionPreviewFragment.newInstance(transition.getId());
        previewFragment.setTargetFragment(previewFragment, 0);
        previewFragment.setListener(new AbstractPreviewFragment.OnLoadedListener() {
            @Override
            public void onLoaded(AbstractShaderPreviewApplication app) {
                try {
                    Class<? extends TransitionModel> clazz = transition.getType().getModelClass();
                    Constructor<? extends TransitionModel>
                            constructor =
                            clazz.getConstructor(TransitionShaderPreviewApplication.class,
                                                 transition.getClass());
                    model = constructor.newInstance(app, transition);
                    adapter = new TransitionShaderEditAdapter(model);
                    recyclerView.setAdapter(adapter);
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}