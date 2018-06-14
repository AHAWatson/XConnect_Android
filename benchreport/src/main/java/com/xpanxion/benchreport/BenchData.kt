package com.xpanxion.benchreport

import com.xpanxion.architecture.BenchDataSort
import com.xpanxion.architecture.BenchItem
import com.xpanxion.architecture.Person

interface BenchData{
    var raw: MutableList<Person>
    var sort: BenchDataSort

    fun getSortedData(): List<BenchItem>
}