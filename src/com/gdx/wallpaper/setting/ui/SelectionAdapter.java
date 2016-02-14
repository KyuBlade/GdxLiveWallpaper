package com.gdx.wallpaper.setting.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class SelectionAdapter<VH extends SelectionAdapter.ViewHolder> extends
        RecyclerView.Adapter<VH> {

    protected RecyclerView recyclerView;
    protected int selectedItem = RecyclerView.NO_POSITION;

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        viewHolder.itemView.setSelected(selectedItem == position);
    }

    protected void setSelected(int position) {
        View firstChild = recyclerView.getChildAt(0);
        int firstChildPosition = recyclerView.getChildAdapterPosition(firstChild);

        if (selectedItem != RecyclerView.NO_POSITION) {
            View lastSelectedChild = recyclerView.getChildAt(selectedItem - firstChildPosition);
            if (lastSelectedChild != null) {
                lastSelectedChild.setSelected(false);
            }
        }

        selectedItem = position;
        View newSelectedChild = recyclerView.getChildAt(selectedItem - firstChildPosition);
        if (newSelectedChild != null) {
            newSelectedChild.setSelected(true);
        }
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(getAdapterPosition());
                }
            });
        }
    }
}