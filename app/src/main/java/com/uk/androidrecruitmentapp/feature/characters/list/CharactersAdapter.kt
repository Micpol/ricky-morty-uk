package com.uk.androidrecruitmentapp.feature.characters.list

import android.view.ViewGroup
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.feature.base.BaseAdapter
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder
import com.uk.androidrecruitmentapp.feature.characters.list.viewholder.CharacterViewHolder
import com.uk.androidrecruitmentapp.feature.locations.list.viewholder.LoadingViewHolder
import com.uk.androidrecruitmentapp.utils.layoutInflater

class CharactersAdapter : BaseAdapter<Character>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = parent.context.layoutInflater
        return when (viewType) {
            PROGRESS_BAR -> {
                val view = layoutInflater.inflate(R.layout.loading_item, parent, false)
                LoadingViewHolder(view)
            }
            ITEM -> {
                val view = layoutInflater.inflate(R.layout.characters_item, parent, false)
                CharacterViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is LoadingViewHolder -> {
                holder.bind(data[position])
            }
            is CharacterViewHolder -> {
                data[position]?.let {
                    holder.bind(it)
                }
            }
        }
    }
}