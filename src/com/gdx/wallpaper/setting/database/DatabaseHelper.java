package com.gdx.wallpaper.setting.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.badlogic.gdx.utils.Scaling;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "playlists.db";
    private static final int VERSION = 3;

    public static final String PLAYLIST_TABLE = "Playlist";
    public static final String ENTRY_TABLE = "Entry";
    public static final String TRANSITION_TABLE = "Transition";
    public static final String COLLECTION_TABLE = "Collection";

    public static class CommonColumns {

        public static final String ID = "ID";
    }

    public static class PlaylistColumns {

        public static final String NAME = "Name";
        public static final String TRANSITION_ID = "TransitionId";
        public static final String COLLECTION_ID = "CollectionId";
        public static final String SCROLLABLE = "Scrollable";
        public static final String SCROLL_TYPE = "ScrollType";
        public static final String ACTIVE = "Active";
    }

    public static class TransitionColumns {

        public static final String NAME = "Name";
        public static final String TYPE = "Type";
        public static final String RANDOM = "Random";
        public static final String DISPLAY_CYCLING_PROGRESS = "DisplayCyclingProgress";
        public static final String CYCLE_TIME = "CycleTime";
        public static final String FADE_TIME = "FadeTime";
    }

    public static class CollectionColumns {

        public static final String NAME = "Name";
    }

    public static class EntryColumns {

        public static final String COLLECTION_ID = "CollectionId";
        public static final String LANDSCAPE_OFFSET_X = "LandscapeOffsetX";
        public static final String LANDSCAPE_OFFSET_Y = "LandscapeOffsetY";
        public static final String LANDSCAPE_ZOOM = "LandscapeZoom";
        public static final String LANDSCAPE_ROTATION = "LandscapeRotation";
        public static final String PORTRAIT_OFFSET_X = "PortraitOffsetX";
        public static final String PORTRAIT_OFFSET_Y = "PortraitOffsetY";
        public static final String PORTRAIT_ZOOM = "PortraitZoom";
        public static final String PORTRAIT_ROTATION = "PortraitRotation";
        public static final String SCALE_TYPE = "ScaleType";
        public static final String IMAGE_PATH = "ImagePath";
    }

    private StringBuilder builder;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

        builder = new StringBuilder();
        readableDatabase = super.getReadableDatabase();
        writableDatabase = super.getWritableDatabase();
    }

    public static void initInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
    }

    public static DatabaseHelper getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        builder.append("CREATE TABLE ").append(PLAYLIST_TABLE).append(" (")
                .append(CommonColumns.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(PlaylistColumns.NAME).append(" TEXT, ")
                .append(PlaylistColumns.TRANSITION_ID).append(" INTEGER NOT NULL DEFAULT -1, ")
                .append(PlaylistColumns.COLLECTION_ID).append(" INTEGER NOT NULL DEFAULT -1, ")
                .append(PlaylistColumns.SCROLLABLE).append(" INTEGER NOT NULL DEFAULT 1, ")
                .append(PlaylistColumns.SCROLL_TYPE).append(" INTEGER NOT NULL DEFAULT 0")
                .append(PlaylistColumns.ACTIVE).append(" INTEGER NOT NULL DEFAULT 0")
                .append(')');
        db.execSQL(builder.toString());
        builder.setLength(0);

        builder.append("CREATE TABLE ").append(ENTRY_TABLE).append(" (")
                .append(CommonColumns.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(EntryColumns.COLLECTION_ID).append(" INTEGER NOT NULL, ")
                .append(EntryColumns.LANDSCAPE_OFFSET_X).append(" INTEGER DEFAULT 0, ")
                .append(EntryColumns.LANDSCAPE_OFFSET_Y).append(" INTEGER DEFAULT 0, ")
                .append(EntryColumns.LANDSCAPE_ZOOM).append(" REAL DEFAULT 1.0, ")
                .append(EntryColumns.LANDSCAPE_ROTATION).append(" REAL DEFAULT 0.0, ")
                .append(EntryColumns.PORTRAIT_OFFSET_X).append(" INTEGER DEFAULT 0, ")
                .append(EntryColumns.PORTRAIT_OFFSET_Y).append(" INTEGER DEFAULT 0, ")
                .append(EntryColumns.PORTRAIT_ZOOM).append(" REAL DEFAULT 1.0, ")
                .append(EntryColumns.PORTRAIT_ROTATION).append(" REAL DEFAULT 0.0, ")
                .append(EntryColumns.SCALE_TYPE).append(" TEXT NOT NULL DEFAULT ")
                .append(Scaling.fill.name()).append(", ")
                .append(EntryColumns.IMAGE_PATH).append(" TEXT NOT NULL")
                .append(')');
        db.execSQL(builder.toString());
        builder.setLength(0);

        builder.append("CREATE TABLE ").append(TRANSITION_TABLE).append(" (")
                .append(CommonColumns.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(TransitionColumns.NAME).append(" TEXT, ")
                .append(TransitionColumns.TYPE).append(" INTEGER NOT NULL, ")
                .append(TransitionColumns.RANDOM).append(" INTEGER NOT NULL DEFAULT 0, ")
                .append(TransitionColumns.DISPLAY_CYCLING_PROGRESS)
                .append(" INTEGER NOT NULL DEFAULT 0, ")
                .append(TransitionColumns.CYCLE_TIME).append(" INTEGER, ")
                .append(TransitionColumns.FADE_TIME).append(" INTEGER")
                .append(')');
        db.execSQL(builder.toString());
        builder.setLength(0);

        builder.append("CREATE TABLE ").append(COLLECTION_TABLE).append(" (")
                .append(CommonColumns.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(CollectionColumns.NAME).append(" TEXT")
                .append(')');
        db.execSQL(builder.toString());
        builder.setLength(0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(
                        "ALTER TABLE " + TRANSITION_TABLE + " ADD COLUMN " + TransitionColumns.DISPLAY_CYCLING_PROGRESS + " INTEGER NOT NULL DEFAULT 0");
            case 2:
                db.execSQL(
                        "ALTER TABLE " + PLAYLIST_TABLE + " ADD COLUMN " + PlaylistColumns.SCROLL_TYPE + " INTEGER NOT NULL DEFAULT 0");
        }
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return readableDatabase;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return writableDatabase;
    }

    public void destroy() {
        readableDatabase.close();
        writableDatabase.close();

        readableDatabase = null;
        writableDatabase = null;
        instance = null;
    }
}