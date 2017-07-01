package io.ray.hexis.presenter

import android.support.v4.view.ViewPager

import io.ray.hexis.model.MatrixModel
import io.ray.hexis.model.QuadrantItem
import io.ray.hexis.model.abs.IMatrixModel
import io.ray.hexis.model.abs.IQuadrantModel
import io.ray.hexis.presenter.abs.IMatrixPresenter
import io.ray.hexis.util.QuadrantItemWriter
import io.ray.hexis.util.SqlLiteHelper
import io.ray.hexis.view.abs.IMatrixFragment

/**
 * Implementation of an IMatrixPresenter.
 * @param fragment  Fragment handled by the presenter
 * @param model     Model handled by the presenter
 * @param helper    SqlLiteHelper reference used to interact with the DB
 */
class MatrixPresenter(private val fragment: IMatrixFragment, private var model: IMatrixModel,
                      private val helper: SqlLiteHelper) : IMatrixPresenter {

  override val pager: ViewPager
    get() = fragment.pager

  /**
   * Add an item from a specific quadrant to the DB.
   * @param quadrant    Quadrant index
   * @param item        New Item
   */
  override fun addItem(quadrant: Int, item: QuadrantItem) {
    // Insert to database and get the UID
    val uid: Long = QuadrantItemWriter(helper).insertNewItem(0, quadrant, item.message)

    // Get the data for a quadrant
    val data: MutableList<QuadrantItem> = getQuadrantData(quadrant).data

    // Remove the item from the data set
    data.remove(item)

    // Add a new item with the UID provided from the DB.
    data.add(QuadrantItem(item.message, uid))
  }

  /**
   * Remove an item from a Quadrant.
   * @param item    Item to be removed from the database
   */
  override fun notifyItemRemoved(item: QuadrantItem) {
    QuadrantItemWriter(helper).removeItem(item)
  }

  /**
   * Update the database because an item has been modified.
   * @param item      Modified QuadrantItem
   * @param quadrant  Quadrant that the item belongs to
   */
  override fun notifyItemModified(item: QuadrantItem, quadrant: Int) {
    QuadrantItemWriter(helper).updateItem(item, quadrant, -1)
  }

  /**
   * Return the IQuadrantModel for a specified quadrant.
   * @param quadrant Quadrant that data is requested from
   * @return IQuadrantModel for a quadrant
   */
  override fun getQuadrantData(quadrant: Int): IQuadrantModel = model.getQuadrant(quadrant)

  /**
   * Return the managed IMatrixFragment.
   */
  override fun getFragment(): IMatrixFragment = fragment

  /**
   * Return the managed IMatrixModel.
   */
  override fun getModel(): IMatrixModel = model

  /**
   * Set the data set for a specific quadrant.
   * @param quadrant  Specific quadrant
   * @param data      Data set
   */
  override fun setQuadrantData(quadrant: Int, data: List<QuadrantItem>) =
      getModel().setQuadrantModel(quadrant, data)
}
