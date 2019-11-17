package com.uk.androidrecruitmentapp.feature.locations.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.feature.locations.list.viewholder.LocationsViewHolder

class LocationsAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<LocationsViewHolder>() {

    private val locationResults = mutableListOf<Location>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.locations_item, parent, false)
        return LocationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val episode = locationResults[position]
        holder.bind(episode)
    }

    override fun onViewRecycled(holder: LocationsViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    override fun getItemCount(): Int {
        return locationResults.size
    }

    fun submitData(data: MutableList<Location>) {
        locationResults.clear()
        locationResults.addAll(data)
        notifyDataSetChanged()
    }
}