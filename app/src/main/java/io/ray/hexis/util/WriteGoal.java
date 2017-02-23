package io.ray.hexis.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import io.ray.hexis.util.sql.GoalsContract.GoalsEntry;

/**
 * InsertGoal class to insert a new row into Goal table
 */
public class WriteGoal {
    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;
    private ContentValues values;

    /**
     * @param sqLiteHelper
     */
    public WriteGoal(SQLiteHelper sqLiteHelper) {
        this.sqLiteHelper = sqLiteHelper;
        this.db = sqLiteHelper.getReadableDatabase();
    }

    /**
     * @return primary key of newly created row
     */
    public long insertNewGoal(String goalTitle) {
        // Use ContentValues to sanitize user defined goalTitle string
        values = new ContentValues();
        values.put(GoalsEntry.COLUMN_NAME_GOAL_TITLE, goalTitle);
        // Insert the new row, returning the primary key value of the new row
        return db.insert(GoalsEntry.TABLE_NAME, null, values);
    }

    /**
     * @return primary key of updated goal, returns -1 if goal does not exist
     */
    public long updateGoal(String goalTitle, String newGoalTitle) {
        ReadGoal readGoal = new ReadGoal(sqLiteHelper);
        if (readGoal.doesGoalExist(goalTitle)) {
            // Use ContentValues to sanitize user defined goalTitle string
            values = new ContentValues();
            values.put(GoalsEntry.COLUMN_NAME_GOAL_TITLE, newGoalTitle);
            return db.update(GoalsEntry.TABLE_NAME, values, GoalsEntry.COLUMN_NAME_GOAL_TITLE
                + "=\""
                + DatabaseUtils.sqlEscapeString(goalTitle)
                + "\"", null);
        }
        return -1;
    }

    /**
     * @return primary key of updated goal, returns -1 if goal does not exist
     */
    public long updateGoal(int goalID, String newGoalTitle) {
        ReadGoal readGoal = new ReadGoal(sqLiteHelper);
        if (readGoal.doesGoalExist(goalID)) {
            // Use ContentValues to sanitize user defined goalTitle string
            values = new ContentValues();
            values.put(GoalsEntry.COLUMN_NAME_GOAL_TITLE, newGoalTitle);
            Log.d("WriteGoal write", "updating");
            return db.update(GoalsEntry.TABLE_NAME, values,
                GoalsEntry.COLUMN_NAME_ID + "=" + goalID, null);
        }
        return -1;
    }
}
