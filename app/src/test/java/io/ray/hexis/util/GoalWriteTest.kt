package io.ray.hexis.util

import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import io.ray.hexis.BuildConfig
import io.ray.hexis.MainActivity
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(22))

public class GoalWriteTest {

  private var sqlHelper: SqlLiteHelper? = null

  @Before
  fun init() {
    val activity = Robolectric.setupActivity(MainActivity::class.java)

    // Initialize the SQL Lite Helper
    sqlHelper = SqlLiteHelper(activity)

  }

  @Test
  fun sqlInitializationTest() {
    // Check if sqlHelper was initialized correctly
    assertNotNull(sqlHelper);

  }

  @Test
  fun writeTest() {
    val goalReader: GoalReader = GoalReader(sqlHelper);
    val goalWriter: GoalWriter = GoalWriter(sqlHelper);
    assertNotNull(goalReader);
    assertNotNull(goalWriter);

    // Write new goal with title "test"
    assertEquals(1, goalWriter.insertNewGoal("test"))

    // Update goal with title "does not exist" to "updated"
    assertEquals(-1,goalWriter.updateGoal("does not exist", "updated"))

    // Update goal by id
    assertEquals(1, goalWriter.updateGoal(1, "updated update"))
  }
}