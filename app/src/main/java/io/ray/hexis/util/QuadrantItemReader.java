package io.ray.hexis.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.util.sql.QuadrantItemsContract;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to read data from QuadrantItems table from hexis Database
 */
public class QuadrantItemReader {
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;

    /**
     * QuadrantItemReader constructor
     * @param sqLiteHelper needed to read database
     */
    public QuadrantItemReader(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
        this.db = sqLiteHelper.getReadableDatabase();
    }

    /**
     * Used to retrieve QuadrantItem list of items from goal table
     *
     * Offest in goalID and quadrantID of +1 is required due to
     * sql autoincrement starting at 1 rather than 0
     *
     * @param goalID id of goal
     * @param quadrantID quadrant id
     * @return a list of every item matching the passed parameters
     */
    public List<QuadrantItem> getItemsTextByQuadrant(int goalID, int quadrantID){
        // Offset to account for difference in counting by SQL Lite
        goalID++;
        quadrantID++;

        // Fields to retrieve from QuadrantItem table
        String[] projection = {
            QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID,
            QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT,
            QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS
        };

        // Fields to query in QuadrantItem table
        String selection = QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID + " = ? AND " +
            QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT + " = ?";

        // Fields that will be queried in QuadrantItem table
        String[] selectionArgs = {String.valueOf(goalID), String.valueOf(quadrantID)};

        // Sort results in descending order based on item id
        String sortOrder = QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + " DESC";

        // Create cursor and query database
        Cursor cursor = db.query(
            QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        );

        // List of item lists
        List<QuadrantItem> items = new ArrayList<>();

        // Traverse the cursor and place all items into item lists
        while (cursor.moveToNext()) {
            long id = cursor.getLong(
                cursor.getColumnIndexOrThrow(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID));
            String msg = cursor.getString(
                cursor.getColumnIndexOrThrow(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT));

            int completion = cursor.getInt(
                cursor.getColumnIndexOrThrow(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS));

            items.add(new QuadrantItem(msg, id, completion));
        }

        // Return list of QuadrantItems
        return items;
    }
}
