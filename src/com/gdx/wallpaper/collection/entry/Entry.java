package com.gdx.wallpaper.collection.entry;

import com.badlogic.gdx.utils.Scaling;

public class Entry {

    private long id;
    private long collectionId;
    private int landscapeOffsetX;
    private int landscapeOffsetY;
    private float landscapeZoom = 1.0f;
    private float landscapeRotation;
    private int portraitOffsetX;
    private int portraitOffsetY;
    private float portraitZoom = 1.0f;
    private float portraitRotation;
    private Scaling scalingType = Scaling.fill;
    private String imagePath;

    public Entry() {

    }

    public Entry(Entry entry) {
        this(entry.id, entry.collectionId,
             entry.landscapeOffsetX, entry.landscapeOffsetY, entry.landscapeZoom,
             entry.landscapeRotation, entry.portraitOffsetX, entry.portraitOffsetY,
             entry.portraitZoom, entry.portraitRotation, entry.scalingType, entry.imagePath);
    }

    public Entry(long id, long collectionId, int landscapeOffsetX, int landscapeOffsetY,
                 float landscapeZoom, float landscapeRotation, int portraitOffsetX,
                 int portraitOffsetY, float portraitZoom,
                 float portraitRotation, Scaling scalingType, String imagePath) {
        this.id = id;
        this.collectionId = collectionId;
        this.landscapeOffsetX = landscapeOffsetX;
        this.landscapeOffsetY = landscapeOffsetY;
        this.landscapeZoom = landscapeZoom;
        this.landscapeRotation = landscapeRotation;
        this.portraitOffsetX = portraitOffsetX;
        this.portraitOffsetY = portraitOffsetY;
        this.portraitZoom = portraitZoom;
        this.portraitRotation = portraitRotation;
        this.scalingType = scalingType;
        this.imagePath = imagePath;
    }

    public void set(Entry entry) {
        this.id = entry.id;
        this.collectionId = entry.collectionId;
        this.landscapeOffsetX = entry.landscapeOffsetX;
        this.landscapeOffsetY = entry.landscapeOffsetY;
        this.landscapeZoom = entry.landscapeZoom;
        this.landscapeRotation = entry.landscapeRotation;
        this.portraitOffsetX = entry.portraitOffsetX;
        this.portraitOffsetY = entry.portraitOffsetY;
        this.portraitZoom = entry.portraitZoom;
        this.portraitRotation = entry.portraitRotation;
        this.scalingType = entry.scalingType;
        this.imagePath = entry.imagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public int getLandscapeOffsetX() {
        return landscapeOffsetX;
    }

    public void setLandscapeOffsetX(int landscapeOffsetX) {
        this.landscapeOffsetX = landscapeOffsetX;
    }

    public int getLandscapeOffsetY() {
        return landscapeOffsetY;
    }

    public void setLandscapeOffsetY(int landscapeOffsetY) {
        this.landscapeOffsetY = landscapeOffsetY;
    }

    public float getLandscapeZoom() {
        return landscapeZoom;
    }

    public void setLandscapeZoom(float landscapeZoom) {
        this.landscapeZoom = landscapeZoom;
    }

    public float getLandscapeRotation() {
        return landscapeRotation;
    }

    public void setLandscapeRotation(float landscapeRotation) {
        this.landscapeRotation = landscapeRotation % 360;
    }

    public void addLandscapeRotation(float rotation) {
        setLandscapeRotation((landscapeRotation + rotation) % 360);
    }

    public int getPortraitOffsetX() {
        return portraitOffsetX;
    }

    public void setPortraitOffsetX(int portraitOffsetX) {
        this.portraitOffsetX = portraitOffsetX;
    }

    public int getPortraitOffsetY() {
        return portraitOffsetY;
    }

    public void setPortraitOffsetY(int portraitOffsetY) {
        this.portraitOffsetY = portraitOffsetY;
    }

    public float getPortraitZoom() {
        return portraitZoom;
    }

    public void setPortraitZoom(float portraitZoom) {
        this.portraitZoom = portraitZoom;
    }

    public float getPortraitRotation() {
        return portraitRotation;
    }

    public void setPortraitRotation(float portraitRotation) {
        this.portraitRotation = portraitRotation;
    }

    public void addPortraitRotation(float rotation) {
        setPortraitRotation((portraitRotation + rotation) % 360);
    }

    public Scaling getScalingType() {
        return scalingType;
    }

    public void setScalingType(Scaling scalingType) {
        this.scalingType = scalingType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        StringBuilder buidler = new StringBuilder();
        buidler.append("Entry[")
                .append("collectionId=").append(collectionId)
                .append(", landscapeOffsetX=").append(landscapeOffsetX)
                .append(", landscapeOffsetY=").append(landscapeOffsetY)
                .append(", landscapeZoom=").append(landscapeZoom)
                .append(", landscapeRotation=").append(landscapeRotation)
                .append(", portraitOffsetX=").append(portraitOffsetX)
                .append(", portraitOffsetY=").append(portraitOffsetY)
                .append(", portraitZoom=").append(portraitZoom)
                .append(", portraitRotation=").append(portraitRotation)
                .append(", imagePath=").append(imagePath)
                .append(']');

        return buidler.toString();
    }
}