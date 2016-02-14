package com.gdx.wallpaper.wallpaper.eventbus;

import com.gdx.wallpaper.image.ManagedImage;

public class ImageLoadedEvent {

    private ManagedImage image;

    public ImageLoadedEvent(ManagedImage image) {
        this.image = image;
    }

    public ManagedImage getImage() {
        return image;
    }
}
