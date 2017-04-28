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
class MatrixPresenter(private val fragment: IMatrixFragment, private var model: IMatrixModel?,
                      private val helper: SqlLiteHelper) : IMatrixPresenter {

    /**
     * Add an item to a specific quadrant.
     * @param quadrant Quadrant that QuadrantItem is being added to
     * @param message  Message which will be used to construct a QuadrantItem
     */
    override fun addItem(quadrant: Int, message: String) = model!!.getQuadrant(quadrant)
            .addItem(QuadrantItem(message))

    override fun addItem(quadrant: Int, item: QuadrantItem) {
        // Insert to database and get the UID
        val uid : Long = QuadrantItemWriter(helper).insertNewItem(0, quadrant, item.message)

        // Get the data for a quadrant
        val data : MutableList<QuadrantItem> = getQuadrantData(quadrant).data

        data.remove(item)
        data.add(QuadrantItem(item.message, uid))
    }

    /**
     * Return the IQuadrantModel for a specified quadrant.
     * @param quadrant Quadrant that data is requested from
     * @return IQuadrantModel for a quadrant
     */
    override fun getQuadrantData(quadrant: Int): IQuadrantModel = model!!.getQuadrant(quadrant)

    override fun getFragment(): IMatrixFragment = fragment

    override fun getModel(): IMatrixModel = model!!

    override fun setQuadrantData(quadrant: Int, data: List<QuadrantItem>) {
        if (model != null) {
            getModel().setQuadrantModel(quadrant, data)
        } else {
            model = MatrixModel()
            model!!.setQuadrantModel(quadrant, data)
        }
    }

    /**
     * Returns the ViewPager used in the Matrix Fragment
     * @return  Return the managed Fragment's ViewPager
     */
    override fun getPager(): ViewPager {
        return fragment.pager
    }

    override fun notifyItemRemoved(item: QuadrantItem) {
        QuadrantItemWriter(helper).removeItem(item)
    }

    override fun notifyItemModified(item: QuadrantItem, quadrant: Int) {
        QuadrantItemWriter(helper).updateItem(item, quadrant, -1)
    }
}
