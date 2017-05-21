package io.ray.hexis.util;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import io.ray.hexis.util.sql.GoalsContract.GoalsEntry;

/**
 * Class to read data from Goal table in Hexis database.
 */
public class GoalReader {
  private final SQLiteDatabase db;

  // A query to retrieve all goals with a specific title
  private final String queryGoalTitle = "SELECT * FROM "
      + GoalsEntry.TABLE_NAME
      + " WHERE "
      + GoalsEntry.COLUMN_NAME_GOAL_TITLE
      + " = \"";

  // A query to retrieve all gaols with a specific id
  private final String queryGoalId =
      "SELECT * FROM " + GoalsEntry.TABLE_NAME + " WHERE " + GoalsEntry.COLUMN_NAME_ID + " = ";

  /**
   * GoalReader constructor.
   * GoalRead needs SqlLiteHelper to access database
   *
   * @param sqlLiteHelper    Helper to get a DB
   */
  public GoalReader(SqlLiteHelper sqlLiteHelper) {
    this.db = sqlLiteHelper.getReadableDatabase();
  }

  /**
   * Used to check if a goal with matching title exists.
   *
   * @param goalTitle the desired title to search for
   * @return true if a goal with matching title is found in table
   */
  public boolean doesGoalExist(String goalTitle) {
    // Sanitize goalTitle string before sql query
    goalTitle = DatabaseUtils.sqlEscapeString(goalTitle);

    // Query database for existence of a matching goal title
    Cursor cursor = db.rawQuery(queryGoalTitle + goalTitle + "\"", null);

    // return true if at least one instance of the goal title exists
    if (cursor.getCount() > 0) {
      // Close cursor
      cursor.close();

      // Return true because goal title exists
      return true;
    }

    // Close the cursor
    cursor.close();

    // Return false because goal does not exist
    return false;
  }

  /**
   * Used to check if a goal with corresponding id exists in database.
   *
   * @param goalId the desired id to search for
   * @return true if goal with matching ID is found in table
   */
  public boolean doesGoalExist(int goalId) {

    // Query database for existence of a matching goal ID
    Cursor cursor = db.rawQuery(queryGoalId + goalId, null);

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
   * Query database looking for goal with matching ID.
   * Returns the title of the goal with the matching ID
   *
   * @param goalId the requested goal id
   * @return title of Goal with matching ID, if goal does not exist return null
   */
  public String getGoalTitle(int goalId) {

    // Query database for existence of a matching goal ID
    Cursor cursor = db.rawQuery(queryGoalId + goalId, null);

    // goalId is unique so only one goal should be returned
    if (cursor.getCount() > 0) {
      cursor.moveToNext();

      // Return the title that corresponds with goal id
      return cursor.getString(
          cursor.getColumnIndexOrThrow(GoalsEntry.COLUMN_NAME_GOAL_TITLE));
    }

    // Close the cursor
    cursor.close();

    // Return null because goal id does not exist in database
    return null;
  }
}
