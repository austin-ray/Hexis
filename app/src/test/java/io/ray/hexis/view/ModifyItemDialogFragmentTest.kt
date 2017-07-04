package io.ray.hexis.view

import org.junit.Test

class ModifyItemDialogFragmentTest {
  @Test
  @Throws(Exception::class)
  fun validateInput() {
    val dialog: ModifyItemDialogFragment = ModifyItemDialogFragment()
    dialog.validateInput()
  }
}