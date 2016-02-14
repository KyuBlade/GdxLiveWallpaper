package com.gdx.wallpaper.setting.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gdx.wallpaper.R;

public abstract class GalleryFragment extends Fragment implements
                                                       AbsListView.MultiChoiceModeListener {

    private View view;
    protected ListAdapter adapter;
    protected GridView gridView;

    public GalleryFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.empty_gridview, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        TextView emptyView = (TextView) view.findViewById(R.id.empty);
        gridView.setEmptyView(emptyView);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = createAdapter();
        gridView.setAdapter(adapter);
        gridView.setMultiChoiceModeListener(this);
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
                                          boolean checked) {
        int count = gridView.getCheckedItemCount();
        String title = getActivity().getResources()
                .getQuantityString(R.plurals.selected_items_count, count,
                                   count);
        mode.setSubtitle(title);
    }

    protected abstract ListAdapter createAdapter();

    protected void setEmptyText(int stringRes) {
        TextView emptyView = (TextView) gridView.getEmptyView();
        emptyView.setText(stringRes);
        gridView.setEmptyView(emptyView);
    }
}