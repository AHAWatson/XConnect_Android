package com.xpanxion.benchreport

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xpanxion.architecture.BenchDataSort
import com.xpanxion.architecture.BenchItem
import com.xpanxion.architecture.TitledBackHandlerFragment
import kotlinx.android.synthetic.main.fragment_layout.*

class BenchFragment : TitledBackHandlerFragment(), SortableBenchData {

    private lateinit var benchData: BenchData
    init {
        title = "Availability"
    }

    private var columnCount = 1
    private var benchFragmentManager: BenchFragmentManager? = null
    private var sortStack: MutableList<BenchDataSort> = mutableListOf()

    companion object {
        val TAG = "BENCH_REPORT"
        const val ARG_COLUMN_COUNT = "column-count"
        @JvmStatic
        fun newInstance(columnCount: Int) =
                BenchFragment().apply {
                    arguments = Bundle().apply { putInt(ARG_COLUMN_COUNT, columnCount) }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { columnCount = it.getInt(ARG_COLUMN_COUNT) }
        benchData = UserBenchData(resources)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_layout, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.bench_report_recycler_view)
        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = BenchItemRecyclerViewAdapter(benchData, this@BenchFragment, benchFragmentManager)
            }
            recyclerView.addOnScrollListener(
                    object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                            if (dy > 5) {
                                fab.hide()
                            } else if (dy < 0) {
                                fab.show()
                            }
                        }
                    }
            )
        }
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val dialog = SortDialogue()
            val args = Bundle()
            args.putString(SORT_KEY, benchData.sort.label)
            dialog.arguments = args
            dialog.show(childFragmentManager, "sort_fragment")
        }
        return view
    }

    override fun sortBy(sort: BenchDataSort?) {
        sort?.let {
            benchData.sort = sort
            bench_report_recycler_view.adapter = BenchItemRecyclerViewAdapter(benchData, this@BenchFragment, benchFragmentManager)
        }
    }

    override fun subSort(sort: BenchDataSort?) {
        sort?.let {
            sortStack.add(benchData.sort)
            sortBy(sort)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BenchFragmentManager) {
            benchFragmentManager = context
        } else {
            throw RuntimeException(context.toString() + " must implement BenchFragmentManager")
        }
    }

    override fun onDetach() {
        super.onDetach()
        benchFragmentManager = null
    }

    override fun onBackPressed(): Boolean {
        return if (!sortStack.isEmpty()) {
            val lastSort = sortStack.last()
            sortStack.removeAt(sortStack.lastIndex)
            sortBy(lastSort)
            true
        } else {
            false
        }
    }

    interface BenchFragmentManager {
        fun onListFragmentInteraction(item: BenchItem?)
    }
}
