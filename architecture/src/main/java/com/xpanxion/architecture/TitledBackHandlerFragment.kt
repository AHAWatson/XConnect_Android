package com.xpanxion.architecture

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

abstract class TitledBackHandlerFragment : Fragment() {
    var title: String? = null
    var titledFragmentManager: TitledFragmentManager? = null
    var backHandler: BackHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(activity is BackHandler){
            backHandler = activity as BackHandler
        } else {
            throw ClassCastException("Hosting activity must implement BackHandler")
        }
    }

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
        backHandler?.activeFragment = this
    }

    abstract fun onBackPressed():Boolean

    interface BackHandler{
        var activeFragment: TitledBackHandlerFragment?
    }
}