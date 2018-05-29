package com.xpanxion.architecture

data class Availability(val points: List<Float>){
    init {
        if(points.size > 6){
            throw IllegalArgumentException("No more than six months of availability permitted")
        }
    }
}