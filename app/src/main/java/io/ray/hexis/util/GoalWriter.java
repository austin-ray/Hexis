package io.ray.hexis.util;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import io.ray.hexis.util.sql.GoalsContract.GoalsEntry;

/**
 * GoalWriter class used to write to Goal table in Hexis database
 */

public class GoalWriter {
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;
    private ContentValues values;

    /**
     * GoalWriter constructor
     * @param sqLiteHelper needed to access database
     */
    public GoalWriter(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
        this.db = sqLiteHelper.getReadableDatabase();
    }

    /**
     * Used to create new goal in goal table
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
     * Use of this method is discouraged as it will only update
     * the first existence of any goal that matches the requested goal title
     *
     * Used to update an existing goal's title in goal table
     *
     * @param goalTitle original goal title
     * @param newGoalTitle new goal title
     * @return primary key of updated goal, returns -1 if goal does not exist
     */
    public long updateGoal(String goalTitle, String newGoalTitle) {
        GoalReader goalReader = new GoalReader(sqLiteHelper);

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
     *  Used to update an existing goal's title in goal table
     *
     * @param goalID id of goal to be updated
     * @param newGoalTitle new goal title
     * @return primary key of updated goal, returns -1 if goal does not exist
     */
    public long updateGoal(int goalID, String newGoalTitle) {
        GoalReader goalReader = new GoalReader(sqLiteHelper);

        // Check if goal exists
        if (goalReader.doesGoalExist(goalID)) {

            // Use ContentValues to sanitize user defined goalTitle string
            values = new ContentValues();

            // Add new goal title to goal_title field in goal table
            values.put(GoalsEntry.COLUMN_NAME_GOAL_TITLE, newGoalTitle);

            // Update goal title in goal table where goal id matches requested id
            return db.update(GoalsEntry.TABLE_NAME, values,
                GoalsEntry.COLUMN_NAME_ID + "=" + goalID, null);
        }

        // Return -1 if no goal exists in goal table matching goal id
        return -1;
    }
}
