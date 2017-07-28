package io.ray.hexis.util

import io.ray.hexis.model.QuadrantItem
import org.junit.Test

import org.junit.Assert.*

class QuadrantItemComparatorTest {
  @Test
  fun compare() {
    val item1 : QuadrantItem = QuadrantItem("TEST1")
    val item2 : QuadrantItem = QuadrantItem("TEST2")
    val item3 : QuadrantItem = QuadrantItem("TEST3")

    item3.setCompletion(true)

    val comparator : QuadrantItemComparator = QuadrantItemComparator()

    assertEquals(0, comparator.compare(item1, item2))
    assertEquals(1, comparator.compare(item3, item1))
    assertEquals(-1, comparator.compare(item1, item3))
  }
}