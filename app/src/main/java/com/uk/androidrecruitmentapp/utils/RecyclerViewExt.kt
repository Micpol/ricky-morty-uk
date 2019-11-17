package com.uk.androidrecruitmentapp.utils

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrolledEvent(onScrolled: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onScrolled.invoke()
        }
    })
}