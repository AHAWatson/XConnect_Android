package com.xpanxion.architecture

data class Name(
        val first: String,
        val last: String
){
    override fun toString() = "$first $last"
}