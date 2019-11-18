package com.uk.androidrecruitmentapp.feature.characters.list.viewholder

import android.view.View
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.episodes_item.*

class CharacterViewHolder(

        override val containerView: View

) : BaseViewHolder<Character?>(containerView), LayoutContainer {

    override fun bind(item: Character?) {
        setupVH(item)
    }

    private fun setupVH(character: Character?) {
        character?.let {
            episode_name.text = it.name
        } ?: episode_name.setText(R.string.placeholder)
    }

    override fun clear() {
        setupVH(null)
    }
}