package io.ray.hexis.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.util.sql.QuadrantItemsContract;

public class QuadrantItemSqlInterator {

  private final SqlLiteHelper helper;
  protected SQLiteDatabase db;

  // A query to retrieve all gaols with a specific id
  protected final String queryGoalId = "SELECT * FROM "
      + QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME
      + " WHERE " + QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + " = ";

  public QuadrantItemSqlInterator(SqlLiteHelper helper) {
    this.helper = helper;
  }

  /**
   * Checks if an item exists.
   * @param item    Object being checked for existence in the DB
   * @return        If the item exists or not
   */
  public boolean doesItemExist(QuadrantItem item) {
    // Query database for existence of a matching goal ID
    Cursor cursor = db.rawQuery(queryGoalId + item.getUid(), null);
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
   * Get the quadrant of an item given its UID.
   *
   * @param uid     UID of the item
   * @return        Item with a given UID
   */
  public long getQuadrantByUid(long uid) {
    return (long) getValueOfColumnByUid(uid,
        QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT, 0L);
  }

  /**
   * Get the goal of an item given its UID.
   *
   * @param uid     UID of the item
   * @return        Item with a given UID
   */
  public long getGoalByUid(long uid) {
    return (long) getValueOfColumnByUid(uid,
        QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID, 0L);
  }

  /**
   * Gets the value in a column given an item's UID.
   * @param uid       UID to determine which row to work with
   * @param column    Column name
   * @param type      Object used to check which type to work with
   * @return          Value in the column
   */
  protected Object getValueOfColumnByUid(long uid, String column, Object type) {
    // Query the database for the existence of a matching goal ID.
    Cursor cursor = db.rawQuery(queryGoalId + uid, null);

    // Return true if at least one instance of the goal id exists
    if (cursor.getCount() > 0) {
      cursor.moveToNext();

      if (type instanceof String) {
        type = cursor.getString(cursor.getColumnIndexOrThrow(column));
      } else if (type instanceof Long) {
        type = cursor.getLong(cursor.getColumnIndexOrThrow(column));
      }

      // Close cursor
      cursor.close();

      // Return true because goal id exists
      return type;
    }

    // Close the cursor
    cursor.close();

    // Return null because goal id does not exist
    return -1L;
  }
}
