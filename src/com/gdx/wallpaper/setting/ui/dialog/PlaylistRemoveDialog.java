package com.gdx.wallpaper.setting.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistRemoveEvent;

public class PlaylistRemoveDialog extends DialogFragment {

    public static final String TAG = "PlaylistRemoveDialog";

    private static final String PLAYLIST_ID = "PlaylistId";

    public PlaylistRemoveDialog() {
    }

    public static PlaylistRemoveDialog newInstance(long playlistId) {
        Bundle args = new Bundle();
        args.putLong(PLAYLIST_ID, playlistId);

        PlaylistRemoveDialog fragment = new PlaylistRemoveDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final long playlistId = args.getLong(PLAYLIST_ID, -1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_remove_title)
                .setMessage(R.string.dialog_remove_playlist_message)
                .setPositiveButton(R.string.ok, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BusProvider.getInstance().post(new PlaylistRemoveEvent(playlistId));
                    }
                }).setNegativeButton(R.string.cancel, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}