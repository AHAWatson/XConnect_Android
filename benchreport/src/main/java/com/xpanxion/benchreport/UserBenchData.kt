package com.xpanxion.benchreport

import android.content.res.Resources
import com.xpanxion.architecture.*
import com.xpanxion.architecture.BenchDataSort.*
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.InputStream

import java.util.*

class UserBenchData(file: InputStream) : BenchData {

    override var raw: MutableList<Person> = mutableListOf()
        set(value) {
            field = value
            updateMap()
            updateRoles()
        }
    override var sort: BenchDataSort = BenchDataSort.ALL
    val MAP: MutableMap<Long, Person> = HashMap()
    var roleItems: MutableList<RoleItem> = mutableListOf()
    var file = file

    init {
        buildData()
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
    private fun buildData(){

        try{
            val excelFile = file
            val workbook = XSSFWorkbook(excelFile)

            val sheet = workbook.getSheet("US Bench")
            val rows = sheet.iterator()
            while (rows.hasNext()) {
                val currentRow = rows.next()
                val cellsInRow = currentRow.iterator()
                while (cellsInRow.hasNext()) {
                    val currentCell = cellsInRow.next()
                    if (currentCell.getCellTypeEnum() === CellType.STRING) {
                        print(currentCell.getStringCellValue() + " | ")
                    } else if (currentCell.getCellTypeEnum() === CellType.NUMERIC) {
                        print(currentCell.getNumericCellValue().toString() + "(numeric)")
                    }
                }

                println()
            }

            workbook.close()
            excelFile.close()

        }catch(e:Exception){
            println("FILE EXCEPTION: " + e)
        }
    }
}