package com.gdx.wallpaper.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gdx.wallpaper.R;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.collection.entry.Entry;
import com.gdx.wallpaper.collection.entry.EntryFactory;
import com.gdx.wallpaper.collection.fragment.CollectionEditFragment;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.playlist.PlaylistManager;
import com.gdx.wallpaper.playlist.fragment.PlaylistEditFragment;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.PlaylistActiveUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.PlaylistCollectionUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.PlaylistNameUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.PlaylistTransitionUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionChangedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionEditEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionEntryChangedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionRemovedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.EntriesInsertedEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistChangedEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistEditEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistRemoveEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionEditEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionRemovedEvent;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;
import com.gdx.wallpaper.transition.fragment.TransitionGlobalEditFragment;
import com.gdx.wallpaper.wallpaper.UpdateReceiver;
import com.squareup.otto.Subscribe;

public class WallpaperSettingActivity extends AppCompatActivity {

    private EntryFactory entryFactory;

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHelper.initInstance(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        setContentView(R.layout.content);

        if (savedInstanceState == null) {
            entryFactory = EntryFactory.getInstance();

            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_container, mainFragment, MainFragment.TAG).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe
    public void changePlaylist(PlaylistChangedEvent event) {
        long playlistId = event.getPlaylistId();
        UpdateOperation updateOperation = event.getUpdateOperation();
        Playlist playlist = PlaylistManager.getInstance().get(playlistId);
        PlaylistManager.getInstance().update(playlist, updateOperation);
        if (playlist.isActive()) {
            if (!(updateOperation instanceof PlaylistNameUpdateOperation)) {
                updateWallpaperService();
            }
        }
    }

    @Subscribe
    public void editPlaylist(PlaylistEditEvent event) {
        long playlistId = event.getPlaylistId();
        if (playlistId == -1) {
            Playlist playlist = new Playlist();
            PlaylistManager.getInstance().insert(playlist);
            playlistId = playlist.getId();

            BusProvider.getInstance().post(new PlaylistCreatedEvent());
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content_container,
                                                               PlaylistEditFragment.newInstance(
                                                                       playlistId))
                .addToBackStack(null).commit();
    }

    @Subscribe
    public void removePlaylist(PlaylistRemoveEvent event) {
        PlaylistManager.getInstance().remove(event.getId());

        if (PlaylistManager.getInstance().isActive(event.getId())) {
            updateWallpaperService();
        }
    }

    @Subscribe
    public void createTransition(TransitionCreatedEvent event) {
        try {
            Transition newTransition = event.getType().getTransitionClass().newInstance();
            TransitionManager.getInstance().insert(newTransition);

            BusProvider.getInstance().post(new TransitionEditEvent(newTransition));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void editTransition(TransitionEditEvent event) {
        Transition transition = event.getTransition();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container,
                                                               TransitionGlobalEditFragment
                                                                       .newInstance(
                                                                               transition.getId()))
                .addToBackStack(null).commit();
    }

    @Subscribe
    public void changeTransition(TransitionChangedEvent event) {
        UpdateOperation updateOperation = event.getUpdateOperation();
        TransitionManager.getInstance().update(event.getTransitionId(), updateOperation);

        Playlist activePlaylist = PlaylistManager.getInstance().getActive();
        if (activePlaylist != null && activePlaylist.getTransitionId() == event.getTransitionId()) {
            updateWallpaperService();
        }
    }

    @Subscribe
    public void removeTransition(TransitionRemovedEvent event) {
        long transitionId = event.getTransitionId();

        Playlist[] playlists = PlaylistManager.getInstance().getAll();
        for (Playlist playlist : playlists) {
            if (playlist.getTransitionId() == event.getTransitionId()) {
                playlist.setTransitionId(-1);

                BusProvider.getInstance().post(
                        new PlaylistChangedEvent(playlist.getId(),
                                                 new PlaylistTransitionUpdateOperation()));

                playlist.setActive(false);
                BusProvider.getInstance().post(
                        new PlaylistChangedEvent(playlist.getId(),
                                                 new PlaylistActiveUpdateOperation()));

                Toast.makeText(this, R.string.transition_remove_deactivate, Toast.LENGTH_LONG)
                        .show();
            }
        }

        TransitionManager.getInstance().remove(transitionId);
    }

    @Subscribe
    public void editCollection(CollectionEditEvent event) {
        long collectionId = event.getCollectionId();
        if (collectionId == -1) {
            Collection collection = new Collection();
            CollectionManager.getInstance().insert(collection);
            collectionId = collection.getId();

            BusProvider.getInstance().post(new CollectionCreatedEvent());
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.content_container,
                                                               CollectionEditFragment
                                                                       .newInstance(
                                                                               collectionId))
                .addToBackStack(null).commit();
    }

    /**
     * Called when collection name change
     *
     * @param event
     */
    @Subscribe
    public void changeCollection(CollectionChangedEvent event) {
        CollectionManager.getInstance().update(event.getCollection(), event.getUpdateOperation());
    }

    @Subscribe
    public void removeCollection(CollectionRemovedEvent event) {
        Playlist[] playlists = PlaylistManager.getInstance().getAll();
        for (Playlist playlist : playlists) {
            if (playlist.getCollectionId() == event.getCollectionId()) {
                playlist.setCollectionId(-1);

                BusProvider.getInstance().post(
                        new PlaylistChangedEvent(playlist.getId(),
                                                 new PlaylistCollectionUpdateOperation()));

                if (playlist.isActive()) {
                    playlist.setActive(false);
                    BusProvider.getInstance().post(
                            new PlaylistChangedEvent(playlist.getId(),
                                                     new PlaylistActiveUpdateOperation()));
                    Toast.makeText(this, R.string.collection_remove_deactivate,
                                   Toast.LENGTH_LONG).show();
                }
            }
        }

        CollectionManager.getInstance().remove(event.getCollectionId());
    }

    @Subscribe
    public void changeCollectionEntries(CollectionEntryChangedEvent event) {
        EntryFactory.getInstance().update(event.getEntry());

        Playlist activePlaylist = PlaylistManager.getInstance().getActive();
        if (activePlaylist != null && activePlaylist.getCollectionId() == event.getEntry()
                .getCollectionId()) {
            updateWallpaperService();
        }
    }

    @Subscribe
    public void insertEntries(EntriesInsertedEvent event) {
        Collection collection = event.getCollection();
        String[] imagePaths = event.getEntries();
        Entry[] entries = new Entry[imagePaths.length];
        for (int i = 0; i < imagePaths.length; i++) {
            String path = imagePaths[i];
            Entry entry = new Entry();
            entry.setCollectionId(collection.getId());
            entry.setImagePath(path);
            entries[i] = entry;
        }
        entryFactory.insert(entries);
        collection.addAll(entries);

        updateWallpaperService();
    }

    private void updateWallpaperService() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(UpdateReceiver.TAG));
    }
}