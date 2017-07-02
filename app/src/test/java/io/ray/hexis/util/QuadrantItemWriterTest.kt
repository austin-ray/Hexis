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

public class QuadrantItemWriterTest {

    var mockHelper: SqlLiteHelper? = null
    var mockCursor: Cursor? = null
    private var sqlHelper: SqlLiteHelper? = null

    @Before
    fun init() {

        // Create the Mock elements to managed
        mockHelper = Mockito.mock(SqlLiteHelper::class.java)
        val mockDb = Mockito.mock(SQLiteDatabase::class.java)


        // Mock the necessary DB calls
        Mockito.`when`(mockDb.insert(Mockito.any(), Mockito.any(),
                Mockito.any(ContentValues::class.java))).thenReturn(1L)

        mockCursor = Mockito.mock(Cursor::class.java)
        Mockito.`when`(mockCursor?.count).thenReturn(1)
        Mockito.`when`(mockDb.rawQuery(Mockito.any(), Mockito.any())).thenReturn(mockCursor)

        // Mock the necessary SqlLiteHelper function calls
        Mockito.`when`(mockHelper?.readableDatabase).thenReturn(mockDb)
    }

    @Before
    fun setup() {
        // Setup the activity via Robolectric
        val activity = Robolectric.setupActivity(MainActivity::class.java)

        // Initialize the SQL Lite Helper
        sqlHelper = SqlLiteHelper(activity)
    }

    @Test
    fun sqlInitializationTest() {

        assertNotNull(mockHelper);

    }

    @Test
    fun quadrantItemWriterTest() {
        val goalWriter: GoalWriter = GoalWriter(mockHelper);
        goalWriter.insertNewGoal("test");
        val quadrantItemWriter: QuadrantItemWriter = QuadrantItemWriter(mockHelper)
        quadrantItemWriter.insertNewItem(1, 1, "Temp")
        quadrantItemWriter.updateItemCompletion(QuadrantItem("Temp"), 1);
        quadrantItemWriter.updateItem(QuadrantItem("Temp"));
        quadrantItemWriter.updateItem(QuadrantItem("Temp"), 1, -1);
        quadrantItemWriter.updateItem(QuadrantItem("Temp"), 1, 1);
        quadrantItemWriter.updateItemCompletion(QuadrantItem(null), 1);
    }
}