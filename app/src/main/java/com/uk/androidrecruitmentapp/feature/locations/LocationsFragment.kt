package com.uk.androidrecruitmentapp.feature.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uk.androidrecruitmentapp.BaseFragment
import com.uk.androidrecruitmentapp.R

class LocationsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_locations, container, false)
    }
}