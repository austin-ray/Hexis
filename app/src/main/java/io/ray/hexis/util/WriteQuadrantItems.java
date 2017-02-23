package io.ray.hexis.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import io.ray.hexis.util.sql.QuadrantItemsContract;

/**
 * WriteGoalItems class to insert data into QuadrantItems table
 */

public class WriteQuadrantItems {
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;
    private ContentValues values;

    /**
     * @param sqLiteHelper
     */
    public WriteQuadrantItems(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
        this.db = sqLiteHelper.getReadableDatabase();
    }

    /**
     * @param goalID the goalID to be set
     * @param quadrant the quadrant the item should be assoicated with
     * @param itemText the content of the item
     * @return the unique id of the newly inserted item
     */
    public long insertNewItem(int goalID, int quadrant, String itemText){
        values = new ContentValues();
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS, 0);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID, goalID);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT, itemText);
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT, quadrant);
        return db.insert(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, null, values);
    }
}
