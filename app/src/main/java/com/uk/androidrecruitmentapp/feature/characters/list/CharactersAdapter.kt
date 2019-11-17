package com.uk.androidrecruitmentapp.feature.characters.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.feature.characters.list.viewholder.CharacterViewHolder

class CharactersAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<CharacterViewHolder>() {

    private val episodesResults = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.characters_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val episode = episodesResults[position]
        holder.bind(episode)
    }

    override fun onViewRecycled(holder: CharacterViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    override fun getItemCount(): Int {
        return episodesResults.size
    }

    fun submitData(data: MutableList<Character>) {
        episodesResults.clear()
        episodesResults.addAll(data)
        notifyDataSetChanged()
    }
}