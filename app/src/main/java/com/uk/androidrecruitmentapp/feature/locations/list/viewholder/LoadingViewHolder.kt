package com.uk.androidrecruitmentapp.feature.locations.list.viewholder

import android.view.View
import com.uk.androidrecruitmentapp.feature.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer

class LoadingViewHolder(
    override val containerView: View
) : BaseViewHolder<Any?>(containerView), LayoutContainer {

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