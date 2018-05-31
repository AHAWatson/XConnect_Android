package com.xpanxion.benchreport

import com.xpanxion.architecture.Person

interface BenchData{
    var RAW: MutableList<Person>
    var SORT: BenchDataSort

    fun getSortedData(): List<Person>
}