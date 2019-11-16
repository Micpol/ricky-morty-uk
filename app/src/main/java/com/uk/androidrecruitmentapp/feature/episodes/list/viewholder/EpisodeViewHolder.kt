package com.uk.androidrecruitmentapp.feature.episodes.list.viewholder

import android.view.View
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Result
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_item.*

class EpisodeViewHolder(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(episode: Result?) {
        setupVH(episode)
    }

    private fun setupVH(episode: Result?) {
        episode?.let {
            episode_name.text = it.name
        } ?: episode_name.setText(R.string.placeholder)
    }

    fun clear() {
        setupVH(null)
    }
}