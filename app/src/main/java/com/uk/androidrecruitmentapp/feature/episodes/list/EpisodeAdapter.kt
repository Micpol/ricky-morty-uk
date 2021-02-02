package com.uk.androidrecruitmentapp.feature.episodes.list

import android.view.ViewGroup
import com.uk.androidrecruitmentapp.data.local.Episode
import com.uk.androidrecruitmentapp.databinding.EpisodesItemBinding
import com.uk.androidrecruitmentapp.databinding.LoadingItemBinding
import com.uk.androidrecruitmentapp.feature.base.BaseAdapter
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder
import com.uk.androidrecruitmentapp.feature.episodes.list.viewholder.EpisodeViewHolder
import com.uk.androidrecruitmentapp.feature.locations.list.viewholder.LoadingViewHolder
import com.uk.androidrecruitmentapp.utils.layoutInflater

class EpisodeAdapter : BaseAdapter<Episode>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = parent.context.layoutInflater
        return when (viewType) {
            PROGRESS_BAR -> {
                LoadingViewHolder(LoadingItemBinding.inflate(layoutInflater))

            }
            ITEM -> {
                EpisodeViewHolder(EpisodesItemBinding.inflate(layoutInflater))
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