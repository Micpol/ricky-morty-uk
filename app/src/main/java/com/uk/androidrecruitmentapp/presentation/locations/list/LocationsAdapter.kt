package com.uk.androidrecruitmentapp.presentation.locations.list

import android.view.ViewGroup
import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.databinding.LoadingItemBinding
import com.uk.androidrecruitmentapp.databinding.LocationsItemBinding
import com.uk.androidrecruitmentapp.presentation.base.BaseAdapter
import com.uk.androidrecruitmentapp.presentation.base.BaseViewHolder
import com.uk.androidrecruitmentapp.presentation.locations.list.viewholder.LoadingViewHolder
import com.uk.androidrecruitmentapp.presentation.locations.list.viewholder.LocationsViewHolder
import com.uk.androidrecruitmentapp.presentation.utils.layoutInflater

class LocationsAdapter : BaseAdapter<Location>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = parent.context.layoutInflater
        return when (viewType) {
            PROGRESS_BAR -> {
                LoadingViewHolder(LoadingItemBinding.inflate(layoutInflater))

            }
            ITEM -> {
                LocationsViewHolder(LocationsItemBinding.inflate(layoutInflater))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is LoadingViewHolder -> {
                holder.bind(data[position])
            }
            is LocationsViewHolder -> {
                data[position]?.let {
                    holder.bind(it)
                }
            }
        }
    }

}