package com.nacarseven.series.presentation.serieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nacarseven.series.R
import com.nacarseven.series.databinding.ItemSeriesLoadStateFooterBinding

class SeriesLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<SeriesLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: SeriesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): SeriesLoadStateViewHolder {
        return SeriesLoadStateViewHolder.create(parent, retry)
    }
}

class SeriesLoadStateViewHolder(
    private val binding: ItemSeriesLoadStateFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.tvErrorDescription.text = loadState.error.localizedMessage
        }
        binding.progressLoadMore.isVisible = loadState is LoadState.Loading
        binding.btnRetry.isVisible = loadState is LoadState.Error
        binding.tvErrorDescription.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): SeriesLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_series_load_state_footer, parent, false)
            val binding = ItemSeriesLoadStateFooterBinding.bind(view)
            return SeriesLoadStateViewHolder(binding, retry)
        }
    }
}