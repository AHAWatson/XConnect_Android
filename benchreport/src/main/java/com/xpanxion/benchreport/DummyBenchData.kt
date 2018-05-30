package com.xpanxion.benchreport

import com.xpanxion.architecture.*
import java.util.*

class DummyBenchData : BenchData {
    override var RAW: MutableList<Person> = mutableListOf()
    override var SORT: BenchDataSort = BenchDataSort.AVAILABILITY
    override var FILTER: BenchDataFilter = BenchDataFilter.ALL
    private val COUNT = Random().nextInt(30)
    val MAP: MutableMap<Long, Person> = HashMap()

    init {
        for (i in 1..COUNT) {
            addItem(createFalsePerson())
        }
    }

    override fun getSortedFilteredData(): List<Person> {
        return RAW.sortedBy { it.availability }
    }

    private fun addItem(person: Person) {
        RAW.add(person)
        MAP[person.id] = person
    }

    private fun createFalsePerson(): Person {
        return Person(
                Random().nextLong(),
                createFalseRole(),
                createFalseName(),
                createFalseLocation(),
                createFalseAvailability()
        )
    }

    private fun createFalseName(): Name {
        val firstNames = listOf("Ignatius", "Athanasius", "Sixtus", "Sebastian", "Charles", "Benjamin", "Richard", "Raymond", "Godfrey")
        val lastNames = listOf("Watson", "Butcher", "Hapsburg", "Aquinas", "Stuart", "Lee")
        val first = firstNames.shuffled().take(1)[0]
        val last = lastNames.shuffled().take(1)[0]
        return Name(first, last)
    }

    private fun createFalseRole(): Role {
        return when (Random().nextInt(5)) {
            0 -> Qa(Random().nextInt(3) + 1)
            1 -> Sdet(Random().nextInt(3) + 1)
            2 -> DevOps(Random().nextInt(3) + 1)
            3 -> Developer(Random().nextInt(3) + 1)
            4 -> FunctionalAnalyst(Random().nextInt(3) + 1)
            else -> {
                Sdet(1)
            }
        }
    }

    private fun createFalseLocation(): Location {
        return Location.values()[Random().nextInt(Location.values().size)]
    }

    private fun createFalseAvailability(): Availability {
        val points = listOf(
                Random().nextInt(3) * 50.toFloat(),
                Random().nextInt(3) * 50.toFloat(),
                Random().nextInt(3) * 50.toFloat(),
                Random().nextInt(3) * 50.toFloat(),
                Random().nextInt(3) * 50.toFloat(),
                Random().nextInt(3) * 50.toFloat()
        )
        val months = listOf("May", "June", "July", "August", "September", "October")
        return Availability(points, months)
    }
}
