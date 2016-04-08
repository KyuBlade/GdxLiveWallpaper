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
import com.gdx.wallpaper.environment.Environment;
import com.gdx.wallpaper.environment.EnvironmentManager;
import com.gdx.wallpaper.environment.fragment.EnvironmentEditFragment;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.playlist.PlaylistManager;
import com.gdx.wallpaper.playlist.fragment.PlaylistEditFragment;
import com.gdx.wallpaper.setting.database.DatabaseHelper;
import com.gdx.wallpaper.setting.database.operation.UpdateOperation;
import com.gdx.wallpaper.setting.database.operation.playlist.PlaylistActiveUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.playlist.PlaylistCollectionUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.playlist.PlaylistNameUpdateOperation;
import com.gdx.wallpaper.setting.database.operation.playlist.PlaylistTransitionUpdateOperation;
import com.gdx.wallpaper.setting.eventbus.BusProvider;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionChangedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionEditEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionEntryChangedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.CollectionRemovedEvent;
import com.gdx.wallpaper.setting.eventbus.collection.EntriesInsertedEvent;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentChangedEvent;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentEditEvent;
import com.gdx.wallpaper.setting.eventbus.environment.EnvironmentRemoveEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistChangedEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistEditEvent;
import com.gdx.wallpaper.setting.eventbus.playlist.PlaylistRemoveEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionChangedEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionCreatedEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionEditEvent;
import com.gdx.wallpaper.setting.eventbus.transition.TransitionRemovedEvent;
import com.gdx.wallpaper.setting.fragment.MainFragment;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;
import com.gdx.wallpaper.transition.fragment.TransitionEditFragment;
import com.gdx.wallpaper.wallpaper.UpdateReceiver;
import com.squareup.otto.Subscribe;

public class WallpaperSettingActivity extends AppCompatActivity {

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
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_container, mainFragment, MainFragment.TAG).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        pauseWallpaperService();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        resumeWallpaperService();
        BusProvider.getInstance().unregister(this);
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
    public void createEnvironment(EnvironmentCreatedEvent event) {
        try {
            Environment newEnvironment = event.getType().getTypeClass().newInstance();
            EnvironmentManager.getInstance().insert(newEnvironment);

            BusProvider.getInstance().post(new EnvironmentEditEvent(newEnvironment.getId()));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void changeEnvironment(EnvironmentChangedEvent event) {
        long environmentId = event.getEnvironmentId();
        UpdateOperation updateOperation = event.getUpdateOperation();
        Environment environment = EnvironmentManager.getInstance().get(environmentId);
        EnvironmentManager.getInstance().update(environment, updateOperation);
        if (!(updateOperation instanceof PlaylistNameUpdateOperation)) {
            updateWallpaperService();
        }
    }

    @Subscribe
    public void editEnvironment(EnvironmentEditEvent event) {
        long environmentId = event.getEnvironmentId();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container,
                                                               EnvironmentEditFragment.newInstance(
                                                                       environmentId))
                .addToBackStack(null).commit();
    }

    @Subscribe
    public void removeEnvironment(EnvironmentRemoveEvent event) {
        EnvironmentManager.getInstance().remove(event.getId());

        updateWallpaperService();
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
                                                               TransitionEditFragment
                                                                       .newInstance(transition))
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
        EntryFactory.getInstance().insert(entries);
        collection.addAll(entries);

        updateWallpaperService();
    }

    private void updateWallpaperService() {
        Intent intent = new Intent(UpdateReceiver.TAG);
        intent.putExtra(UpdateReceiver.ACTION, UpdateReceiver.Action.UPDATE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void pauseWallpaperService() {
        Intent intent = new Intent(UpdateReceiver.TAG);
        intent.putExtra(UpdateReceiver.ACTION, UpdateReceiver.Action.PAUSE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void resumeWallpaperService() {
        Intent intent = new Intent(UpdateReceiver.TAG);
        intent.putExtra(UpdateReceiver.ACTION, UpdateReceiver.Action.RESUME);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}