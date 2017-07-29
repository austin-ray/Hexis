package io.ray.hexis.util

import android.database.sqlite.SQLiteDatabase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

import io.ray.hexis.BuildConfig
import io.ray.hexis.MainActivity
import io.ray.hexis.util.sql.GoalsContract
import io.ray.hexis.util.sql.QuadrantItemsContract
import org.junit.Assert.assertNotNull

import org.mockito.Mockito

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, packageName = BuildConfig.BASE_APP_ID,
    sdk = intArrayOf(25))
class SqlLiteHelperTest {
  private var sqlHelper: SqlLiteHelper? = null

  @Before
  fun setup() {
    // Setup the activity via Robolectric
    val activity = Robolectric.setupActivity(MainActivity::class.java)

    // Initialize the SQL Lite Helper
    sqlHelper = SqlLiteHelper(activity)
  }

  @Test
  @Throws(Exception::class)
  fun sqlInitializeTest() {
    GoalsContract.GoalsEntry()
    QuadrantItemsContract.QuadrantItemsEntry()
    assertNotNull(sqlHelper)
  }

  @Test
  @Throws(Exception::class)
  fun goalReaderWriterTest() {
    val goalReader = GoalReader(sqlHelper)
    val goalWriter = GoalWriter(sqlHelper)
    assertNotNull(goalReader)
    assertNotNull(goalWriter)
    goalReader.getGoalTitle(0)
    goalReader.doesGoalExist(0)
    goalReader.getGoalTitle(1)
    goalReader.doesGoalExist(1)
    goalReader.doesGoalExist("")
  }

  @Test
  @Throws(Exception::class)
  fun quadrantItemReaderTest() {
    val quadrantItemReader = QuadrantItemReader(sqlHelper)
    quadrantItemReader.getItemsTextByQuadrant(1, 1)
  }


  @Test
  @Throws(Exception::class)
  fun onUpgradeTest() {
    val mockDb: SQLiteDatabase = Mockito.mock(SQLiteDatabase::class.java)
    sqlHelper?.onUpgrade(mockDb, 1, 2)
  }
}
