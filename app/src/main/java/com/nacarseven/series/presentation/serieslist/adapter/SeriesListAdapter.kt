package com.nacarseven.series.presentation.serieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nacarseven.series.databinding.SeriesListItemBinding
import com.nacarseven.series.presentation.model.SeriesUi

class SeriesListAdapter(private val onItemClick: (SeriesUi) -> Unit) :
    PagingDataAdapter<SeriesUi, SeriesViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val holder = SeriesViewHolder(
            SeriesListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        holder.binding.root.setOnClickListener { view ->
            getItem(holder.adapterPosition)?.let {
                onItemClick.invoke(it)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

class SeriesViewHolder(
    val binding: SeriesListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(seriesUi: SeriesUi?, onItemClick: (SeriesUi) -> Unit) {
        with(binding) {
            seriesUi?.let {
                nameSeries.text = it.name
                Glide.with(binding.root.context).load(it.image).into(imgSeries)

                root.setOnClickListener {
                    onItemClick.invoke(seriesUi)
                }
            }
        }
    }
}

class MovieDiffCallBack : DiffUtil.ItemCallback<SeriesUi>() {
    override fun areItemsTheSame(oldItem: SeriesUi, newItem: SeriesUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SeriesUi, newItem: SeriesUi): Boolean {
        return oldItem == newItem
    }
}