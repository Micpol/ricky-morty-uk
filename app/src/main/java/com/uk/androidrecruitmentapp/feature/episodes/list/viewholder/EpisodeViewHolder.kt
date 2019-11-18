package com.uk.androidrecruitmentapp.feature.episodes.list.viewholder

import android.view.View
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Episode
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.episodes_item.*

class EpisodeViewHolder(

        override val containerView: View

) : BaseViewHolder<Episode?>(containerView), LayoutContainer {

    override fun bind(item: Episode?) {
        setupVH(item)
    }

    private fun setupVH(episode: Episode?) {
        episode?.let {
            episode_name.text = it.name
        } ?: episode_name.setText(R.string.placeholder)
    }

    override fun clear() {
        setupVH(null)
    }
}