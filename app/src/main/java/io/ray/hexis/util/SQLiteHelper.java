package io.ray.hexis.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.ray.hexis.util.sql.GoalsContract.GoalsEntry;
import io.ray.hexis.util.sql.QuadrantItemsContract.QuadrantItemsEntry;

public class SQLiteHelper extends SQLiteOpenHelper {

    /**
     * Database structure variables defined here
     * Dtabase is stored in /data/data/<packagename>/database
     * Important note: SQL autoincrement starts at 1 not 0
     */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Hexis.db";

    /**
     * Command to create "Goals" table in Hexis database.
     *
     * "Goals" table has the following fields:
     *
     * "id", primary id, autoincremented (starts at 1)
     * "goal_total", text, the name of the goal
     */
    private static final String SQL_CREATE_GOALS_TABLE =
            "CREATE TABLE " + GoalsEntry.TABLE_NAME + "(" +
                    GoalsEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GoalsEntry.COLUMN_NAME_GOAL_TITLE + " TEXT );";

    /**
     * Command to create "QuadrantItems" table in Hexis database
     *
     * "QuadrantItems" table has the following fields:
     *
     * "id", primary id, autoincremented (starts at 1)
     * "goal_id", integer, corresponds with associated goal id from Goals table
     * "item_text", text, information about the item
     * "quadrant", integer, which quadrant the item belongs to
     * "completion_status", integer, set to 0 for unfinished. Set to 1 for finished
     */
    private static final String SQL_CREATE_QUADRANT_ITEMS_TABLE =
            "CREATE TABLE " + QuadrantItemsEntry.TABLE_NAME + " ( " +
                    QuadrantItemsEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QuadrantItemsEntry.COLUMN_NAME_GOAL_ID + " INTEGER," +
                    QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT + " TEXT," +
                    QuadrantItemsEntry.COLUMN_NAME_QUADRANT + " INTEGER, " +
                    QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS + " INTEGER );";

    // Command to delete Goal table
    private static final String SQL_DELETE_GOALS = "DROP TABLE IF EXISTS " + GoalsEntry.TABLE_NAME;

    // Command to delete QuadrantItems table
    private static final String SQL_DELETE_QUADRANT_ITEMS = "DROP TABLE IF EXISTS " +
            QuadrantItemsEntry.TABLE_NAME;

    // Purge database variable, set to true if database needs to be reset
    private static final boolean PURGE_DATABASE = false;

    /**
     * Constructor used to create database if database does not already exist
     * Will delete existing Hexis database if PURGE_DATABASE is true
     * @param context
     */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Delete Hexis database if PURGE_DATABASE is true
        if (PURGE_DATABASE) {

            // For testing purposes we need to delete the database so onCreate will be called
            context.deleteDatabase(DATABASE_NAME);

            // Delete actual database file from filesystem
            context.deleteFile(DATABASE_NAME);
        }
    }

    /**
     * This method is only called if the database does not already exist
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Goals and QuadrantItems tables in Hexis database if the database
        // does not already exist
        db.execSQL(SQL_CREATE_GOALS_TABLE);
        db.execSQL(SQL_CREATE_QUADRANT_ITEMS_TABLE);
    }

    /**
     * This method is only called if DATABASE_VERSION is changed
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete Goals and QuadrantItems tables in Hexis database
        db.execSQL(SQL_DELETE_GOALS);
        db.execSQL(SQL_DELETE_QUADRANT_ITEMS);

        // Do what is necessary to create Hexis database tables
        db.execSQL(SQL_CREATE_GOALS_TABLE);
        db.execSQL(SQL_CREATE_QUADRANT_ITEMS_TABLE);
    }
}
