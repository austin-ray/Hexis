package io.ray.hexis.view

import android.view.LayoutInflater
import android.view.View
import io.ray.hexis.BuildConfig
import io.ray.hexis.MainActivity
import io.ray.hexis.R
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(22))
class QuadrantItemViewHolderTest {

  var contextAct: MainActivity? = null
  var view: View? = null

  @Before
  fun init() {
    contextAct = Robolectric.setupActivity(MainActivity::class.java)
    view = LayoutInflater.from(contextAct?.baseContext)
        .inflate(R.layout.view_quadrant_item, null, false)
  }

  @Test
  fun clickCheckTest() {
    val vh: QuadrantItemViewHolder = QuadrantItemViewHolder(view)

    assertFalse(vh.isChecked)

    vh.clickCheck()
    assertTrue(vh.isChecked)
  }

  @Test
  fun isCheckedTest() {
    val vh: QuadrantItemViewHolder = QuadrantItemViewHolder(view)
    assertFalse(vh.isChecked)
  }
}