package com.xpanxion.benchreport

import com.xpanxion.architecture.*
import java.util.*

object DummyBenchData {

    val ITEMS: MutableList<Person> = ArrayList()
    val ITEM_MAP: MutableMap<Long, Person> = HashMap()
    val MONTHS = listOf("May", "June", "July", "August", "September", "October")

    private val COUNT = Random().nextInt(30)

    init {
        for (i in 1..COUNT) {
            addItem(createFalsePerson())
        }
        ITEMS.sortBy { it.availability }
    }

    private fun addItem(person: Person) {
        ITEMS.add(person)
        ITEM_MAP[person.id] = person
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
        return Availability(points)
    }
}
