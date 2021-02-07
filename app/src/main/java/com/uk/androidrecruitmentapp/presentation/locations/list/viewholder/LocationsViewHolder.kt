package com.uk.androidrecruitmentapp.presentation.locations.list.viewholder

import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.databinding.LocationsItemBinding
import com.uk.androidrecruitmentapp.presentation.base.BaseViewHolder

class LocationsViewHolder(
    private val binding: LocationsItemBinding
) : BaseViewHolder<Location>(binding.root) {

    override fun bind(item: Location) {
        setupVH(item)
    }

    private fun setupVH(location: Location?) {
        location?.let {
            binding.episodeName.text = it.name
        } ?: binding.episodeName.setText(R.string.placeholder)
    }

    override fun clear() {
        setupVH(null)
    }
}