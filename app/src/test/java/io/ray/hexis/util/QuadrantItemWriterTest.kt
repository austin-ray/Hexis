package io.ray.hexis.util


import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import io.ray.hexis.model.QuadrantItem
import org.junit.Assert.assertNotNull
import io.ray.hexis.BuildConfig
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, packageName = BuildConfig.BASE_APP_ID, sdk = intArrayOf(25))
class QuadrantItemWriterTest {

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
  fun sqlInitializationTest() {
    assertNotNull(mockHelper)
  }

  @Test
  fun quadrantItemWriterInsertTest() {
    val goalWriter: GoalWriter = GoalWriter(mockHelper)
    goalWriter.insertNewGoal("test")
    val quadrantItemWriter: QuadrantItemWriter = QuadrantItemWriter(mockHelper)

    // Add new item with title of Temp
    assertEquals(1, quadrantItemWriter.insertNewItem(1, 1, "Temp"))
  }


  @Test
  fun quadrantItemWriterUpdateTest() {
    val goalWriter: GoalWriter = GoalWriter(mockHelper)
    goalWriter.insertNewGoal("test")
    val quadrantItemWriter: QuadrantItemWriter = QuadrantItemWriter(mockHelper)

    // Insert items to be tested
    quadrantItemWriter.insertNewItem(1, 1, "Temp")
    quadrantItemWriter.insertNewItem(0, 1, "Temp")
    quadrantItemWriter.insertNewItem(1, 0, "Temp")
    quadrantItemWriter.insertNewItem(0, 0, "Temp")

    // Update completion status item with title of temp
    assertEquals(0, quadrantItemWriter.updateItemCompletion(QuadrantItem("Temp"), 1))

    // Update item
    assertEquals(0, quadrantItemWriter.updateItem(QuadrantItem("Temp")))

    // Update item
    assertEquals(0, quadrantItemWriter.updateItem(QuadrantItem("Temp"), 1, -1))

    // Update item
    assertEquals(0, quadrantItemWriter.updateItem(QuadrantItem("Temp"), 1, 1))

    // Update item
    assertEquals(0, quadrantItemWriter.updateItemCompletion(QuadrantItem(null), 1))
  }
}