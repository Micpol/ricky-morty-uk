package com.uk.androidrecruitmentapp.feature.locations.list.viewholder

import android.view.View
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.episodes_item.*

class LocationsViewHolder(

        override val containerView: View

) : BaseViewHolder<Location>(containerView), LayoutContainer {

    override fun bind(item: Location) {
        setupVH(item)
    }

    private fun setupVH(location: Location?) {
        location?.let {
            episode_name.text = it.name
        } ?: episode_name.setText(R.string.placeholder)
    }

    override fun clear() {
        setupVH(null)
    }
}