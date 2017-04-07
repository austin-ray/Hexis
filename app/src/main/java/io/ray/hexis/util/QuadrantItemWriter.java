package io.ray.hexis.util;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import io.ray.hexis.util.sql.QuadrantItemsContract;

/**
 * QuadrantItemWriter class to insert data into QuadrantItems table.
 */
public class QuadrantItemWriter {
  private SQLiteDatabase db;
  private SqlLiteHelper sqlLiteHelper;
  private ContentValues values;

  /**
   * QuadrantItemWriter constructor.
   *
   * @param sqlLiteHelper needed to read database
   */
  public QuadrantItemWriter(SqlLiteHelper sqlLiteHelper) {
    this.sqlLiteHelper = sqlLiteHelper;
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

  /**
   * Used to update text of item based on item id.
   *
   * @param itemUid     id of item to be updated
   * @param newItemText new item text
   * @return            -1 if item does not exist, otherwise return item id
   */
  public long updateItemText(long itemUid, String newItemText) {
    QuadrantItemReader quadrantItemReader = new QuadrantItemReader(sqlLiteHelper);

    // First check if an item with the passed itemUid exists
    if (quadrantItemReader.doesItemExist(itemUid)) {
      // Use ContentValues to sanitize user defined new ItemText
      values = new ContentValues();

      values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT, newItemText);

      // Update itemText field where itemUid matches the given itemUid
      return db.update(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, values,
          QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + "=\"" + itemUid + "\"",
          null);
    }
    return -1L;
  }

  /**
   * Used to update the completion status of an item based on item ID.
   *
   * @param itemUid          id of item to be updated
   * @param completionStatus new completion status value of item
   * @return                 -1 if item does not exist, otherwise return item id
   */
  public long updateItemCompletion(long itemUid, int completionStatus) {
    QuadrantItemReader quadrantItemReader = new QuadrantItemReader(sqlLiteHelper);

    // First check if an item with the passed itemUid exists
    if (quadrantItemReader.doesItemExist(itemUid)) {

      // Use ContentValues to sanitize user defined new ItemText
      values = new ContentValues();

      values.put(QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS,
          completionStatus);

      // Update itemText field where itemUid matches the given itemUid
      return db.update(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, values,
          QuadrantItemsContract.QuadrantItemsEntry.COLUMN_NAME_ID + "=\"" + itemUid + "\"",
          null);
    }
    return -1L;
  }

  /**
   * Delete item based on itemUid.
   * @param itemUid id of item to be deleted
   * @return        -1 if item does not exist, otherwise return item id
   */
  public long removeItem(long itemUid) {
    QuadrantItemReader quadrantItemReader = new QuadrantItemReader(sqlLiteHelper);

    // First check if an item with the passed itemUid exists
    if (quadrantItemReader.doesItemExist(itemUid)) {

      // Delete item and return the item id
      return db.delete(QuadrantItemsContract.QuadrantItemsEntry.TABLE_NAME, "id=?",
          new String[]{Long.toString(itemUid)});
    }

    // Return -1 if item does not exist
    return -1L;
  }
}
