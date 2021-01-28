package com.uk.androidrecruitmentapp.feature.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<*>>() {

    internal val data = mutableListOf<T?>()

    override fun getItemViewType(position: Int): Int {
        return if (data[position] == null) PROGRESS_BAR else ITEM
    }

    override fun onViewRecycled(holder: BaseViewHolder<*>) {
        super.onViewRecycled(holder)
        holder.clear()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitData(data: MutableList<T?>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun showLoading() {
        data.add(null)
        notifyItemInserted(data.size - 1)
    }

    fun hideLoading() {
        val position = data.indexOf(null)
        data.remove(null)
        notifyItemRemoved(position)
    }

    companion object {
        const val ITEM = 0
        const val PROGRESS_BAR = 1
    }
}