package com.uk.androidrecruitmentapp.feature.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Episodes
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_item.*

class EpisodeAdapter(private val episodes: Episodes) : androidx.recyclerview.widget.RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return episodes.results.size
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodes.results.get(position)
        holder.bind(episode.name)
    }

    class EpisodeViewHolder(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(episodeName: String) {
            episode_name.text = episodeName
        }
    }
}