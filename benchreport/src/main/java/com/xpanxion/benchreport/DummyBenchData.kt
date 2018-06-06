package com.xpanxion.benchreport

import com.xpanxion.architecture.*
import java.util.*

class DummyBenchData : BenchData {
    override var raw: MutableList<Person> = mutableListOf()
        set(value) {
            field = value
            updateMap()
            updateRoles()
        }
    override var sort: BenchDataSort = BenchDataSort.AVAILABILITY
    private val COUNT = Random().nextInt(30)
    val MAP: MutableMap<Long, Person> = HashMap()
    var roleItems: MutableList<RoleItem> = mutableListOf()

    init {
        raw = buildFalseData()
    }

    private fun updateMap() {
        for (person in raw) {
            MAP[person.id] = person
        }
    }

    private fun updateRoles() {
        for (person in raw) {
            roleItems.filter { it.role.sameTitle(person.role) }.let { result ->
                if (result.isEmpty()) {
                    roleItems.add(RoleItem(person))
                } else {
                    result[0].persons.add(person)
                }
            }
        }
        val sortedRoles = roleItems.sortedBy { it.availability.rawData.max() }
        if (!sortedRoles.isEmpty()) {
            val maximum = sortedRoles[sortedRoles.lastIndex].availability.rawData.max()
            maximum?.let {
                for (roleItem in roleItems) {
                    roleItem.graph_maximum = maximum
                }
            }
        }
    }

    private fun buildFalseData(): MutableList<Person> {
        val result = mutableListOf<Person>()
        for (i in 1..COUNT) {
            val falsePerson = createFalsePerson()
            result.add(falsePerson)
            MAP[falsePerson.id] = falsePerson
            addItem(createFalsePerson())
        }
        return result
    }

    override fun getSortedData(): List<BenchItem> {
        return when (sort) {
            BenchDataSort.AVAILABILITY -> {
                raw.sortedBy { it.availability }
            }
            BenchDataSort.ROLE -> {
                roleItems
            }
        }
    }

    private fun addItem(person: Person) {
        raw.add(person)
        MAP[person.id] = person
    }

    private fun createFalsePerson(): Person {
        return Person(
                Random().nextLong(),
                createFalseRole(),
                createFalseName(),
                createFalseLocation(),
                createFalseSkills(),
                createFalseAvailability()
        )
    }

    private fun createFalseSkills(): Array<Skill> {
        val number = 3 + Random().nextInt(3)
        return Array(number, { i ->
            Skill.subClassInstances.shuffled()[0]
        }
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
        val points = arrayOf(
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
