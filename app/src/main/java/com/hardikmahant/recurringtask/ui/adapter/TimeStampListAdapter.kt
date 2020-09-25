package com.hardikmahant.recurringtask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hardikmahant.recurringtask.R
import kotlinx.android.synthetic.main.item_timestamp.view.*

class TimeStampListAdapter : RecyclerView.Adapter<TimeStampListAdapter.TimeStampListViewHolder>() {

    class TimeStampListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallBack)
    var timeStamps: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeStampListViewHolder {
        return TimeStampListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_timestamp, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TimeStampListViewHolder, position: Int) {
        val timeStamp = timeStamps[position]
        holder.itemView.apply {
            tvTimeStamp.text = timeStamp
        }
    }

    override fun getItemCount() = timeStamps.size
}