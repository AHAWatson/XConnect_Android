package com.xpanxion.benchreport

import android.content.res.Resources
import com.xpanxion.architecture.*
import com.xpanxion.architecture.BenchDataSort.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook


import java.util.*

class UserBenchData(res: Resources) : BenchData {

    override var raw: MutableList<Person> = mutableListOf()
        set(value) {
            field = value
            updateMap()
            updateRoles()
        }
    override var sort: BenchDataSort = BenchDataSort.ALL
    val MAP: MutableMap<Long, Person> = HashMap()
    var roleItems: MutableList<RoleItem> = mutableListOf()
    var resources = res

    init {
        raw = buildData()
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
                    result[0].updateAvailability()
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

    override fun getSortedData(): List<BenchItem> {
        return when (sort) {
            ALL -> {
                raw.sortedBy { it.availability }
            }
            ROLE -> {
                roleItems
            }
            FAVORITE ->{
                raw.filter { it.starred }
            }
            else -> {
                raw.filter { it.role.sort == sort }.sortedBy { it.availability }
            }
        }
    }

    private fun addItem(person: Person) {
        raw.add(person)
        MAP[person.id] = person
    }

    private fun buildData(): MutableList<Person>{
        val result = mutableListOf<Person>()

        try{
            val excelFile = resources.openRawResource(R.raw.benchreportsheet)
            val workbook = XSSFWorkbook(excelFile)

            val sheet = workbook.getSheet("US Bench")
            val rows = sheet.iterator()
            rows.next()
            var infocells = rows.next().iterator()
            infocells.next();infocells.next();infocells.next();infocells.next();infocells.next()
            val months = listOf(infocells.next().toString(),infocells.next().toString(),infocells.next().toString(),infocells.next().toString(),infocells.next().toString(),infocells.next().toString())
            rows.next()
            while (rows.hasNext()) {
                val currentRow = rows.next()
                val cellsInRow = currentRow.iterator()
                var id = cellsInRow.next().numericCellValue
                var name = cellsInRow.next().toString()
                var location = cellsInRow.next().toString()
                var practice = cellsInRow.next().toString()
                var track = cellsInRow.next().toString()
                var availability = arrayOf(cellsInRow.next().toString(),cellsInRow.next().toString(),cellsInRow.next().toString(),cellsInRow.next().toString(),cellsInRow.next().toString(),cellsInRow.next().toString())
                var lbd = cellsInRow.next().toString()
                var lastAcount = cellsInRow.next().toString()
                var tempPerson=Person(id.toLong(),
                        getRole(track),
                        getName(name),
                        getLocation(location),
                        createFalseSkills(),
                        getAvailability(availability,months),
                        false,
                        "1234567890",
                        "jmarvin@xpanxion.com"
                        )
                result.add(tempPerson)
                addItem(tempPerson)
            }

            workbook.close()
            excelFile.close()

        }catch(e:Exception){
            println("FILE EXCEPTION: " + e)
        }
        return result
    }

    private fun getAvailability(availability: Array<String>, months: List<String>): Availability {
        var rawData: Array<Float> = arrayOf(availability[0].toFloat() * 100,availability[1].toFloat() * 100,availability[2].toFloat() * 100,availability[3].toFloat() * 100,availability[4].toFloat() * 100,availability[5].toFloat() * 100)
        return Availability(rawData,months)

    }

    private fun getRole(track: String): Role {
        var cellRole = track.substringAfter(" - ")
        var level = 2
        cellRole = cellRole.substringBeforeLast(" ")
        return when (cellRole) {
            "Quality Assurance" -> Qa(level)
            "SDET" -> Sdet(level)
            "DevOps" -> DevOps(level)
            "Developer" -> Developer(level)
            "Functional Analyst" -> FunctionalAnalyst(level)
            else -> {
                Sdet(1)
            }
        }
    }

    private fun getName(name: String): Name{
        return Name(name.substringBefore(" "),name.substringAfterLast(" "))
    }

    private fun getLocation(loc: String): Location{
        return when(loc.substringBefore(" - ")){
            "Ames" -> Location.AMES
            "Kearney" -> Location.KEARNEY
            "Atlanta" -> Location.ATLANTA
            "Manhattan" -> Location.MANHATTAN
            "Remote" -> Location.REMOTE
            "Fort Collins" -> Location.FORT_COLLINS
            "Lincoln" -> Location.LINCOLN
            "Pune" -> Location.PUNE
            else -> {
                Location.REMOTE
            }
        }
    }


    private fun createFalseSkills(): Array<Skill> {
        val number = 3 + Random().nextInt(3)
        return Array(number, { i ->
            Skill.subClassInstances.shuffled()[0]
        }
        )
    }
}