package com.xpanxion.architecture

sealed class Role(val title: String, val degree: Int) {
    override fun toString(): String{
        return "$title ${degree.toRomanNumeral()}"
    }
}

class Qa(level: Int) : Role("QA", level)
class Sdet(level: Int) : Role("SDET", level)
class DevOps(level: Int) : Role("DevOps", level)
class Developer(level: Int) : Role("Dev", level)
class FunctionalAnalyst(level: Int) : Role("FA", level)
