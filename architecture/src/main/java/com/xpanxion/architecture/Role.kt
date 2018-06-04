package com.xpanxion.architecture

sealed class Role(val short_title: String, val long_title: String, val degree: Int) {
    override fun toString(): String{
        return "$long_title ${degree.toRomanNumeral()}"
    }

    fun sameTitle(other: Role): Boolean{
        return long_title == other.long_title
    }

    override fun equals(other: Any?): Boolean {
        return other is Role && other.long_title == long_title && other.degree == degree
    }
}

class Qa(level: Int) : Role("QA", "Quality Assurance", level)
class Sdet(level: Int) : Role("SDET", "SDET", level)
class DevOps(level: Int) : Role("DevOps", "DevOps", level)
class Developer(level: Int) : Role("Dev", "Developer", level)
class FunctionalAnalyst(level: Int) : Role("FA","Functional Analyst", level)
