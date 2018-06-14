package com.xpanxion.benchreport

import com.xpanxion.architecture.BenchDataSort

interface SortableBenchData {
    fun sortBy(sort: BenchDataSort?)

    fun subSort(sort: BenchDataSort?)
}