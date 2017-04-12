package io.ray.hexis.util;

import android.content.ContentValues;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.util.sql.QuadrantItemsContract;

/**
 * QuadrantItemWriter class to insert data into QuadrantItems table.
 */
public class QuadrantItemWriter extends QuadrantItemSqlInterator {
  private ContentValues values;

  /**
   * QuadrantItemWriter constructor.
   *
   * @param sqlLiteHelper needed to read database
   */
  public QuadrantItemWriter(SqlLiteHelper sqlLiteHelper) {
    super(sqlLiteHelper);
    this.db = sqlLiteHelper.getReadableDatabase();
  }

  /**
   * Used to insert new item into QuadrantItem table in database.
   *
   * @param goalId   goalId to be set
   * @param quadrant quadrant where item belongs
   * @param itemText content of item
   * @return         unique id of newly inserted item
   */
  public long insertNewItem(int goalId, int quadrant, String itemText) {
    // Shift to account for difference in counting by SQL Lite
    goalId++;
    quadrant++;

    // Use ContentValues to sanitize user defined goalText
    values = new ContentValues();

    // Add values to fields in QuadrantItem table
    values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS, 0);
    values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID, goalId);
    values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT, itemText);
    values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT, quadrant);

    // Return unique id of newly inserted item
    return db.insert(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, null, values);
  }

  public long updateItem(QuadrantItem item) {
    return updateItem(item, -1, -1);
  }

  /**
   * Update an item in the DB given a quadrant and goal.
   * Allows for moving item between quadrant and goals
   * @param item        Item being modified
   * @param quadrant    Quadrant to place the item
   * @param goal        Goal to place the item
   * @return            UID of the item
   */
  public long updateItem(QuadrantItem item, int quadrant, int goal) {
    if (doesItemExist(item)) {
      values = new ContentValues();

      values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID, item.getUid());
      values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT, item.getMessage());
      values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS,
          item.isComplete() ? 1 : 0);

      if (quadrant == -1) {
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT,
            getQuadrantByUid(item.getUid()));
      } else {
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_QUADRANT, quadrant);
      }

      if (goal == -1) {
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID,
            getGoalByUid(item.getUid()));
      } else {
        values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_GOAL_ID, goal);
      }

      return db.update(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, values,
          QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + "=\"" + item.getUid() + "\"",
          null);
    }

    return -1L;
  }

  /**
   * Used to update the completion status of an item based on item ID.
   *
   * @param item              id of item to be updated
   * @param completionStatus  new completion status value of item
   * @return                  -1 if item does not exist, otherwise return item id
   */
  public long updateItemCompletion(QuadrantItem item, int completionStatus) {
    // First check if an item with the passed itemUid exists
    if (doesItemExist(item)) {

      // Use ContentValues to sanitize user defined new ItemText
      values = new ContentValues();

      values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS,
          completionStatus);

      // Update itemText field where itemUid matches the given itemUid
      return db.update(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, values,
          QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + "=\"" + item.getUid() + "\"",
          null);
    }
    return -1L;
  }

  /**
   * Delete item based on itemUid.
   * @param item    id of item to be deleted
   * @return        -1 if item does not exist, otherwise return item id
   */
  public long removeItem(QuadrantItem item) {
    // First check if an item with the passed itemUid exists
    if (doesItemExist(item)) {

      // Delete item and return the item id
      return db.delete(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, "id=?",
          new String[]{Long.toString(item.getUid())});
    }

    // Return -1 if item does not exist
    return -1L;
  }
}
