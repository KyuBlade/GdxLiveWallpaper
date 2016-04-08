package com.gdx.wallpaper.setting.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.gdx.wallpaper.setting.ui.Model;
import com.gdx.wallpaper.setting.fragment.adapter.control.EditControlType;
import com.gdx.wallpaper.setting.fragment.adapter.control.holder.EditControlHolder;

public class AbstractShaderEditAdapter extends RecyclerView.Adapter {

    private final Model model;

    public AbstractShaderEditAdapter(Model model) {
        this.model = model;
    }

    @Override
    public int getItemViewType(int position) {
        return model.get(position).getType().ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (EditControlType.values().length - 1 < viewType) {
            throw new IllegalStateException(
                    "View type " + viewType + " not implemented in EditControlType.");
        }
        return EditControlType.values()[viewType].createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        model.get(position).bind((EditControlHolder) holder);
    }

    @Override
    public int getItemCount() {
        return model.size;
    }
}