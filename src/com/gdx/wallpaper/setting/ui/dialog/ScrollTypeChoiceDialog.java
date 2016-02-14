package com.gdx.wallpaper.setting.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.playlist.PlaylistManager;
import com.gdx.wallpaper.playlist.ScrollType;
import com.gdx.wallpaper.setting.database.operation.PlaylistScrollTypeUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistChangedEvent;

public class ScrollTypeChoiceDialog extends DialogFragment {

    private static final String PLAYLIST_ID = "PlaylistId";
    private static final String SELECTED_INDEX = "SelectedIndex";

    private final ScrollType[] items;
    private int selectedIndex;

    public ScrollTypeChoiceDialog() {
        items = ScrollType.values();
    }

    public static ScrollTypeChoiceDialog newInstance(long playlistId, int selectedIndex) {
        Bundle args = new Bundle();
        args.putLong(PLAYLIST_ID, playlistId);
        args.putInt(SELECTED_INDEX, selectedIndex);

        ScrollTypeChoiceDialog fragment = new ScrollTypeChoiceDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        selectedIndex = getArguments().getInt(SELECTED_INDEX);
        String[] names = new String[items.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = getActivity().getString(items[i].getNameRes());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_playlist_edit_scroll_title)
                .setSingleChoiceItems(names, selectedIndex, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex = which;
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                ScrollType type = items[selectedIndex];
                long playlistId = getArguments().getLong(PLAYLIST_ID);
                Playlist playlist = PlaylistManager.getInstance().get(playlistId);
                playlist.setScrollType(type);
                BusProvider.getInstance().post(new PlaylistChangedEvent(playlistId,
                                                                        new PlaylistScrollTypeUpdateOperation()));
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return builder.create();
    }
}