package com.xpanxion.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xpanxion.architecture.Person

import com.xpanxion.architecture.TitledFragment

class ProfileFragment : TitledFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    init {
        title = "Profile"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    companion object {

        fun newInstance(person: Person): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
