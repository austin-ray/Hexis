package io.ray.hexis.util;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import io.ray.hexis.util.sql.GoalsContract;
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

    /**
     * Used to update text of item based on item id
     *
     * @param itemUID id of item to be updated
     * @param newItemText new item text
     * @return -1 if item does not exist, otherwise return item id
     */
    public long updateItemText(long itemUID, String newItemText)
    {
        QuadrantItemReader quadrantItemReader = new QuadrantItemReader(sqLiteHelper);

        // First check if an item with the passed itemUID exists
        if(quadrantItemReader.doesItemExist(itemUID)) {

            // Use ContentValues to sanitize user defined new ItemText
            values = new ContentValues();

            values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT, newItemText);

            // Update itemText field where itemUID matches the given itemUID
            return db.update(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, values,
                QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + "=\"" + itemUID + "\"",
                null);
        }
        return -1L;
    }

    /**
     * Used to update the completion status of an item based on item ID
     *
     * @param itemUID id of item to be updated
     * @param completionStatus new completion status value of item
     * @return return -1 if item does not exist, otherwise return item id
     */
    public long updateItemCompletion(long itemUID, int completionStatus){
        QuadrantItemReader quadrantItemReader = new QuadrantItemReader(sqLiteHelper);

        // First check if an item with the passed itemUID exists
        if(quadrantItemReader.doesItemExist(itemUID)) {

            // Use ContentValues to sanitize user defined new ItemText
            values = new ContentValues();

            values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS, completionStatus);

            // Update itemText field where itemUID matches the given itemUID
            return db.update(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, values,
                QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + "=\"" + itemUID + "\"",
                null);
        }
        return -1L;
    }
}
