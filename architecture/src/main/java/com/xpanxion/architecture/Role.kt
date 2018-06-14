package com.xpanxion.architecture

sealed class Role(val sort: BenchDataSort, val short_title: String, val long_title: String, val degree: Int) {
    override fun toString(): String {
        return "$long_title ${degree.toRomanNumeral()}"
    }

    fun sameTitle(other: Role): Boolean {
        return long_title == other.long_title
    }

    override fun equals(other: Any?): Boolean {
        return other is Role && other.long_title == long_title && other.degree == degree
    }
}

class Qa(level: Int) : Role(BenchDataSort.QA, "QA", "Quality Assurance", level)
class Sdet(level: Int) : Role(BenchDataSort.SDET, "SDET", "SDET", level)
class DevOps(level: Int) : Role(BenchDataSort.DEVOPS, "DevOps", "DevOps", level)
class Developer(level: Int) : Role(BenchDataSort.DEV, "Dev", "Developer", level)
class FunctionalAnalyst(level: Int) : Role(BenchDataSort.FA, "FA", "Functional Analyst", level)
