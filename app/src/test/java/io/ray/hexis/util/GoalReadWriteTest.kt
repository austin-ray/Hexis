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

public class GoalReadWriteTest {

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
    fun readWriteTest() {
        val goalReader: GoalReader = GoalReader(mockHelper);
        val goalWriter: GoalWriter = GoalWriter(mockHelper);
        assertNotNull(goalReader);
        assertNotNull(goalWriter);

        goalWriter.insertNewGoal("test");
        assert(goalReader.doesGoalExist("test"));
        goalWriter.updateGoal("test", "updated");
        assert(!goalReader.doesGoalExist("test"));
        assert(goalReader.doesGoalExist("updated"));
        goalWriter.updateGoal(0, "updated update");
        assert(goalReader.doesGoalExist("updated update"));

        goalReader.getGoalTitle(0);
        goalReader.doesGoalExist(0);
        goalReader.getGoalTitle(1);
        goalReader.doesGoalExist(1);
        goalReader.doesGoalExist("");
        goalReader.doesGoalExist(-1);
        goalReader.doesGoalExist("none");
    }
}