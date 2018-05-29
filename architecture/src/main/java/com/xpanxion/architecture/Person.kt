package com.xpanxion.architecture

data class Person(
        val id: Long,
        val role: Role,
        val name: Name,
        val location: Location,
        val availability: Availability
)