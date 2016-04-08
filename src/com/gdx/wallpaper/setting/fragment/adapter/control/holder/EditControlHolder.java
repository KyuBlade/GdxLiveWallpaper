package com.gdx.wallpaper.setting.fragment.adapter.control.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gdx.wallpaper.R;

public class EditControlHolder extends RecyclerView.ViewHolder {

    public TextView title;

    public EditControlHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
    }
}