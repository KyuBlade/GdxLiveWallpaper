package com.gdx.wallpaper.setting.ui;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.playlist.PlaylistManager;
import com.gdx.wallpaper.setting.database.operation.PlaylistActiveUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistChangedEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistStateChangedEvent;

public class PlaylistSwitchChangeListener implements CompoundButton.OnCheckedChangeListener {

    private ListView listView;
    private View convertView;

    public PlaylistSwitchChangeListener(ListView listView, View convertView) {
        this.listView = listView;
        this.convertView = convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = listView.getPositionForView(convertView);

        if (isChecked) { // Activating a new playlist
            Playlist currentPlaylist = PlaylistManager.getInstance()
                    .getActive(); // Get the current active playlist
            if (currentPlaylist != null) {
                currentPlaylist.setActive(false); // deactivate the current activated playlist
                BusProvider.getInstance().post(new PlaylistChangedEvent(
                        currentPlaylist.getId(),
                        new PlaylistActiveUpdateOperation()));
            }
        }

        Playlist newPlaylist = (Playlist) listView.getAdapter().getItem(position);
        newPlaylist.setActive(isChecked);
        BusProvider.getInstance()
                .post(new PlaylistChangedEvent(newPlaylist.getId(),
                                               new PlaylistActiveUpdateOperation()));

        BusProvider.getInstance().post(new PlaylistStateChangedEvent((RadioButton) buttonView));
    }
}
