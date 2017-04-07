package io.ray.hexis.util;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import io.ray.hexis.util.sql.GoalsContract.GoalsEntry;

/**
 * GoalWriter class used to write to Goal table in Hexis database.
 */
public class GoalWriter {
  private final SQLiteDatabase db;
  private final SqlLiteHelper sqlLiteHelper;
  private ContentValues values;

  /**
   * GoalWriter constructor.
   *
   * @param sqlLiteHelper needed to access database
   */
  public GoalWriter(SqlLiteHelper sqlLiteHelper) {
    this.sqlLiteHelper = sqlLiteHelper;
    this.db = sqlLiteHelper.getReadableDatabase();
  }

  /**
   * Used to create new goal in goal table.
   *
   * @param goalTitle title of new goal
   * @return primary key of newly created goal
   */
  public long insertNewGoal(String goalTitle) {
    // Use ContentValues to sanitize user defined goalTitle string
    values = new ContentValues();

    // Add new goal title value to goal_title field in goal table
    values.put(GoalsEntry.COLUMN_NAME_GOAL_TITLE, goalTitle);

    // Insert the new goal, returning the primary key value of the new goal
    return db.insert(GoalsEntry.TABLE_NAME, null, values);
  }

  /**
   * Use of this method is discouraged as it will only update the first existence of any goal that
   * matches the requested goal title.
   * Used to update an existing goal's title in goal table
   *
   * @param goalTitle    original goal title
   * @param newGoalTitle new goal title
   * @return primary key of updated goal, returns -1 if goal does not exist
   */
  public long updateGoal(String goalTitle, String newGoalTitle) {
    GoalReader goalReader = new GoalReader(sqlLiteHelper);

    // Check if goal with original title exists in goal table
    if (goalReader.doesGoalExist(goalTitle)) {
      // Use ContentValues to sanitize user defined goalTitle string
      values = new ContentValues();

      // Add new goal title to goal_title field in goal table
      values.put(GoalsEntry.COLUMN_NAME_GOAL_TITLE, newGoalTitle);

      // Update goal title in goal table where goal title matches requested title
      return db.update(GoalsEntry.TABLE_NAME, values, GoalsEntry.COLUMN_NAME_GOAL_TITLE
          + "=\""
          + DatabaseUtils.sqlEscapeString(goalTitle)
          + "\"", null);
    }

    // Return -1 if no goal exists in goal table matching original goal title
    return -1;
  }

  /**
   * Used to update an existing goal's title in goal table.
   *
   * @param goalId       id of goal to be updated
   * @param newGoalTitle new goal title
   * @return primary key of updated goal, returns -1 if goal does not exist
   */
  public long updateGoal(int goalId, String newGoalTitle) {
    GoalReader goalReader = new GoalReader(sqlLiteHelper);

    // Check if goal exists
    if (goalReader.doesGoalExist(goalId)) {

      // Use ContentValues to sanitize user defined goalTitle string
      values = new ContentValues();

      // Add new goal title to goal_title field in goal table
      values.put(GoalsEntry.COLUMN_NAME_GOAL_TITLE, newGoalTitle);

      // Update goal title in goal table where goal id matches requested id
      return db.update(GoalsEntry.TABLE_NAME, values,
          GoalsEntry.COLUMN_NAME_ID + "=" + goalId, null);
    }

    // Return -1 if no goal exists in goal table matching goal id
    return -1;
  }
}
