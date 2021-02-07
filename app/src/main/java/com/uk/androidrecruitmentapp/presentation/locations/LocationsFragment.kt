package com.uk.androidrecruitmentapp.presentation.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uk.androidrecruitmentapp.databinding.LocationsFragmentBinding
import com.uk.androidrecruitmentapp.presentation.base.BaseFragment
import com.uk.androidrecruitmentapp.presentation.locations.list.LocationsAdapter
import com.uk.androidrecruitmentapp.presentation.utils.addOnScrolledEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : BaseFragment() {

    private lateinit var binding: LocationsFragmentBinding

    private val viewModel: LocationsVM by viewModels<LocationsVMImpl>()

    private val locationsAdapter by lazy { LocationsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LocationsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observe()
    }

    private fun setupList() {
        binding.locationsRV.apply {
            adapter = locationsAdapter
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = linearLayoutManager
            addOnScrolledEvent {
                viewModel.onListScrolled(linearLayoutManager.findLastCompletelyVisibleItemPosition(), locationsAdapter.itemCount)
            }
        }
    }

    private fun observe() {
        viewModel.run {
            progressVisibility.observe(viewLifecycleOwner, {
                if (it) {
                    binding.locationsLoadPB.visibility = View.VISIBLE
                } else {
                    binding.locationsLoadPB.visibility = View.GONE
                }
            })
            locationsList.observe(viewLifecycleOwner, {
                locationsAdapter.submitData(it.toMutableList())
            })
            toastMessage.observe(viewLifecycleOwner, { errorMsgResId ->
                context?.let { Toast.makeText(it, errorMsgResId, Toast.LENGTH_SHORT).show() }
            })
            loadingMoreVisibility.observe(viewLifecycleOwner, {
                if (it) {
                    locationsAdapter.showLoading()
                } else {
                    locationsAdapter.hideLoading()
                }
            })
        }
    }
}
