package com.xpanxion.architecture

data class Availability(val rawData: List<Float>){
    init {
        if(rawData.size > 6){
            throw IllegalArgumentException("No more than six months of availability permitted")
        }
    }
}