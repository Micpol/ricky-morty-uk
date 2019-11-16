package com.uk.androidrecruitmentapp.feature.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Result
import com.uk.androidrecruitmentapp.feature.episodes.list.viewholder.EpisodeViewHolder

class EpisodeAdapter() : androidx.recyclerview.widget.RecyclerView.Adapter<EpisodeViewHolder>() {

    private val episodesResults = mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodesResults[position]
        holder.bind(episode.name)
    }

    override fun getItemCount(): Int {
        return episodesResults.size
    }

    fun submitData(data: MutableList<Result>) {
        episodesResults.clear()
        episodesResults.addAll(data)
        notifyDataSetChanged()
    }
}