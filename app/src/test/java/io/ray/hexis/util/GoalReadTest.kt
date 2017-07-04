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
@Config(constants = BuildConfig::class, sdk = intArrayOf(22)) class GoalReadTest {

  private var sqlHelper: SqlLiteHelper? = null

  @Before
  fun init() {
    val activity = Robolectric.setupActivity(MainActivity::class.java)

    // Initialize the SQL Lite Helper
    sqlHelper = SqlLiteHelper(activity)

    //Insert goals into database
    val goalWriter: GoalWriter = GoalWriter(sqlHelper)
    goalWriter.insertNewGoal("test")
  }

  @Test
  fun sqlInitializationTest() {
    // Check if sqlHelper was initialized correctly
    assertNotNull(sqlHelper)

  }

  @Test
  fun readGoalTitleTest() {
    val goalReader: GoalReader = GoalReader(sqlHelper)
    assertNotNull(goalReader)

    // Get first goal title in database
    assertEquals("test", goalReader.getGoalTitle(1))

    // Get second non existent goal title in database
    assertEquals(null, goalReader.getGoalTitle(2))

  }

  @Test
  fun doesGoalExistTest(){
    val goalReader: GoalReader = GoalReader(sqlHelper)
    assertNotNull(goalReader)

    // One goal exists in database with id of 1
    assertTrue(goalReader.doesGoalExist(1))

    // No goal exists in database with id of 2
    assertTrue(!goalReader.doesGoalExist(2))

    // No goal exists in database with title of "does not exist"
    assertTrue(!goalReader.doesGoalExist("does not exist"))
  }


}