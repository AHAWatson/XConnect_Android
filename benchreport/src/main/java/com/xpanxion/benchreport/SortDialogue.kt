package com.xpanxion.benchreport

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.xpanxion.architecture.BenchDataSort

const val SORT_KEY = "SORT_KEY"

class SortDialogue : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (parentFragment is SortableBenchData) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Sort By:")
            val labels = Array(3) { "" }
            val map = HashMap<String, BenchDataSort>()
            arrayOf(BenchDataSort.ALL, BenchDataSort.ROLE,BenchDataSort.FAVORITE).forEachIndexed { index, sort ->
                labels[index] = sort.label
                map[sort.label] = sort
            }
            val checkedIndex = labels.indexOf(arguments?.getString(SORT_KEY))
            builder.setSingleChoiceItems(labels, checkedIndex) { dialog, which ->
                (parentFragment as SortableBenchData).sortBy(map[labels[which]])
                dismiss()
            }
            return builder.create()
        } else {
            throw RuntimeException("Calling fragment must implement SortableBenchData")
        }
    }
}