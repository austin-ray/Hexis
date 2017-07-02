package io.ray.hexis.util

import android.database.sqlite.SQLiteDatabase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GoalWriterTest {

  var writer: GoalWriter? = null

  @Before
  fun init() {
    val mockHelper: SqlLiteHelper = Mockito.mock(SqlLiteHelper::class.java)
    val mockDb: SQLiteDatabase = Mockito.mock(SQLiteDatabase::class.java)

    Mockito.`when`(mockHelper.readableDatabase).thenReturn(mockDb)
    Mockito.`when`(mockDb.insert(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(1L)

    writer = GoalWriter(mockHelper)
  }

  @Test
  @Throws(Exception::class)
  fun insertNewGoalTest() {
    writer?.insertNewGoal("TEST")
  }
}