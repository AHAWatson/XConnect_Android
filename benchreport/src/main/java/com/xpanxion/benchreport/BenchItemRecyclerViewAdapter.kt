package com.xpanxion.benchreport

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.xpanxion.architecture.AvailabilityGraph
import com.xpanxion.architecture.Person
import kotlinx.android.synthetic.main.person_list_item.view.*

class BenchItemRecyclerViewAdapter(
        benchData: BenchData,
        private val listener: BenchFragment.BenchFragmentManager?
) : RecyclerView.Adapter<BenchItemRecyclerViewAdapter.PersonViewHolder>() {
    private val values: List<Person> = benchData.getSortedFilteredData()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Person
            listener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.person_list_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = values[position]
        holder.nameTextView.text = person.name.toString()
        holder.roleTextView.text = person.role.toString()
        holder.availabilityGraph.months = person.availability.months
        holder.availabilityGraph.availability = person.availability.rawData
        with(holder.view) {
            tag = person
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class PersonViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.person_name
        val roleTextView: TextView = view.person_role
        val availabilityGraph: AvailabilityGraph = view.availability_graph

        override fun toString(): String {
            return super.toString() + " '" + nameTextView.text + "'"
        }
    }
}
