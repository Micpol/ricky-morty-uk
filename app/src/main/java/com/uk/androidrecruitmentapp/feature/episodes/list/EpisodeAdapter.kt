package com.uk.androidrecruitmentapp.feature.episodes.list

import android.view.ViewGroup
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Episode
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.feature.base.BaseAdapter
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder
import com.uk.androidrecruitmentapp.feature.episodes.list.viewholder.EpisodeViewHolder
import com.uk.androidrecruitmentapp.feature.locations.list.viewholder.LoadingViewHolder
import com.uk.androidrecruitmentapp.feature.locations.list.viewholder.LocationsViewHolder
import com.uk.androidrecruitmentapp.utils.layoutInflater

class EpisodeAdapter : BaseAdapter<Episode>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = parent.context.layoutInflater
        return when (viewType) {
            PROGRESS_BAR -> {
                val view = layoutInflater.inflate(R.layout.loading_item, parent, false)
                LoadingViewHolder(view)
            }
            ITEM -> {
                val view = layoutInflater.inflate(R.layout.episodes_item, parent, false)
                EpisodeViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is LoadingViewHolder -> {
                holder.bind(data[position])
            }
            is EpisodeViewHolder -> {
                data[position]?.let {
                    holder.bind(it)
                }
            }
        }
    }
}