package com.uk.androidrecruitmentapp.feature.episodes.list.viewholder

import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_item.*

class EpisodeViewHolder(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(episodeName: String) {
        episode_name.text = episodeName
    }
}