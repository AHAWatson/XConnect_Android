package com.xpanxion.benchreport

import com.xpanxion.architecture.Person

interface BenchData{
    var RAW: MutableList<Person>
    var SORT: BenchDataSort
    var FILTER: BenchDataFilter

    fun getSortedFilteredData(): List<Person>
}