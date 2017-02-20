package io.ray.hexis.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    /**
     * Database structure variables defined here
     * Dtabase is stored in /data/data/<packagename>/database
     */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Hexis.db";

    // Command to create Goals table
    public static final String SQL_CREATE_GOALS_TABLE = "CREATE TABLE \"Goals\" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `goal_title` TEXT );";

    // Command to create QuadrantItems table
    public static final String SQL_CREATE_QUADRANTITEMS_TABLE = "CREATE TABLE \"QuadrantItems\" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `goal_id` INTEGER, `item_text` TEXT, `quadrant` INTEGER, `completion_status` INTEGER );";

    // Command to delete Goal table
    public static final String SQL_DELETE_GOALS = "DROP TABLE IF EXISTS Goals";

    // Command to delete QuadrantItems table
    public static final String SQL_DELETE_QUADRANTITEMS = "DROP TABLE IF EXISTS QuadrantItems";

    // Purge database variable, set to true if database needs to be reset
    public static final boolean PURGE_DATABASE = false;

    /**
     * Constructor used to create database if database does not already exist
     * @param context
     */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if(PURGE_DATABASE) {
            //For testing purposes we need to delete the database so onCreate will be called
            context.deleteDatabase(DATABASE_NAME);
            context.deleteFile(DATABASE_NAME);
        }
    }

    /**
     * This method is only called if the database does not already exist
     * @param db
     */
    @Override public void onCreate(SQLiteDatabase db) {
        // Create Goals and QuadrantItems tables in Hexis database if the database does not already exist
            db.execSQL(SQL_CREATE_GOALS_TABLE);
            db.execSQL(SQL_CREATE_QUADRANTITEMS_TABLE);
    }

    /**
     * This method is only called if DATABASE_VERSION is changed
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete Goals and QuadrantItems tables in Hexis database
        db.execSQL(SQL_DELETE_GOALS);
        db.execSQL(SQL_DELETE_QUADRANTITEMS);
        // Do what is necessary to update Hexis database
        db.execSQL(SQL_CREATE_GOALS_TABLE);
        db.execSQL(SQL_CREATE_QUADRANTITEMS_TABLE);
    }
}
