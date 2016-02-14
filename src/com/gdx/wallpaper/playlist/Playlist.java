package com.gdx.wallpaper.playlist;

public class Playlist {

    private long id;
    private String name;
    private long transitionId = -1;
    private long collectionId = -1;
    private boolean scrollable = true;
    private ScrollType scrollType = ScrollType.NONE;
    private boolean active;

    public Playlist() {
    }

    public Playlist(long id, String name, long transition,
                    long collection, boolean scrollable, ScrollType scrollType, boolean active) {
        this.id = id;
        this.name = name;
        this.transitionId = transition;
        this.collectionId = collection;
        this.scrollable = scrollable;
        this.scrollType = scrollType;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(long transitionId) {
        this.transitionId = transitionId;
    }

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public ScrollType getScrollType() {
        return scrollType;
    }

    public void setScrollType(ScrollType scrollType) {
        this.scrollType = scrollType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Playlist[id=").append(id).append(", name=").append(name)
                .append(", transitionId=").append(transitionId).append(", collectionId=")
                .append(collectionId).append(", scrollable=").append(scrollable)
                .append(", scrollType=").append(scrollType).append(
                        ", active=").append(active).append(
                        ']').toString();
    }
}