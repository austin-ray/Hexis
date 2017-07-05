package io.ray.hexis.view

import io.ray.hexis.model.MatrixModel
import io.ray.hexis.presenter.MatrixPresenter
import io.ray.hexis.util.SqlLiteHelper
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito

class MatrixFragmentTest {
  @Test
  @Throws(Exception::class)
  fun onCreate() {
    val fragment = MatrixFragment.newInstance()
    (fragment as? MatrixFragment)?.onCreate(null)
    assertNotNull(fragment?.presenter)
  }

  @Test
  @Throws(Exception::class)
  fun setPresenter() {
    val fragment = MatrixFragment.newInstance()
    val presenter = MatrixPresenter(fragment, Mockito.mock(MatrixModel::class.java),
        Mockito.mock(SqlLiteHelper::class.java))

    assertNotEquals(fragment.presenter, presenter)
    fragment.presenter = presenter
    assertEquals(fragment.presenter, presenter)
  }
}