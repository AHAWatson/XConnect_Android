package com.xpanxion.architecture

import android.os.Parcel
import android.os.Parcelable

open class Skill(val name: String, val image: Int = R.drawable.skill) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Skill> {
        val subClassInstances = listOf(
                Agile, Angular, Appium, AspNet, Aws, Azure, CSharp, Cucumber, Docker, Hibernate,
                Html, Iis, Java, Javascript, Kotlin, MongoDb, Net, NodeJs, Ruby, Scala, Selenium,
                Spring, Sql, Swift, TestNg, VisualBasic, VmWare, Webhooks
        )

        override fun createFromParcel(parcel: Parcel): Skill {
            return Skill(parcel)
        }

        override fun newArray(size: Int): Array<Skill?> {
            return arrayOfNulls(size)
        }
    }

    object Agile : Skill("Agile", R.drawable.agile)
    object Angular : Skill("AngularJS", R.drawable.angular)
    object Appium : Skill("Appium", R.drawable.appium)
    object AspNet : Skill("ASP.NET", R.drawable.net)
    object Aws : Skill("AWS", R.drawable.aws)
    object Azure : Skill("Azure", R.drawable.azure)
    object CSharp : Skill("C#", R.drawable.c_sharp)
    object Cucumber : Skill("cucumber", R.drawable.cucumber)
    object Docker : Skill("Docker", R.drawable.docker)
    object Hibernate : Skill("Hibernate", R.drawable.hibernate)
    object Html : Skill("HTML", R.drawable.html)
    object Iis : Skill("IIS", R.drawable.microsoft)
    object Java : Skill("Java", R.drawable.java)
    object Javascript : Skill("Javascript", R.drawable.javascript)
    object Kotlin : Skill("Kotlin", R.drawable.kotlin)
    object MongoDb : Skill("mongoDB", R.drawable.mongo_db)
    object Net : Skill(".NET", R.drawable.net)
    object NodeJs : Skill("Node.js", R.drawable.node_js)
    object Ruby : Skill("Ruby", R.drawable.ruby)
    object Scala : Skill("Scala", R.drawable.scala)
    object Selenium : Skill("Selenium", R.drawable.selenium)
    object Spring : Skill("spring", R.drawable.spring)
    object Sql : Skill("SQL", R.drawable.sql)
    object Swift : Skill("Swift", R.drawable.swift)
    object TestNg : Skill("TestNG", R.drawable.test_ng)
    object VisualBasic : Skill("Visual Basic", R.drawable.visual_basic)
    object VmWare : Skill("VMWare", R.drawable.vmware)
    object Webhooks : Skill("Webhooks", R.drawable.webhooks)
}
