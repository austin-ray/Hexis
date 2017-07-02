package io.ray.hexis.util


import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.junit.Before;
import org.junit.Test;
import io.ray.hexis.model.QuadrantItem
import org.junit.Assert.assertNotNull
import io.ray.hexis.BuildConfig
import io.ray.hexis.MainActivity
import org.junit.runner.RunWith
import org.mockito.Mockito;
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(22))

public class QuadrantItemReaderTest {

    private var sqlHelper: SqlLiteHelper? = null

    @Before
    fun setup() {
        // Setup the activity via Robolectric
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        // Initialize the SQL Lite Helper
        sqlHelper = SqlLiteHelper(activity)
    }

    @Test
    fun sqlInitializationTest() {

        assertNotNull(sqlHelper);

    }

    @Test
    fun quadrantItemRedaerTest() {
        val goalWriter: GoalWriter = GoalWriter(sqlHelper);
        goalWriter.insertNewGoal("test");
        val quadrantItemWriter: QuadrantItemWriter = QuadrantItemWriter(sqlHelper)
        quadrantItemWriter.insertNewItem(1, 1, "Temp")
        quadrantItemWriter.insertNewItem(0, 1, "Temp")
        quadrantItemWriter.insertNewItem(1, 0, "Temp")
        quadrantItemWriter.insertNewItem(0, 0, "Temp")
        quadrantItemWriter.updateItemCompletion(QuadrantItem("Temp"), 1);
        quadrantItemWriter.updateItem(QuadrantItem("Temp"));
        quadrantItemWriter.updateItem(QuadrantItem("Temp"), 1, -1);
        quadrantItemWriter.updateItem(QuadrantItem("Temp"), 1, 1);
        quadrantItemWriter.updateItemCompletion(QuadrantItem(null), 1);
        val quadrantItemReader: QuadrantItemReader = QuadrantItemReader(sqlHelper);
        quadrantItemReader.getItemByUid(1L);
        quadrantItemReader.getItemsTextByQuadrant(0, 1);
    }
}