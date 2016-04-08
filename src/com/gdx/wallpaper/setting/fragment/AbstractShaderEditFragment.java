package com.gdx.wallpaper.setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.ui.DividerItemDecoration;

public abstract class AbstractShaderEditFragment extends Fragment {

    protected View view;
    protected RecyclerView recyclerView;
    protected AbstractPreviewFragment previewFragment;

    protected AbstractShaderEditFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.preview_edit_shader, container, false);
        previewFragment.setRadialProgress((ProgressBar) view.findViewById(R.id.radialProgress));
        getFragmentManager().beginTransaction()
                .add(R.id.previewContainer, previewFragment, AbstractPreviewFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        return view;
    }
}