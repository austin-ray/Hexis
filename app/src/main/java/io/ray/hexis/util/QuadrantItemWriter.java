package io.ray.hexis.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import io.ray.hexis.util.sql.QuadrantItemsContract;

/**
 * QuadrantItemWriter class to insert data into QuadrantItems table
 */

public class QuadrantItemWriter {
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;
    private ContentValues values;

    /**
     * QuadrantItemWriter constructor
     * @param sqLiteHelper needed to read database
     */
    public QuadrantItemWriter(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
        this.db = sqLiteHelper.getReadableDatabase();
    }

    /**
     * Used to insert new item into QuadrantItem table in database
     *
     * @param goalID goalID to be set
     * @param quadrant quadrant where item belongs
     * @param itemText content of item
     * @return unique id of newly inserted item
     */
    public long insertNewItem(int goalID, int quadrant, String itemText) {
        // Shift to account for difference in counting by SQL Lite
        goalID++;
        quadrant++;

        // Use ContentValues to sanitize user defined goalText
        values = new ContentValues();

        // Add values to fields in QuadrantItem table
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS, 0);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID, goalID);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT, itemText);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT, quadrant);

        // Return unique id of newly inserted item
        return db.insert(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, null, values);
    }
}
