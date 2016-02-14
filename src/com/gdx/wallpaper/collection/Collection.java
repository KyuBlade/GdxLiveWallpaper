package com.gdx.wallpaper.collection;

import android.support.v4.util.LongSparseArray;

import com.gdx.wallpaper.collection.entry.Entry;

public class Collection extends LongSparseArray<Entry> {

    private long id;
    private String name;

    public Collection() {
        super();
    }

    public Collection(long id, String name) {
        super();

        this.id = id;
        this.name = name;
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

    public void addEntry(long id, Entry entry) {
        put(id, entry);
    }

    public void addAll(Entry[] entries) {
        for (int i = 0; i < entries.length; i++) {
            Entry entry = entries[i];
            addEntry(entry.getId(), entry);
        }
    }

    public void removeEntry(long id) {
        remove(id);
    }

    public void removeEntries(long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            removeEntry(ids[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[id=").append(id).append(", name=")
                .append(name)
                .append(", entries=[");
        for (int i = 0; i < size(); i++) {
            Entry value = valueAt(i);
            builder.append(value.toString());
            if (i + 1 < size()) {
                builder.append(", ");
            }
        }

        return builder.append("]]").toString();
    }
}
