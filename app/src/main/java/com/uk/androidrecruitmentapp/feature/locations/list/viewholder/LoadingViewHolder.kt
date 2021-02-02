package com.uk.androidrecruitmentapp.feature.locations.list.viewholder

import com.uk.androidrecruitmentapp.databinding.LoadingItemBinding
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder

class LoadingViewHolder(
    binding: LoadingItemBinding
) : BaseViewHolder<Any?>(binding.root) {

    override fun bind(item: Any?) {
        setupVH(item)
    }

    private fun setupVH(item: Any?) {
        //TODO showing some fancy animation if item not null
    }

    override fun clear() {
        setupVH(null)
    }
}