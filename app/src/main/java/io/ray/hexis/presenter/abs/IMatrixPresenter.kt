package io.ray.hexis.presenter.abs

import android.support.v4.view.ViewPager
import io.ray.hexis.model.QuadrantItem
import io.ray.hexis.model.abs.IMatrixModel
import io.ray.hexis.model.abs.IQuadrantModel
import io.ray.hexis.view.abs.IMatrixFragment

/**
 * Presenter for an IMatrixFragment.
 */
interface IMatrixPresenter : IPresenter<IMatrixFragment, IMatrixModel> {

  // ViewPager from the managed fragment
  val pager : ViewPager

  /**
   * Add a QuadrantItem to a specified quadrant.
   * @param quadrant    Specific quadrant to add the item
   * @param item        New item
   */
  fun addItem(quadrant: Int, item: QuadrantItem)

  /**
   * Return the IQuadrantModel for a specified quadrant.
   * @param quadrant  Quadrant that data is requested from
   * @return          IQuadrantModel for a quadrant
   */
  fun getQuadrantData(quadrant: Int): IQuadrantModel

  /**
   * Set the data set for a given Quadrant.
   * @param quadrant    Specific quadrant
   * @param data        New data set
   */
  fun setQuadrantData(quadrant: Int, data: List<QuadrantItem>)

  /**
   * Notify the DB that an item has been removed from the DB.
   */
  fun notifyItemRemoved(item: QuadrantItem)

  /**
   * Notify the DB that an item has been modified.
   * @param item      Modified QuadrantItem
   * @param quadrant  Specific quadrant
   */
  fun notifyItemModified(item: QuadrantItem, quadrant: Int)
}

