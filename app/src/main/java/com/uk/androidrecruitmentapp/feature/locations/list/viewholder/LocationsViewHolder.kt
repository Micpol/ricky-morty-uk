package com.uk.androidrecruitmentapp.feature.locations.list.viewholder

import android.view.View
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Location
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.episodes_item.*

class LocationsViewHolder(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(location: Location?) {
        setupVH(location)
    }

    private fun setupVH(location: Location?) {
        location?.let {
            episode_name.text = it.name
        } ?: episode_name.setText(R.string.placeholder)
    }

    fun clear() {
        setupVH(null)
    }
}