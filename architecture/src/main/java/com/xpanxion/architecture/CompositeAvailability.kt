package com.xpanxion.architecture

private fun compileRawData(persons: List<Person>): Array<Float> {
    val result = arrayOf(0f,0f,0f,0f,0f,0f)
    for (person in persons) {
        person.availability.rawData.forEachIndexed { index, value ->
            result[index] += (value / 100f)
        }
    }
    return result
}

class CompositeAvailability(
        persons: List<Person>
) : Availability(compileRawData(persons), persons[0].availability.months) {
    var persons: List<Person> = persons
        set(value) {
            field = value
            rawData = compileRawData(value)
        }
}