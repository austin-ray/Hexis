package io.ray.hexis.util

import io.ray.hexis.model.QuadrantItem

class QuadrantItemComparator : Comparator<QuadrantItem> {
    override fun compare(o1: QuadrantItem?, o2: QuadrantItem?): Int {
        if (o1?.isComplete as Boolean && !(o2?.isComplete as Boolean)) {
           return 1
        } else if (!o1.isComplete && o2?.isComplete as Boolean) {
            return -1
        }

        return 0
    }
}