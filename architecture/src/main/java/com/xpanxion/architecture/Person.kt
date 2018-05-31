package com.xpanxion.architecture

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class Person(
        val id: Long,
        val role: Role,
        val name: Name,
        val location: Location,
        availability: Availability
): BenchItem(availability) {
    override fun getTagView(context: Context): View {
        val view = LayoutInflater.from(context).inflate(R.layout.person_tag_layout, null)
        view.findViewById<TextView>(R.id.person_name).text = name.toString()
        view.findViewById<TextView>(R.id.person_role).text = role.toString()
        return view
    }

    override fun getGraphMaximum() = 100f
}