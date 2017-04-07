package io.ray.hexis.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.util.sql.QuadrantItemsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to read data from QuadrantItems table from Hexis Database.
 */
public class QuadrantItemReader {
  private final SQLiteDatabase db;
  private final SqlLiteHelper sqlLiteHelper;

  // A query to retrieve all gaols with a specific id
  private final String queryGoalId = "SELECT * FROM "
      + QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME
      + " WHERE " + QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + " = ";

  /**
   * QuadrantItemReader constructor.
   *
   * @param sqlLiteHelper needed to read database
   */
  public QuadrantItemReader(SqlLiteHelper sqlLiteHelper) {
    this.sqlLiteHelper = sqlLiteHelper;
    this.db = sqlLiteHelper.getReadableDatabase();
  }

  /**
   * Used to retrieve QuadrantItem list of items from goal table.
   * Offset in goalId and quadrantId of +1 is required due to
   * sql autoincrement starting at 1 rather than 0
   *
   * @param goalId        id of goal
   * @param quadrantId    quadrant id
   * @return a list of every item matching the passed parameters
   */
  public List<QuadrantItem> getItemsTextByQuadrant(int goalId, int quadrantId) {
    // Offset to account for difference in counting by SQL Lite
    goalId++;
    quadrantId++;

    // Fields to retrieve from QuadrantItem table
    String[] projection = {
        QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID,
        QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT,
        QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS
    };

    // Fields to query in QuadrantItem table
    String selection = QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID + " = ? AND "
        + QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT + " = ?";

    // Fields that will be queried in QuadrantItem table
    String[] selectionArgs = {String.valueOf(goalId), String.valueOf(quadrantId)};

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
      long id = cursor.getLong(cursor.getColumnIndexOrThrow(
          QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID));

      String msg = cursor.getString(cursor.getColumnIndexOrThrow(
          QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT));

      int completion = cursor.getInt(cursor.getColumnIndexOrThrow(
          QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS));

      items.add(new QuadrantItem(msg, id, completion));
    }

    cursor.close();

    // Return list of QuadrantItems
    return items;
  }

  /**
   * Used to check if an item with a defined ID exists in QuadrantItem table.
   *
   * @param itemUid   ID of item in the database
   * @return true if item with defined ID exists
   */
  public boolean doesItemExist(long itemUid) {
    // Query database for existence of a matching goal ID
    Cursor cursor = db.rawQuery(queryGoalId + itemUid, null);
    // Return true if at least one instance of the goal id exists
    if (cursor.getCount() > 0) {

      // Close cursor
      cursor.close();

      // Return true because goal id exists
      return true;
    }

    // Close the cursor
    cursor.close();

    // Return false because goal id does not exist
    return false;
  }

  /**
   * Get an item from the database given its UID.
   *
   * @param itemUid   UID of the item
   * @return          Item with a given UID
   */
  public String getItemByUid(long itemUid) {
    // Query database for existence of a matching goal ID
    Cursor cursor = db.rawQuery(queryGoalId + itemUid, null);
    // Return true if at least one instance of the goal id exists
    if (cursor.getCount() > 0) {
      cursor.moveToNext();

      String itemText = cursor.getString(cursor.getColumnIndexOrThrow(
          QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT));

      // Close cursor
      cursor.close();

      // Return true because goal id exists
      return itemText;
    }

    // Close the cursor
    cursor.close();

    // Return null because goal id does not exist
    return null;
  }
}
