package com.xpanxion.benchreport

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xpanxion.architecture.AvailabilityGraph
import com.xpanxion.architecture.BenchItem
import com.xpanxion.architecture.Person
import com.xpanxion.architecture.RoleItem
import kotlinx.android.synthetic.main.bench_list_item.view.*

class BenchItemRecyclerViewAdapter(
        benchData: BenchData,
        private val sortable: SortableBenchData,
        private val listener: BenchFragment.BenchFragmentManager?
) : RecyclerView.Adapter<BenchItemRecyclerViewAdapter.PersonViewHolder>() {
    private val values: List<BenchItem> = benchData.getSortedData()
    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as BenchItem
            listener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bench_list_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val benchItem = values[position]
        holder.tagLayout.removeAllViews()
        holder.tagLayout.addView(benchItem.getTagView(holder.view.context))
        holder.availabilityGraph.months = benchItem.availability.months
        holder.availabilityGraph.maximum = benchItem.getGraphMaximum()
        holder.availabilityGraph.availability = benchItem.availability.rawData
        with(holder.view) {
            tag = benchItem
            when (benchItem) {
                is Person -> {
                    setOnClickListener(onClickListener)
                }
                is RoleItem -> {
                    setOnClickListener{sortable.subSort(benchItem.role.sort)}
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class PersonViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tagLayout: ViewGroup = view.tag_layout
        val availabilityGraph: AvailabilityGraph = view.availability_graph
    }
}
