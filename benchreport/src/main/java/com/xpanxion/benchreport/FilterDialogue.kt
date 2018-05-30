package com.xpanxion.benchreport

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

class FilterDialogue : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Sort By:")
        val sorts = Array(BenchDataSort.values().size){""}
        BenchDataSort.values().forEachIndexed { index, sort ->
            sorts[index] = sort.label
        }
        builder.setSingleChoiceItems(sorts, 0) { dialog, which -> }
        return builder.create()
    }
}