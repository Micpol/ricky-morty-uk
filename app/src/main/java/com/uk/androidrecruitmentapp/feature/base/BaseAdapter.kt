package com.uk.androidrecruitmentapp.feature.base

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {

        const val ITEM = 0
        const val PROGRESS_BAR = 1

    }

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
        Log.d("BaseAdapter", "showLoading: ");
        data.add(null)
        notifyItemInserted(data.size - 1)
    }

    fun hideLoading() {
        Log.d("BaseAdapter", "hideLoading: ");
        val position = data.indexOf(null)
        data.remove(null)
        notifyItemRemoved(position)
    }
}