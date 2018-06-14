package com.xpanxion.architecture

import android.content.Context
import android.view.View

abstract class BenchItem(
        val availability: Availability
) {
    abstract fun getTagView(context: Context): View

    abstract fun getGraphMaximum(): Float
}