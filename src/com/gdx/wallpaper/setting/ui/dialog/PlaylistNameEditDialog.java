package com.gdx.wallpaper.setting.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.playlist.PlaylistManager;
import com.gdx.wallpaper.setting.database.operation.PlaylistNameUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistChangedEvent;

public class PlaylistNameEditDialog extends TextInputDialog {

    public static final String TAG = "PlaylistNameEditDialog";

    private static final String PLAYLIST_ID = "PlaylistId";

    private Playlist playlist;

    public PlaylistNameEditDialog() {
        super(R.string.dialog_playlist_edit_name_title, R.string.dialog_playlist_edit_name_message);
    }

    public static PlaylistNameEditDialog newInstance(long playlistId, String defaultName) {
        Bundle args = new Bundle();
        args.putLong(PLAYLIST_ID, playlistId);
        args.putString(DEFAULT_TEXT, defaultName);

        PlaylistNameEditDialog fragment = new PlaylistNameEditDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        long playlistId = args.getLong(PLAYLIST_ID);
        playlist = PlaylistManager.getInstance().get(playlistId);

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onPositive(String text) {
        playlist.setName(text);
        BusProvider.getInstance()
                .post(new PlaylistChangedEvent(playlist.getId(),
                                               new PlaylistNameUpdateOperation()));
    }
}