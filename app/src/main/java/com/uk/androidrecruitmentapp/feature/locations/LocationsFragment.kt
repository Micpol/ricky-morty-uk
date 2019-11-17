package com.uk.androidrecruitmentapp.feature.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uk.androidrecruitmentapp.BaseFragment
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.feature.locations.list.LocationsAdapter
import com.uk.androidrecruitmentapp.utils.getVM
import kotlinx.android.synthetic.main.fragment_locations.*
import javax.inject.Inject

class LocationsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: LocationsVM by lazy { getVM<LocationsVM>(viewModelFactory) }

    private val locationsAdapter by lazy { LocationsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observe()
    }

    private fun setupList() {
        locationsRV.apply {
            adapter = locationsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun observe() {
        viewModel.progressVisibility.observe(this, Observer {
            if (it) {
                locationsLoadPB.visibility = View.VISIBLE
            } else {
                locationsLoadPB.visibility = View.GONE
            }
        })
        viewModel.locationsList.observe(this, Observer {
            locationsAdapter.submitData(it.toMutableList())
        })
        viewModel.toastMessage.observe(this, Observer { errorMsgResId ->
            context?.let { Toast.makeText(it, errorMsgResId, Toast.LENGTH_SHORT).show() }
        })
    }
}
