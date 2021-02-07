package com.uk.androidrecruitmentapp.presentation.characters.list.viewholder

import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.databinding.CharactersItemBinding
import com.uk.androidrecruitmentapp.presentation.base.BaseViewHolder

class CharacterViewHolder(
    private val binding: CharactersItemBinding
) : BaseViewHolder<Character?>(binding.root) {

    override fun bind(item: Character?) {
        setupVH(item)
    }

    private fun setupVH(character: Character?) {
        character?.let {
            binding.episodeName.text = it.name
        } ?: binding.episodeName.setText(R.string.placeholder)
    }

    override fun clear() {
        setupVH(null)
    }
}