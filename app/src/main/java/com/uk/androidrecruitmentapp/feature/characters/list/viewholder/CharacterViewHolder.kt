package com.uk.androidrecruitmentapp.feature.characters.list.viewholder

import android.view.View
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.data.local.Character
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.episodes_item.*

class CharacterViewHolder(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(character: Character?) {
        setupVH(character)
    }

    private fun setupVH(character: Character?) {
        character?.let {
            episode_name.text = it.name
        } ?: episode_name.setText(R.string.placeholder)
    }

    fun clear() {
        setupVH(null)
    }
}