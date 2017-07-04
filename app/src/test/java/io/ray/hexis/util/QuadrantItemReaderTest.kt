package io.ray.hexis.util

import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals
import io.ray.hexis.BuildConfig
import io.ray.hexis.MainActivity
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(22)) class QuadrantItemReaderTest {

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

    // Check if sqlHelper was initialized correctly
    assertNotNull(sqlHelper)

  }

  @Test
  fun quadrantItemRedaerTest() {
    val goalWriter: GoalWriter = GoalWriter(sqlHelper)
    goalWriter.insertNewGoal("test")
    val quadrantItemWriter: QuadrantItemWriter = QuadrantItemWriter(sqlHelper)
    // Insert items to be tested
    quadrantItemWriter.insertNewItem(1, 1, "Temp")
    quadrantItemWriter.insertNewItem(0, 1, "Temp")
    quadrantItemWriter.insertNewItem(1, 0, "Temp")
    quadrantItemWriter.insertNewItem(0, 0, "Temp")


    val quadrantItemReader: QuadrantItemReader = QuadrantItemReader(sqlHelper)

    // Test if an item by the id of 1 exists with the text "Temp"
    assertEquals("Temp", quadrantItemReader.getItemByUid(1L))

    // Test if item in quadrant 0 and id 1 exists with text "Temp"
    assertEquals("Temp", quadrantItemReader.getItemsTextByQuadrant(0, 1)[0].message)
  }
}