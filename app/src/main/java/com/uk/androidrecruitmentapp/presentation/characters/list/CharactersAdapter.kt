package com.uk.androidrecruitmentapp.presentation.characters.list

import android.view.ViewGroup
import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.databinding.CharactersItemBinding
import com.uk.androidrecruitmentapp.databinding.LoadingItemBinding
import com.uk.androidrecruitmentapp.presentation.base.BaseAdapter
import com.uk.androidrecruitmentapp.presentation.base.BaseViewHolder
import com.uk.androidrecruitmentapp.presentation.characters.list.viewholder.CharacterViewHolder
import com.uk.androidrecruitmentapp.presentation.locations.list.viewholder.LoadingViewHolder
import com.uk.androidrecruitmentapp.presentation.utils.layoutInflater

class CharactersAdapter : BaseAdapter<Character>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = parent.context.layoutInflater
        return when (viewType) {
            PROGRESS_BAR -> {
                LoadingViewHolder(LoadingItemBinding.inflate(layoutInflater))
            }
            ITEM -> {
                CharacterViewHolder(CharactersItemBinding.inflate(layoutInflater))
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