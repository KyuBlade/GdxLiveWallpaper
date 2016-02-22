package com.gdx.wallpaper.environment;

public class Environment {

    private long id;
    private String name;
    private EnvironmentType type = EnvironmentType.NONE;
    private int screenCount = 1;

    public Environment() {

    }

    public Environment(long id, String name, EnvironmentType type, int screenCount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.screenCount = screenCount;
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

    public EnvironmentType getType() {
        return type;
    }

    public void setType(EnvironmentType type) {
        this.type = type;
    }

    public int getScreenCount() {
        return screenCount;
    }

    public void setScreenCount(int screenCount) {
        this.screenCount = screenCount;
    }
}