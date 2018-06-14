package com.xpanxion.architecture

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class RoleItem(
        val role: Role,
        val persons: MutableList<Person>,
        availability: Availability
) : BenchItem(availability) {
    var graph_maximum = 100f

    constructor(
            person: Person
    ) : this(person.role, mutableListOf(person), CompositeAvailability(listOf(person)))

    override fun getTagView(context: Context): View {
        val view = LayoutInflater.from(context).inflate(R.layout.role_tag_layout, null)
        view.findViewById<TextView>(R.id.role_label).text = role.short_title
        view.findViewById<TextView>(R.id.role_count).text = "(${persons.size})"
        return view
    }

    override fun getGraphMaximum() = graph_maximum
}