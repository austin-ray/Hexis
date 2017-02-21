package io.ray.hexis.util.sql;


import android.provider.BaseColumns;

public final class GoalsContract {
    // Prevent instantiation
    private GoalsContract() { }

    public static class GoalsEntry implements BaseColumns {
        public static final String TABLE_NAME = "goals";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_GOAL_TITLE = "goal_title";
    }
}
