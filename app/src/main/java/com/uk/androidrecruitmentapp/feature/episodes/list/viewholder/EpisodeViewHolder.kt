package com.uk.androidrecruitmentapp.feature.episodes.list.viewholder

import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Episode

import com.uk.androidrecruitmentapp.databinding.EpisodesItemBinding
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder

class EpisodeViewHolder(
    private val binding: EpisodesItemBinding
) : BaseViewHolder<Episode?>(binding.root) {

    override fun bind(item: Episode?) {
        setupVH(item)
    }

    private fun setupVH(episode: Episode?) {
        episode?.let {
            binding.episodeName.text = it.name
        } ?: binding.episodeName.setText(R.string.placeholder)
    }

    override fun clear() {
        setupVH(null)
    }
}