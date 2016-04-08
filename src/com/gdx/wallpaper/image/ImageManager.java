package com.gdx.wallpaper.image;

import android.util.Log;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.gdx.wallpaper.collection.Collection;
import com.gdx.wallpaper.collection.CollectionManager;
import com.gdx.wallpaper.collection.entry.Entry;
import com.gdx.wallpaper.playlist.Playlist;
import com.gdx.wallpaper.transition.Transition;
import com.gdx.wallpaper.transition.TransitionManager;
import com.gdx.wallpaper.wallpaper.eventbus.BusProvider;
import com.gdx.wallpaper.wallpaper.eventbus.ImageLoadedEvent;

public class ImageManager implements Disposable {

    private final AssetManager assetManager;

    private Playlist playlist;
    private int lastIndex;

    public ImageManager() {
        assetManager = new AssetManager(new AbsoluteFileHandleResolver());
    }

    public ManagedImage obtain(boolean backward) {
        Transition transition = TransitionManager.getInstance().get(playlist.getTransitionId());
        boolean random = transition.isRandom();
        Collection collection = CollectionManager.getInstance().get(playlist.getCollectionId());
        int index;
        if (random) {
            do {
                index = MathUtils.random(collection.size() - 1);
            } while (index != lastIndex);
        } else if (backward) {
            index = lastIndex - 1;
            if (index < 0) {
                index = collection.size() - 1;
            }
        } else {
            index = lastIndex + 1;
            if (index >= collection.size()) {
                index = 0;
            }
        }

        lastIndex = index;

        Entry entry = collection.valueAt(index);
        ManagedImage managedImage = new ManagedImage();
        loadImage(entry, managedImage);

        return managedImage;
    }

    private void loadImage(final Entry entry, final ManagedImage managedImage) {
        managedImage.setState(ManagedImage.State.LOADING);
        LoadedCallback callback = new LoadedCallback() {

            @Override
            public void finishedLoading(AssetManager assetManager, String fileName,
                                        Class type) {
                Texture texture = assetManager.get(fileName, Texture.class);
                managedImage.setup(entry, texture);
                managedImage.setState(ManagedImage.State.LOADED);

                BusProvider.getInstance().post(new ImageLoadedEvent(managedImage));
            }
        };

        String path = entry.getImagePath();
        TextureLoader.TextureParameter params = new TextureLoader.TextureParameter();
        params.textureData = new WallpaperTextureData(path);
        params.loadedCallback = callback;
        AssetDescriptor<Texture> textureDescriptor = new AssetDescriptor<>(path,
                                                                           Texture.class,
                                                                           params);
        Log.i("ImageManager", "Loading : " + path);
        managedImage.setAssetDescriptor(textureDescriptor);
        assetManager.load(textureDescriptor);
    }

    public void free(ManagedImage image) {
        if (image == null) {
            return;
        }

        AssetDescriptor<Texture> assetDescriptor = image.getAssetDescriptor();
        if (assetDescriptor == null) {
            return;
        }

        assetManager.unload(assetDescriptor.fileName);
    }

    public void update() {
        assetManager.update();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}