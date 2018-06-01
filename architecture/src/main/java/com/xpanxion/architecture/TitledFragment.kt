package com.xpanxion.architecture

import android.content.Context
import android.support.v4.app.Fragment

abstract class TitledFragment : Fragment() {
    var title: String? = null
    var titledFragmentManager: TitledFragmentManager? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TitledFragmentManager) {
            titledFragmentManager = context
            context.onTitleUpdated(title)
        } else {
            throw RuntimeException(context.toString() + " must implement TitledFragmentManager")
        }
    }

    override fun onStart() {
        super.onStart()
        titledFragmentManager?.onTitleUpdated(title)
    }
}