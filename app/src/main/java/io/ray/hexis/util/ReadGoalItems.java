package io.ray.hexis.util;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.util.sql.QuadrantItemsContract;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to read data from QuadrantItems table from hexis Database
 */
public class ReadGoalItems {
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;

    /**
     * @param sqLiteHelper
     */
    public ReadGoalItems(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
        this.db = sqLiteHelper.getReadableDatabase();
    }

    /**
     * @param goalID the id of goal
     * @param quadrantID the quadrant id
     * @return a list of every item matching the passed parameters
     */
    public List getItemsTextByQuadrant(int goalID, int quadrantID){
        String[] projection = {
            QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID,
            QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT,
            QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS
        };

        String selection = QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID + " = ? AND " +
            QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT + " = ?";

        String[] selectionArgs = {String.valueOf(goalID), String.valueOf(quadrantID)};

        String sortOrder = QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
            QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        );

        List<QuadrantItem> items = new ArrayList<>();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(
                cursor.getColumnIndexOrThrow(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID));
            String msg = cursor.getString(
                cursor.getColumnIndexOrThrow(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT));

            int completion = cursor.getInt(
                cursor.getColumnIndexOrThrow(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS));

            items.add(new QuadrantItem(msg, id, completion));
        }
        return items;
    }
}
