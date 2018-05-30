package com.xpanxion.architecture

data class Availability(val rawData: List<Float>, val months: List<String>): Comparable<Availability>{
    init {
        if(rawData.size > 6){
            throw IllegalArgumentException("No more than six months of availability permitted")
        }
    }

    override fun compareTo(other: Availability): Int {
        rawData.forEachIndexed { index, value ->
            if(value != other.rawData[index]){
                return (other.rawData[index] - value).toInt()
            }
        }
        return 0
    }
}