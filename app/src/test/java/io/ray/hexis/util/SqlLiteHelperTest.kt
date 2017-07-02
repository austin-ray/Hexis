package io.ray.hexis.util;

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.junit.Before;
import org.junit.Test;
import io.ray.hexis.model.QuadrantItem
import org.junit.Assert.assertNotNull
import org.mockito.Mockito;

public class SqlLiteHelperTest {

    var mockHelper: SqlLiteHelper? = null
    var mockCursor: Cursor? = null

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

    @Test
    fun createHelper() {

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
    }

    @Test
    fun quadrantItemRedaerTest() {
        val goalWriter: GoalWriter = GoalWriter(mockHelper);
        goalWriter.insertNewGoal("test");
        val quadrantItemWriter: QuadrantItemWriter = QuadrantItemWriter(mockHelper)
        quadrantItemWriter.insertNewItem(1, 1, "Temp")
        quadrantItemWriter.updateItemCompletion(QuadrantItem("Temp"), 1);
        quadrantItemWriter.updateItem(QuadrantItem("Temp"));
        quadrantItemWriter.updateItem(QuadrantItem("Temp"), 1, -1);
        quadrantItemWriter.updateItem(QuadrantItem("Temp"), 1, 1);
        quadrantItemWriter.updateItemCompletion(QuadrantItem(null), 1);
        val quadrantItemReader: QuadrantItemReader = QuadrantItemReader(mockHelper);
        quadrantItemReader.getItemByUid(-1L);
        quadrantItemReader.getItemsTextByQuadrant(0, 0);
    }

}
